package net.gilbert.chris.checkout.repository

import spock.lang.Specification

class SpecialOfferRepositorySpec extends Specification {

    def "Retrieved offers with subsequent updates"() {

        given: 'a set of offers retrieved from the repository'

        when: 'the stored offers are amended'

        then: 'the retrieved set are unchanged'

    }

}
