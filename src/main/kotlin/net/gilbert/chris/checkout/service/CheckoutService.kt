package net.gilbert.chris.checkout.service

import net.gilbert.chris.checkout.domain.Basket
import net.gilbert.chris.checkout.domain.PricingRules
import net.gilbert.chris.checkout.entity.StockItem
import net.gilbert.chris.checkout.repository.StockItemRepository

class CheckoutService(
    private val stockItemRepository: StockItemRepository
) {

    /**
     * Create a new [Basket] associated with a set of [pricing rules][PricingRules]
     * that are valid for the lifetime of the [Basket].
     */
    fun startCheckout(pricingRules: PricingRules) = Basket(pricingRules)

    /**
     * Add the [StockItem] identified by the sku to the basket, if the sku
     * is valid, else throw [IllegalArgumentException]
     */
    fun scanItem(sku: String, basket: Basket) {
        basket.addItem(requireNotNull(stockItemRepository.findBySku(sku)))
    }

}