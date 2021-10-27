package net.gilbert.chris.checkout.entity

import net.gilbert.chris.checkout.annotation.VisibleForTesting

@VisibleForTesting
data class SpecialOffer(
    val id: String,
    val stockItem: StockItem,
    val bundleQuantity: Int,
    val bundlePrice: Int
) {

    fun priceOf(quantity: Int) =
        ((quantity / bundleQuantity * bundlePrice)
                + ((quantity % bundleQuantity) * stockItem.unitPrice))
}