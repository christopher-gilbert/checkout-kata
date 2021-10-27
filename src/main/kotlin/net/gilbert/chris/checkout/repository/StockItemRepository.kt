package net.gilbert.chris.checkout.repository

import net.gilbert.chris.checkout.annotation.VisibleForTesting
import net.gilbert.chris.checkout.entity.StockItem
import java.util.*
import java.util.UUID.randomUUID

@VisibleForTesting
class StockItemRepository(
    private val stockItems: MutableList<StockItem> = mutableListOf()
) {

    /**
     * Store the stock item with a unique ID, and return the stored version.
     */
    fun save(stockItem: StockItem): StockItem {
        val storedStockItem = stockItem.copy(id = randomUUID().toString())
        stockItems.add(storedStockItem)
        return storedStockItem
    }

    fun findBySku(sku: String) = stockItems.find {
        it.sku == sku
    }

}