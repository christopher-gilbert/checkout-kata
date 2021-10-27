package net.gilbert.chris.checkout.service

import net.gilbert.chris.checkout.entity.SpecialOffer
import net.gilbert.chris.checkout.entity.StockItem
import net.gilbert.chris.checkout.exception.DuplicateItemException
import net.gilbert.chris.checkout.exception.MissingItemException

/**
 * Service for safely managing [StockItems][StockItem] and [SpecialOffers][SpecialOffer], ensuring the integrity of
 * stored data.
 */
class StockPriceManagementService {

    /**
     * Create and store a new [StockItem] returning that item unless an item with the same SKU already exists,
     * in which case a [DuplicateItemException] is thrown.
     */
    fun addStockItem(sku: String, unitPrice: Int): StockItem = TODO()

    /**
     * Create and store a new [SpecialOffer] for the [StockItem] identified by the passed in SKU, returning that offer
     * unless an offer already exists for the same [StockItem] in which case a [DuplicateItemException] is thrown, or
     * there is no [StockItem] with that SKU, in which case a [MissingItemException] is thrown.
     */
    fun addSpecialOffer(stockItemSku: String, quantity: Int, bundlePrice: Int): SpecialOffer = TODO()
}