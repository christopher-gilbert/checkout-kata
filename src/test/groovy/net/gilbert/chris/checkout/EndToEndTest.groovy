package net.gilbert.chris.checkout


import net.gilbert.chris.checkout.repository.SpecialOfferRepository
import net.gilbert.chris.checkout.repository.StockItemRepository
import net.gilbert.chris.checkout.service.CheckoutService
import net.gilbert.chris.checkout.service.StockPriceManagementService
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

        and: 'a new basket'
        def checkoutService = new CheckoutService(
                stockItemRepository, specialOfferRepository
        )
        def basket = checkoutService.startCheckout()

        when: 'a selection of items is added to the basket'
        basket = checkoutService.scanItem('A', basket)
        basket = checkoutService.scanItem('D', basket)
        basket = checkoutService.scanItem('A', basket)
        basket = checkoutService.scanItem('A', basket)
        basket = checkoutService.scanItem('A', basket)
        basket = checkoutService.scanItem('B', basket)
        basket = checkoutService.scanItem('B', basket)
        basket = checkoutService.scanItem('C', basket)
        basket = checkoutService.scanItem('C', basket)
        basket = checkoutService.scanItem('C', basket)

        and: 'the total price is calculated'
        def finalBill = checkoutService.calculateTotalPrice(basket)

        then: 'the total is correct, accounting for special offers'
        // A -> 130 + 50
        // B -> 45
        // C -> 3 * 20
        // D -> 1 * 15
        finalBill == 300


    }

}
