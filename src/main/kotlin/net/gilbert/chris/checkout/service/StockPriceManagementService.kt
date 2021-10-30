package net.gilbert.chris.checkout.service

import net.gilbert.chris.checkout.domain.SpecialOffer
import net.gilbert.chris.checkout.domain.StockItem
import net.gilbert.chris.checkout.exception.DuplicateItemException
import net.gilbert.chris.checkout.exception.MissingItemException
import net.gilbert.chris.checkout.repository.SpecialOfferRepository
import net.gilbert.chris.checkout.repository.StockItemRepository

/**
 * Service for safely managing [StockItems][StockItem] and [SpecialOffers][SpecialOffer], ensuring the integrity of
 * stored data.
 */
class StockPriceManagementService(
    private val stockItemRepository: StockItemRepository,
    private val specialOfferRepository: SpecialOfferRepository
) {

    /**
     * Create and store a new [StockItem] returning that item unless an item with the same SKU already exists,
     * in which case a [DuplicateItemException] is thrown.
     */
    fun addStockItem(sku: String, unitPrice: Int): StockItem {
        if (stockItemRepository.findBySku(sku) != null) {
            throw DuplicateItemException("A stock item already exists with sku $sku")
        }
        return stockItemRepository.save(
            StockItem(
                sku = sku,
                unitPrice = unitPrice
            )
        )
    }

    /**
     * Create and store a new [SpecialOffer] for the [StockItem] identified by the passed in SKU, returning that offer
     * unless an offer already exists for the same [StockItem] in which case a [DuplicateItemException] is thrown, or
     * there is no [StockItem] with that SKU, in which case a [MissingItemException] is thrown.
     */

    fun addSpecialOffer(stockItemSku: String, bundleQuantity: Int, bundlePrice: Int): SpecialOffer {
        if (specialOfferRepository.findOfferForStockItem(stockItemSku) != null) {
            throw DuplicateItemException("A special offer already exists for the stock item with sku $stockItemSku")
        }
        stockItemRepository.findBySku(stockItemSku)?.let {
            return specialOfferRepository.save(
                SpecialOffer(
                    stockItem = it,
                    bundleQuantity = bundleQuantity,
                    bundlePrice = bundlePrice
                )
            )
        } ?: throw MissingItemException("No stock items exist with sku $stockItemSku")

    }
}