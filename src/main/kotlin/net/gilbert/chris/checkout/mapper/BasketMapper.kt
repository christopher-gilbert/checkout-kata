package net.gilbert.chris.checkout.mapper

import net.gilbert.chris.checkout.domain.Basket
import net.gilbert.chris.checkout.dto.BasketDto
import net.gilbert.chris.checkout.dto.Sterling
import net.gilbert.chris.checkout.dto.StockItemDto
import net.gilbert.chris.checkout.entity.StockItem

class BasketMapper {

    fun basketToBasketDto(basket: Basket): BasketDto = TODO()
}

class StockItemMapper {

    fun itemsToItemDto(itemSummary: Map<StockItem, Int> ): StockItemDto = TODO()
}

class SterlingMapper {

    fun penceToPoundsAndPence(pence: Int): Sterling = TODO()
}