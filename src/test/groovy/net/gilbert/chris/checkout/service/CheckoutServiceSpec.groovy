package net.gilbert.chris.checkout.service

import net.gilbert.chris.checkout.domain.Basket
import net.gilbert.chris.checkout.domain.PricingRules
import net.gilbert.chris.checkout.entity.SpecialOffer
import net.gilbert.chris.checkout.entity.StockItem
import net.gilbert.chris.checkout.repository.SpecialOfferRepository
import net.gilbert.chris.checkout.repository.StockItemRepository
import spock.lang.Specification

class CheckoutServiceSpec extends Specification {

    def "Start a new checkout"() {

        given: 'a set of special offers'
        def offer1 = Stub(SpecialOffer)
        def offer2 = Stub(SpecialOffer)
        def specialOfferRepository = Stub(SpecialOfferRepository) {
            it.currentSpecialOffers >> [offer1, offer2]
        }

        when: 'a new checkout is started'
        def basket = new CheckoutService(Stub(StockItemRepository), specialOfferRepository)
                .startCheckout()

        then: 'an empty basket is created with those special offers'
        basket.currentPricingRules == new PricingRules([offer1, offer2])
        basket.items == []
    }

    def "Start a new checkout with custom special offers"() {

        given: 'a set of special offers'
        def offer1 = Stub(SpecialOffer)
        def offer2 = Stub(SpecialOffer)

        when: 'a new checkout is started with those offers'
        def basket = new CheckoutService(Stub(StockItemRepository), Stub(SpecialOfferRepository))
                .startCheckout([offer1, offer2])

        then: 'an empty basket is created with those special offers'
        basket.currentPricingRules == new PricingRules([offer1, offer2])
        basket.items == []
    }


    def "Empty basket - known item scanned"() {

        given: 'an empty basket'
        def basket = new Basket(Stub(PricingRules), [])

        and: 'an item with a known SKU'
        def item = Stub(StockItem)
        def stockItemRepository = Stub(StockItemRepository) {
            it.findBySku('SKU') >> item
        }

        when: 'the SKU is scanned'
        def updatedBasket = new CheckoutService(stockItemRepository, Stub(SpecialOfferRepository))
                .scanItem('SKU', basket)

        then: 'the item is added to the basket'
        updatedBasket.items == [item]
    }

    def "Basket with existing items - known item scanned"() {

        given: 'a non-empty basket'
        def item1 = Stub(StockItem)
        def item2 = Stub(StockItem)
        def basket = new Basket(Stub(PricingRules), [])
                .addItem(item1)
                .addItem(item2)

        and: 'an item with a known SKU'
        def item3 = Stub(StockItem)
        def stockItemRepository = Stub(StockItemRepository) {
            it.findBySku('SKU') >> item3
        }

        when: 'the SKU is scanned'
        def updatedBasket = new CheckoutService(stockItemRepository, Stub(SpecialOfferRepository))
                .scanItem('SKU', basket)

        then: 'the item is added to the basket'
        updatedBasket.items == [item1, item2, item3]
    }

    def "Unknown item scanned"() {

        given: 'an empty basket'
        def basket = new Basket(Stub(PricingRules), [])

        and: 'an unknown SKU'
        def stockItemRepository = Stub(StockItemRepository) {
            it.findBySku('SKU') >> null
        }

        when: 'the SKU is scanned'
        new CheckoutService(stockItemRepository, Stub(SpecialOfferRepository))
                .scanItem('SKU', basket)

        then: 'an exception is thrown'
        thrown(IllegalArgumentException)

    }

    def "Calculating price of basket"() {

        given: 'a basket containing a mix of items'

        and: 'a set of offers, some of which apply to items in the basket'

        when: 'the price of the basket is calculated'

        then: 'it is as expected'

    }


}
