package net.gilbert.chris.checkout.service

import net.gilbert.chris.checkout.domain.Basket
import net.gilbert.chris.checkout.domain.PricingRules
import net.gilbert.chris.checkout.entity.StockItem
import net.gilbert.chris.checkout.repository.SpecialOfferRepository
import net.gilbert.chris.checkout.repository.StockItemRepository

class CheckoutService(
    private val stockItemRepository: StockItemRepository,
    private val specialOfferRepository: SpecialOfferRepository,
    private val pricingService: PricingService
) {

    /**
     * Create a new [Basket] associated with a set of [pricing rules][PricingRules]
     * that are valid for the lifetime of the [Basket].
     */
    fun startCheckout() = Basket(getCurrentPricingRules())

    /**
     * Add the [StockItem] identified by the sku to the [Basket] and return a new [Basket], if the sku
     * is valid, else throw [IllegalArgumentException]
     *
     * Note that the [Basket] passed in is not modified - client should work with the returned [Basket].
     */
    fun scanItem(sku: String, basket: Basket) =
        basket.addItem(requireNotNull(stockItemRepository.findBySku(sku)))

    fun calculateTotalPrice(basket: Basket) =
        basket
            .getSummary()
            .map {
                pricingService.calculateItemTotal(it.key, it.value, basket.currentPricingRules)
            }
            .sum()

    private fun getCurrentPricingRules() =
        PricingRules(specialOfferRepository.getCurrentSpecialOffers())

}