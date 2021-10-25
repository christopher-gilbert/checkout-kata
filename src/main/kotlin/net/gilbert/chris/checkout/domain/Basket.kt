package net.gilbert.chris.checkout.domain

import net.gilbert.chris.checkout.annotation.VisibleForTesting
import net.gilbert.chris.checkout.entity.StockItem

@VisibleForTesting
data class Basket(
    private val currentPricingRules: PricingRules,
    val items: List<StockItem> = emptyList()
) {

    /**
     * return a new [Basket] derived from the existing basket with the new [StockItem] added.
     */
    fun addItem(stockItem: StockItem) = Basket(this.currentPricingRules, listOf(*items.toTypedArray(), stockItem))

}