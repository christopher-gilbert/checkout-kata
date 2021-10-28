package net.gilbert.chris.checkout.dto

/**
 * Read only view of basket contents for presentation.
 */
data class BasketDto(
    val basketId: String,
    val items: List<StockItemDto> = listOf(),
    val totalPrice: Sterling?
)

data class StockItemDto(
    val sku: String,
    val quantity: Int?,
    val totalPrice: Sterling?
)

data class Sterling(
    val pounds: Int,
    val pence: Int
)