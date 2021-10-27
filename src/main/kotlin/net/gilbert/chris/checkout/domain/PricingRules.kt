package net.gilbert.chris.checkout.domain

import net.gilbert.chris.checkout.annotation.VisibleForTesting
import net.gilbert.chris.checkout.entity.SpecialOffer
import net.gilbert.chris.checkout.entity.StockItem

/**
 * Business rule implementation for determining the price of a number
 * of [stock items][StockItem] accounting for any applicable [special offers][SpecialOffer]
 */
@VisibleForTesting
data class PricingRules(
    private val currentOffers: List<SpecialOffer>
) {

    fun isOnOffer(stockItem: StockItem) = currentOffers
        .any { it == stockItem }

}