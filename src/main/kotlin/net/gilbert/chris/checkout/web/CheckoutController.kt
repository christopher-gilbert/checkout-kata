package net.gilbert.chris.checkout.web

import net.gilbert.chris.checkout.dto.BasketDto
import net.gilbert.chris.checkout.mapper.BasketMapper
import net.gilbert.chris.checkout.mapper.SterlingMapper
import net.gilbert.chris.checkout.service.CheckoutService

class CheckoutController(
    private val checkoutService: CheckoutService,
    private val basketMapper: BasketMapper,
    private val sterlingMapper: SterlingMapper
) {

    // POST /baskets (actually no details to post at this stage - could pass in a sparse DTO, but then everything has
    // to be nullable
    fun createBasket() = basketMapper
        .basketToBasketDto(checkoutService.startCheckout())

    /**
     * Add a single item in the updatedBasket to the stored version of the basket, and return an
     * updated view. Note that this API does not follow REST conventions - to do so would
     * require a PATCH or PUT of a basket, and patching an additional item in would really
     * require the API to expose the list of distinct items in the basket leaving the client
     * to form a presentable view of that raw data.
     */
    fun scanItem(itemSku: String, basketId: String): BasketDto {
        val basket = checkoutService.addItem(itemSku, basketId)
        return basketMapper.basketToBasketDto(basket)
    }

    fun getTotalPrice(basketId: String) = sterlingMapper.penceToSterling(checkoutService.calculateTotalPrice(basketId))

}