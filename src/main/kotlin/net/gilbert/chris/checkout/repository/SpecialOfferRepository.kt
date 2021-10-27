package net.gilbert.chris.checkout.repository

import net.gilbert.chris.checkout.annotation.VisibleForTesting
import net.gilbert.chris.checkout.entity.SpecialOffer

@VisibleForTesting
class SpecialOfferRepository
    (private val currentOffers: MutableList<SpecialOffer> = mutableListOf()){

    /**
     * Return a list of [SpecialOffers][SpecialOffer] in place at the time of
     * retrieval, safe from any subsequent modification.
     */
    fun getCurrentSpecialOffers() = currentOffers.toList()
}