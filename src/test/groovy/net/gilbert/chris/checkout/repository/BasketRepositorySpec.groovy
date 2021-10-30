package net.gilbert.chris.checkout.repository

import net.gilbert.chris.checkout.domain.Basket
import net.gilbert.chris.checkout.domain.PricingRules
import net.gilbert.chris.checkout.domain.StockItem
import net.gilbert.chris.checkout.exception.DuplicateItemException
import net.gilbert.chris.checkout.exception.MissingItemException
import spock.lang.Specification

class BasketRepositorySpec extends Specification {

    def "Safely save new basket"() {

        given: 'a basket'
        def input = new Basket('id1', Stub(PricingRules), [])

        when: 'it is stored'
        def repository = new BasketRepository()
        def output = repository.save(input)

        then: 'the input and output are detached from the stored version'
        def stored = repository.findById('id1')
        !input.is(stored)
        !output.is(stored)

        and: 'it is the same as the input'
        stored == input

    }

    def "Save new basket - but already exists"() {

        given: 'a basket'
        def basket = new Basket('id1', Stub(PricingRules), [])

        and: 'a stored basket with the same id'
        def repository = new BasketRepository(['id1': basket])

        when: 'attempting to store the basket'
        repository.save(basket)

        then: 'an exception is thrown'
        thrown(DuplicateItemException)

    }

    def "Safely update basket"() {

        given: 'a basket'
        def input = new Basket('id1', Stub(PricingRules), [])

        and: 'a stored basket with the same ID'
        def repository = new BasketRepository(['id1': new Basket('id1', Stub(PricingRules), [Stub(StockItem)])])

        when: 'the stored basket is updated'
        def output = repository.update(input)

        then: 'the input and output are detached from the stored version'
        def stored = repository.findById('id1')
        !input.is(stored)
        !output.is(stored)

        and: 'it is the same as the input'
        stored == input

    }

    def "update basket - not found"() {

        given: 'a basket'
        def basket = new Basket('id1', Stub(PricingRules), [])

        and: 'an empty repository'
        def repository = new BasketRepository([:])

        when: 'attempting to update the basket'
        repository.update(basket)

        then: 'an exception is thrown'
        thrown(MissingItemException)

    }

    def "Safely retrieve basket"() {

        given: 'a stored basket'
        def stored = new Basket('id1', Stub(PricingRules), [Stub(StockItem)])
        def repository = new BasketRepository(['id1': stored])

        when: 'it is retrieved'
        def output = repository.findById('id1')

        then: 'the output is detached from the stored version'
        !output.is(stored)
    }

}
