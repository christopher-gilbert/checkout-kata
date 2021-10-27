package net.gilbert.chris.checkout.service

import net.gilbert.chris.checkout.entity.SpecialOffer
import net.gilbert.chris.checkout.entity.StockItem
import net.gilbert.chris.checkout.exception.DuplicateItemException

/**
 * Service for safely managing [StockItems][StockItem] and [SpecialOffers][SpecialOffer], ensuring the integrity of
 * stored data.
 */
class StockPriceManagementService {

    /**
     * Create and store a new [StockItem] returning that item unless an item with the same SKU already exists,
     * in which case a [DuplicateItemException] is thrown
     */
    fun addStockItem(sku: String, unitPrice: Int): StockItem = TODO()

    /**
     * Create and store a new [SpecialOffer] returning that offer unless an offer already exists for the
     * same [StockItem] in which case a [DuplicateItemException] is thrown
     */
    fun addSpecialOffer(stockItem: StockItem, quantity: Int, bundlePrice: Int): SpecialOffer = TODO()
}