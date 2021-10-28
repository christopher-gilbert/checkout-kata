package net.gilbert.chris.checkout.repository

import net.gilbert.chris.checkout.entity.SpecialOffer
import net.gilbert.chris.checkout.entity.StockItem
import spock.lang.Specification

class StockItemRepositorySpec extends Specification {

    def "Store item, return stored version"() {

        given: 'an item'
        def item = new StockItem(null, 'sku1', 10)

        when: 'it is stored'
        def repository = new StockItemRepository([])
        def storedItem = repository.save(item)

        then: 'the stored item has a unique (in the current universe) ID'
        UUID.fromString(storedItem.id)

        and: 'it is stored in the database'
        repository.stockItems.any { it == storedItem }

    }

    def "Retrieve item by SKU - item found"() {

        given: 'a set of stock items'
        def item1 = new StockItem(null, 'sku1', 10)
        def item2 = new StockItem(null, 'sku2', 20)

        when: 'looking up an item by one of the item SKUs'
        def result = new StockItemRepository([item1, item2]).findBySku('sku2')

        then: 'the relevant item is found'
        result == item2

    }

    def "Retrieve item by SKU - no offer found"() {

        given: 'a set of stock items'
        def item1 = new StockItem(null, 'sku1', 10)
        def item2 = new StockItem(null, 'sku2', 20)

        when: 'looking up an item by a different SKU'
        def result = new StockItemRepository([item1, item2]).findBySku('sku3')

        then: 'nothing is found'
        result == null

    }
}
