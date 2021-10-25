package net.gilbert.chris.checkout.entity

import net.gilbert.chris.checkout.annotation.VisibleForTesting

@VisibleForTesting
data class StockItem
    (val id: String,
     val sku: String,
     val unitPrice: Int)