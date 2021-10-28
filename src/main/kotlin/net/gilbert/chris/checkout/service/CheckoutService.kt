package net.gilbert.chris.checkout.service

import net.gilbert.chris.checkout.domain.Basket
import net.gilbert.chris.checkout.domain.PricingRules
import net.gilbert.chris.checkout.entity.SpecialOffer
import net.gilbert.chris.checkout.entity.StockItem
import net.gilbert.chris.checkout.repository.SpecialOfferRepository
import net.gilbert.chris.checkout.repository.StockItemRepository

/**
 * Main entry point to the checkout process, allowing a [Basket] of [StockItems][StockItem] to be created,
 * added to, and priced.
 */
class CheckoutService(
    private val stockItemRepository: StockItemRepository,
    private val specialOfferRepository: SpecialOfferRepository
) {

    /**
     * Create a new [Basket] associated with the set of [pricing rules][PricingRules]
     * based on [SpecialOffers][SpecialOffer] have been previously stored. These rules will be
     * valid for the lifetime of the [Basket], even if the stored rules are updated.
     */
    fun startCheckout() = Basket(getCurrentPricingRules())

    /**
     * Create a new [Basket] associated with a set of [pricing rules][PricingRules]
     * based on the passed in [SpecialOffers][SpecialOffer]. These override any [SpecialOffers][SpecialOffer]
     * that have been previously stored.
     */
    fun startCheckout(applicableOffers: List<SpecialOffer>) = Basket(PricingRules(applicableOffers))


    /**
     * Add the [StockItem] identified by the SKU to the [Basket] and return a new [Basket], if the SKU
     * is valid, else throw [IllegalArgumentException]
     *
     * Note that the [Basket] passed in is not modified - client should work with the returned [Basket].
     */
    fun scanItem(sku: String, basket: Basket) =
        basket.addItem(requireNotNull(stockItemRepository.findBySku(sku)))


    fun calculateTotalPrice(basket: Basket) =
        basket
            .getSummary()
            .mapKeys { (item) -> basket.applyPricingRules(item) }
            .map { (itemPricing, quantity) ->
                itemPricing.priceOf(quantity)
            }
            .sum()


    private fun getCurrentPricingRules() =
        PricingRules(specialOfferRepository.getCurrentSpecialOffers())

}