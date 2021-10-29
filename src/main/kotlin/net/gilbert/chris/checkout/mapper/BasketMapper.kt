package net.gilbert.chris.checkout.mapper

import net.gilbert.chris.checkout.domain.Basket
import net.gilbert.chris.checkout.dto.BasketDto
import net.gilbert.chris.checkout.dto.StockItemDto

class BasketMapper {

    fun basketToBasketDto(basket: Basket) =
        BasketDto(
            basketId = basket.id,
            items = basket.getSummary()
                .map { (item, quantity) -> StockItemDto(item.sku, quantity) }
        )

}
