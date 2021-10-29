package net.gilbert.chris.checkout.domain

import net.gilbert.chris.checkout.annotation.VisibleForTesting
import java.util.UUID.randomUUID

@VisibleForTesting
data class SpecialOffer(
    val id: String = randomUUID().toString(),
    val stockItem: StockItem,
    val bundleQuantity: Int,
    val bundlePrice: Int
) : ItemPricing {

    override fun priceOf(quantity: Int) =
        ((quantity / bundleQuantity * bundlePrice)
                + stockItem.priceOf(quantity % bundleQuantity))

}