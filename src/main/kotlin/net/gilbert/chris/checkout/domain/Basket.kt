package net.gilbert.chris.checkout.domain

import net.gilbert.chris.checkout.annotation.VisibleForTesting
import net.gilbert.chris.checkout.entity.StockItem
import net.gilbert.chris.checkout.entity.SpecialOffer

/**
 * Class for managing the state of checked out [StockItems][StockItem] during the checkout process. Each [Basket] is
 * associated with a set of [PricingRules] that determine the total cost of each item in the [Basket] after any
 * [SpecialOffers][SpecialOffer] are applied.
 *
 * Note that [Baskets][Basket] are immutable.
 */
@VisibleForTesting
data class Basket(
    val currentPricingRules: PricingRules,
    val items: List<StockItem> = emptyList()
) {

    /**
     * Returns a new [Basket] derived from the existing basket with the new [StockItem] added.
     */
    fun addItem(stockItem: StockItem) = Basket(this.currentPricingRules, listOf(*items.toTypedArray(), stockItem))

    /**
     * Provides a summary map of distinct [StockItems][StockItem] to the number of each in the basket.
     */
    fun getSummary() = items.groupingBy { it }.eachCount()

}