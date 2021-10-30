package net.gilbert.chris.checkout.domain

import net.gilbert.chris.checkout.annotation.VisibleForTesting
import java.util.UUID.randomUUID

/**
 * Simple representation of an item that may be purchased in the supermarket.
 */
@VisibleForTesting
data class StockItem(
    val id: String = randomUUID().toString(),
    val sku: String,
    private val unitPrice: Int
) : PricingStrategy {

    override fun priceOf(quantity: Int) = quantity * unitPrice

}