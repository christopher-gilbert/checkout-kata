package net.gilbert.chris.checkout.entity

data class StockItem
    (val id: String,
     val sku: String,
     val unitPrice: Int,
     val specialOffer: SpecialOffer?)
{

        fun isOnOffer() = specialOffer != null
}