package net.gilbert.chris.checkout.service

import spock.lang.Specification

class CheckoutServiceSpec extends Specification {

    def "Empty basket - known item scanned"() {

        given: 'an empty basket'

        and: 'an item with a known SKU'

        when: 'the SKU is scanned'

        then: 'the item is added to the basket'
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


}
