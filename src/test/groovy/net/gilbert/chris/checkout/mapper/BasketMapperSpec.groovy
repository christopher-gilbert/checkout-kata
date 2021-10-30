package net.gilbert.chris.checkout.mapper

import net.gilbert.chris.checkout.domain.Basket
import net.gilbert.chris.checkout.domain.StockItem
import net.gilbert.chris.checkout.dto.BasketDto
import net.gilbert.chris.checkout.dto.StockItemDto
import spock.lang.Specification

class BasketMapperSpec extends Specification {

    def "Map a simple basket"() {

        given: 'a typical basket containing a few items'
        def item1 = new StockItem('id1', 'sku1', 10)
        def item2 = new StockItem('id2', 'sku2', 10)
        def basket = Stub(Basket) {
            it.id >> 'id3'
            getSummary() >> [(item1): 3, (item2): 1]
        }

        when: 'it is mapped to a data transfer view'
        def result = new BasketMapper().basketToBasketDto(basket)

        then: 'the details are correctly mapped'
        result == new BasketDto('id3',
        [
                new StockItemDto('sku1', 3),
                new StockItemDto('sku2', 1)
        ])
    }
}
