package net.gilbert.chris.checkout.entity

import net.gilbert.chris.checkout.annotation.VisibleForTesting

@VisibleForTesting
data class SpecialOffer(
    val id: String,
    val stockItem: StockItem,
    val quantity: Int,
    val bundlePrice: Int
) {
}