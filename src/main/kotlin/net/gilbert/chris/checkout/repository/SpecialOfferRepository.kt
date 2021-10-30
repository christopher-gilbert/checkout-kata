package net.gilbert.chris.checkout.repository

import net.gilbert.chris.checkout.annotation.VisibleForTesting
import net.gilbert.chris.checkout.domain.SpecialOffer
import net.gilbert.chris.checkout.domain.StockItem

@VisibleForTesting
class SpecialOfferRepository(
    private val currentOffers: MutableList<SpecialOffer> = mutableListOf()
) {

    /**
     * Store the offer, and return the stored version.
     * (note that as special offers are immutable it is safe to return the
     * actual stored version)
     */
    fun save(specialOffer: SpecialOffer): SpecialOffer {
        currentOffers.add(specialOffer)
        return specialOffer
    }

    /**
     * Return a list of [SpecialOffers][SpecialOffer] in place at the time of
     * retrieval, safe from any subsequent modification.
     */
    fun getCurrentSpecialOffers() = currentOffers.toList()

    /**
     * Return the [SpecialOffer] associated with the [StockItem] with the passed in SKU or null if none is found.
     */
    fun findOfferForStockItem(sku: String) = currentOffers.find { it.stockItem.sku == sku }
}