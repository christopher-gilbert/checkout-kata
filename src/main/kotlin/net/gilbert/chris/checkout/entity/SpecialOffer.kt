package net.gilbert.chris.checkout.entity

import net.gilbert.chris.checkout.annotation.VisibleForTesting
import net.gilbert.chris.checkout.domain.ItemPricing

@VisibleForTesting
data class SpecialOffer(
    val id: String,
    val stockItem: StockItem,
    val bundleQuantity: Int,
    val bundlePrice: Int
): ItemPricing {

    override fun priceOf(quantity: Int) =
        ((quantity / bundleQuantity * bundlePrice)
                + ((quantity % bundleQuantity) * stockItem.unitPrice))
}