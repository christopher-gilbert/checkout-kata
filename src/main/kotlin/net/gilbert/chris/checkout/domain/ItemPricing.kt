package net.gilbert.chris.checkout.domain

import net.gilbert.chris.checkout.entity.StockItem
import net.gilbert.chris.checkout.entity.SpecialOffer

/**
 * Represents the behaviour around calculating a price for
 * a quantity of any type of item. See [StockItem] and [SpecialOffer] for example implementations.
 */
interface ItemPricing {

    fun priceOf(quantity: Int): Int
}