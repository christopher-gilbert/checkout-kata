package net.gilbert.chris.checkout.domain

import spock.lang.Specification

class PricingRulesSpec extends Specification {

    def "Pricing for item on offer"() {

        given: 'a stock item that has a related special offer'

        when: 'the pricing for that item is retrieved'

        then: 'it is the special offer pricing'

    }

    def "Pricing for item not on offer"() {

        given: 'a stock item that does not have a related special offer'

        when: 'the pricing for that item is retrieved'

        then: 'it is the standard pricing'
    }

}
