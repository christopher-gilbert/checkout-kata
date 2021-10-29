package net.gilbert.chris.checkout.repository

import net.gilbert.chris.checkout.annotation.VisibleForTesting
import net.gilbert.chris.checkout.domain.StockItem
import java.util.UUID.randomUUID

@VisibleForTesting
class StockItemRepository(
    private val stockItems: MutableList<StockItem> = mutableListOf()
) {

    /**
     * Store the stock item and return the stored version.
     * (note that as stock items are immutable it is safe to return the
     * actual stored version)
     */
    fun save(stockItem: StockItem): StockItem {
        stockItems.add(stockItem)
        return stockItem
    }

    fun findBySku(sku: String) = stockItems.find {
        it.sku == sku
    }

}