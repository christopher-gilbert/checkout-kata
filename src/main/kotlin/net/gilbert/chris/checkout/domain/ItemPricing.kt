package net.gilbert.chris.checkout.domain

import net.gilbert.chris.checkout.service.CheckoutService

/**
 * Represents the strategy for calculating a price for a quantity of any type of item.
 * See [StockItem] and [SpecialOffer] for example implementations, and [CheckoutService] for example usage.
 */
interface ItemPricing {

    fun priceOf(quantity: Int): Int

}