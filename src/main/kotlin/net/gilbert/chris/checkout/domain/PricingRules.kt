package net.gilbert.chris.checkout.domain

import net.gilbert.chris.checkout.annotation.VisibleForTesting
import net.gilbert.chris.checkout.entity.SpecialOffer
import net.gilbert.chris.checkout.entity.StockItem

/**
 * Business rule implementation for determining the price of a number
 * of [stock items][StockItem] accounting for any applicable [special offers][SpecialOffer].
 */
@VisibleForTesting
data class PricingRules(
    private val applicableOffers: List<SpecialOffer>
) {

    /**
     * Retrieve the [ItemPricing] that enables the total cost for a quantity of the passed in
     * [StockItem] to be calculated, accounting for any applicable [SpecialOffers][SpecialOffer].
     */
    fun getItemPricing(stockItem: StockItem) =
        applicableOffers.find {
            it.stockItem == stockItem
        } ?: stockItem

}