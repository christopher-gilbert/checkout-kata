package net.gilbert.chris.checkout.repository

import net.gilbert.chris.checkout.entity.StockItem

class StockItemRepository
    (val stockItems: List<StockItem>){



    fun findBySku(sku: String) = stockItems.find {
        it.sku == sku
    }

}