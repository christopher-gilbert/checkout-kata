package net.gilbert.chris.checkout.service

import net.gilbert.chris.checkout.annotation.VisibleForTesting
import net.gilbert.chris.checkout.domain.PricingRules
import net.gilbert.chris.checkout.entity.StockItem

@VisibleForTesting
class PricingService {

    fun calculateItemTotal(stockItem: StockItem, quantity: Int, applicablePricingRules: PricingRules) =
        when {
            applicablePricingRules.isOnOffer(stockItem) -> TODO()
            else -> stockItem.priceOf(quantity)
        }



    fun calculateItemTotal(stockItem: StockItem, quantity: Int): Int =
        quantity * stockItem.unitPrice

}
