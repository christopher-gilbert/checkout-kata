package net.gilbert.chris.checkout

import net.gilbert.chris.checkout.dto.Sterling
import net.gilbert.chris.checkout.mapper.BasketMapper
import net.gilbert.chris.checkout.mapper.SterlingMapper
import net.gilbert.chris.checkout.repository.BasketRepository
import net.gilbert.chris.checkout.repository.SpecialOfferRepository
import net.gilbert.chris.checkout.repository.StockItemRepository
import net.gilbert.chris.checkout.service.CheckoutService
import net.gilbert.chris.checkout.service.StockPriceManagementService
import net.gilbert.chris.checkout.web.CheckoutController
import spock.lang.Specification

class EndToEndTest extends Specification {

    def "Checkout a typical basket"() {

        given: 'a stock database'
        def stockItemRepository = new StockItemRepository()
        def specialOfferRepository = new SpecialOfferRepository()

        and: 'a set of stored stock items'
        def priceService = new StockPriceManagementService(
                stockItemRepository, specialOfferRepository
        )
        priceService.addStockItem('A', 50)
        priceService.addStockItem('B', 30)
        priceService.addStockItem('C', 20)
        priceService.addStockItem('D', 15)

        and: 'some special offers on some of them'
        priceService.addSpecialOffer('A', 3, 130)
        priceService.addSpecialOffer('B', 2, 45)

        and: 'an API to access stock'
        def basketRepository = new BasketRepository()
        def checkoutService = new CheckoutService(
                stockItemRepository, specialOfferRepository, basketRepository
        )
        def checkoutController = new CheckoutController(checkoutService, new BasketMapper(), new SterlingMapper())

        and: 'a new basket'
        def basketId = checkoutController.createBasket().basketId

        when: 'a selection of items is added to the basket'
        with(checkoutController) {
            scanItem('A', basketId)
            scanItem('D', basketId)
            scanItem('A', basketId)
            scanItem('A', basketId)
            scanItem('A', basketId)
            scanItem('B', basketId)
            scanItem('B', basketId)
            scanItem('C', basketId)
            scanItem('C', basketId)
            scanItem('C', basketId)


        }

        and: 'the total price is calculated'
        def finalBill = checkoutController.getTotalPrice(basketId)

        then: 'the total is correct, accounting for special offers'
        // A -> 130 + 50
        // B -> 45
        // C -> 3 * 20
        // D -> 1 * 15
        finalBill == new Sterling(3, 0)


    }

}
