package net.gilbert.chris.checkout.repository

import net.gilbert.chris.checkout.annotation.VisibleForTesting
import net.gilbert.chris.checkout.entity.SpecialOffer
import java.util.UUID.randomUUID

@VisibleForTesting
class SpecialOfferRepository
    (private val currentOffers: MutableList<SpecialOffer> = mutableListOf()) {

    /**
     * Store the stock item with a unique ID, and return the stored version.
     */
    fun save(specialOffer: SpecialOffer): SpecialOffer {
        val storedSpecialOffer = specialOffer.copy(id = randomUUID().toString())
        currentOffers.add(storedSpecialOffer)
        return storedSpecialOffer
    }

    /**
     * Return a list of [SpecialOffers][SpecialOffer] in place at the time of
     * retrieval, safe from any subsequent modification.
     */
    fun getCurrentSpecialOffers() = currentOffers.toList()

    fun findOfferForStockItem(sku: String) = currentOffers.find { it.stockItem.sku == sku }
}