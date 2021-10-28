package net.gilbert.chris.checkout.domain

import net.gilbert.chris.checkout.entity.SpecialOffer
import net.gilbert.chris.checkout.entity.StockItem
import spock.lang.Specification

class PricingRulesSpec extends Specification {

    def "Pricing for item on offer"() {

        given: 'a stock item that has a related special offer'
        def item = new StockItem(null, 'sku', 20)
        def offer = Stub(SpecialOffer) {
            getStockItem() >> item
        }
        def applicableOffers = [offer]

        when: 'the pricing for that item is retrieved'
        def pricing = new PricingRules(applicableOffers).getItemPricing(item)

        then: 'it is the special offer pricing'
        pricing == offer

    }

    def "Pricing for item not on offer"() {

        given: 'a stock item that does not have a related special offer'
        def item = new StockItem(null, 'sku', 20)
        def applicableOffers = []

        when: 'the pricing for that item is retrieved'
        def pricing = new PricingRules(applicableOffers).getItemPricing(item)

        then: 'it is the standard pricing'
        pricing == item
    }

}
