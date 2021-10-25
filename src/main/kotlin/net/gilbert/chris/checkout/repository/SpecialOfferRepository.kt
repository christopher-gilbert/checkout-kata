package net.gilbert.chris.checkout.repository

import net.gilbert.chris.checkout.annotation.VisibleForTesting
import net.gilbert.chris.checkout.entity.SpecialOffer

@VisibleForTesting
class SpecialOfferRepository
    (private val currentOffers: List<SpecialOffer>){

    fun getCurrentSpecialOffers() = currentOffers
}