package net.gilbert.chris.checkout.service

import net.gilbert.chris.checkout.domain.Basket
import net.gilbert.chris.checkout.domain.PricingRules
import net.gilbert.chris.checkout.domain.SpecialOffer
import net.gilbert.chris.checkout.domain.StockItem
import net.gilbert.chris.checkout.exception.MissingItemException
import net.gilbert.chris.checkout.repository.BasketRepository
import net.gilbert.chris.checkout.repository.SpecialOfferRepository
import net.gilbert.chris.checkout.repository.StockItemRepository

/**
 * Main entry point to the checkout process, allowing a [Basket] of [StockItems][StockItem] to be created,
 * added to, and priced.
 */
class CheckoutService(
    private val stockItemRepository: StockItemRepository,
    private val specialOfferRepository: SpecialOfferRepository,
    private val basketRepository: BasketRepository
) {

    /**
     * Create a new [Basket] associated with the set of [pricing rules][PricingRules]
     * based on [SpecialOffers][SpecialOffer] have been previously stored. These rules will be
     * valid for the lifetime of the [Basket], even if the stored rules are updated.
     */
    fun startCheckout() = basketRepository.save(Basket(applicablePricingRules = getCurrentPricingRules()))

    /**
     * Create a new [Basket] associated with a set of [pricing rules][PricingRules]
     * based on the passed in [SpecialOffers][SpecialOffer]. These override any [SpecialOffers][SpecialOffer]
     * that have been previously stored.
     */
    fun startCheckout(applicableOffers: List<SpecialOffer>) =
        basketRepository.save(Basket(applicablePricingRules = PricingRules(applicableOffers)))

    /**
     * Add the [StockItem] identified by the SKU to the [Basket] with the passed in basketId and return the updated
     * basket, if the basketId and SKU exist, else throw an exception.
     */
    fun addItem(sku: String, basketId: String) =
        basketRepository.findById(basketId)
            ?.addItem(requireNotNull(stockItemRepository.findBySku(sku)))
            ?.let { basketRepository.save(it) }
            ?: throw MissingItemException("basket with ID $basketId does not exist")


    /**
     * Calculate a total price for all the [StockItems][StockItem] in the [Basket] in pence, accounting
     * for any [SpecialOffers][SpecialOffer] that are applicable to the items based on the set of offers
     * associated with the basket at the start of checkout.
     *
     * WARNING - this assumes that each type of item in the basket has a unique pricing strategy.
     */
    fun calculateTotalPrice(basketId: String): Int {
        val basket = basketRepository.findById(basketId)
            ?: throw MissingItemException("basket with ID $basketId does not exist")
        return basket
            .getSummary()
            .mapKeys { (item) -> basket.applyPricingRules(item) }
            .map { (pricingStrategy, quantity) ->
                pricingStrategy.priceOf(quantity)
            }
            .sum()
    }

    private fun getCurrentPricingRules() =
        PricingRules(specialOfferRepository.getCurrentSpecialOffers())

}