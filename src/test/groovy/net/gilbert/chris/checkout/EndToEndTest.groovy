package net.gilbert.chris.checkout

import spock.lang.Specification

class EndToEndTest extends Specification {

    def "Checkout a typical basket"() {

        given: 'a set of stock items'

        and: 'some special offers on some of them'

        and: 'a new basket'

        when: 'a selection of items is added to the basket'

        and: 'the total price is calculated'

        then: 'the total is correct, accounting for special offers'




    }

}
