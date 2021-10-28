package net.gilbert.chris.checkout.web

import net.gilbert.chris.checkout.dto.BasketDto
import net.gilbert.chris.checkout.mapper.BasketMapper
import net.gilbert.chris.checkout.service.CheckoutService

class CheckoutController(
    private val checkoutService: CheckoutService,
    private val basketMapper: BasketMapper
) {

    // POST /baskets (actually no details to post at this stage - could pass in a sparse DTO, but then everything has
    // to be nullable
    fun createBasket(): BasketDto = TODO()

    /**
     * Add any items in the updatedBasket to the stored version of the basket, and return an
     * updated view. Caller is expected to populate the basket ID and SKUs of any new items only. Any
     * other properties that are passed in are ignored.
     *
     * PATCH /baskets. Although patch semantics allow any amended properties to be passed in,
     * this system only expects (and allows) new items to be added.
     *
     * eg
     * {
     *     "basketId": "111234de-12df-acb4-1235-84535dac6385",
     *     "items": [
     *         {
     *            "sku": "A"
     *         }
     *     ]
     */
    fun addItem(updatedBasket: BasketDto): BasketDto = TODO()


}