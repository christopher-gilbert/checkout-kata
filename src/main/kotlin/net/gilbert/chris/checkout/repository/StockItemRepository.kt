package net.gilbert.chris.checkout.repository

import net.gilbert.chris.checkout.annotation.VisibleForTesting
import net.gilbert.chris.checkout.entity.StockItem

@VisibleForTesting
class StockItemRepository
    (val stockItems: List<StockItem>){



    fun findBySku(sku: String) = stockItems.find {
        it.sku == sku
    }

}