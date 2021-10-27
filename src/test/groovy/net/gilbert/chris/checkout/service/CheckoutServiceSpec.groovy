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
        def result = new CheckoutService(Stub(StockItemRepository), specialOfferRepository, Stub(PricingService))
                .startCheckout()

        then: 'an empty basket is created with those special offers'
        result.currentPricingRules == new PricingRules([offer1, offer2])
        result.items == []
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
        def result = new CheckoutService(stockItemRepository, Stub(SpecialOfferRepository), Stub(PricingService))
                .scanItem('SKU', basket)

        then: 'the item is added to the basket'
        result.items == [item]
    }

    def "Basket with existing items - known item scanned"() {

        given: 'a non-empty basket'

        and: 'an item with a known SKU'

        when: 'the SKU is scanned'

        then: 'the item is added to the basket'
    }

    def "Unknown item scanned"() {

        given: 'an empty basket'

        and: 'an item with an unknown SKU'

        when: 'the SKU is scanned'

        then: 'an exception is thrown'

    }

    def "Calculating price of basket"() {

        given: 'a basket containing a mix of items'

        and: 'a set of offers, some of which apply to items in the basket'

        when: 'the price of the basket is calculated'

        then: 'it is as expected'

    }


}
