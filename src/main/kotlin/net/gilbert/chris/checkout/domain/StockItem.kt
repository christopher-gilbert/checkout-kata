package net.gilbert.chris.checkout.domain

import net.gilbert.chris.checkout.annotation.VisibleForTesting
import java.util.UUID.randomUUID

@VisibleForTesting
data class StockItem
    (
    val id: String = randomUUID().toString(),
    val sku: String,
    private val unitPrice: Int
) : ItemPricing {

    override fun priceOf(quantity: Int) = quantity * unitPrice

}