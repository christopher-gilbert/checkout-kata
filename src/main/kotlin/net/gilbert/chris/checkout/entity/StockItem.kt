package net.gilbert.chris.checkout.entity

import net.gilbert.chris.checkout.annotation.VisibleForTesting
import net.gilbert.chris.checkout.domain.ItemPricing

@VisibleForTesting
data class StockItem
    (val id: String,
     val sku: String,
     val unitPrice: Int): ItemPricing {

        override fun priceOf(quantity: Int): Int  = TODO()
    }