package net.gilbert.chris.checkout.domain

import net.gilbert.chris.checkout.annotation.VisibleForTesting

/**
 * Business rule implementation for determining the price of a number
 * of [stock items][StockItem] accounting for any applicable [special offers][SpecialOffer].
 */
@VisibleForTesting
data class PricingRules(
    private val applicableOffers: List<SpecialOffer>
) {

    /**
     * Retrieve the [PricingStrategy] that enables the total cost for a quantity of the passed in
     * [StockItem] to be calculated, accounting for any applicable [SpecialOffers][SpecialOffer].
     */
    fun getPricingStrategy(stockItem: StockItem) =
        applicableOffers.find {
            it.stockItem == stockItem
        } ?: stockItem

}