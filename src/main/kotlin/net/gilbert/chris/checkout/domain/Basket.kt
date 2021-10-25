package net.gilbert.chris.checkout.domain

import net.gilbert.chris.checkout.entity.StockItem

data class Basket(
    private val currentPricingRules: PricingRules,
    private val items: MutableList<StockItem> = mutableListOf()
) {

    fun addItem(stockItem: StockItem) {
        items.add(stockItem)
    }


}