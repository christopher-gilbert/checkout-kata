package net.gilbert.chris.checkout.repository

import net.gilbert.chris.checkout.domain.Basket
import net.gilbert.chris.checkout.exception.MissingItemException

/**
 * Store of [Baskets][Basket] that are in the checkout process or have completed checkout. Note that any
 * [Baskets][Basket] accessed through the repository are detached from the stored versions, so modifying
 * a returned [Basket] will not affect the stored version - that can only be updated via
 * [BasketRepository#update]
 */
class BasketRepository(
    private val baskets: MutableMap<String, Basket> = mutableMapOf()
) {

    /**
     * Stores a new [Basket] and returns the stored version.
     */
    fun save(basket: Basket): Basket {
        val storedBasket = basket.copy()
        baskets[storedBasket.id] = (storedBasket)
        return storedBasket
    }

    /**
     * Replace the current version of the [Basket] with the passed in updated
     * version, and returns the stored version
     *
     * Throws [MissingItemException] if there is no [Basket] with the id of
     * the passed in one.
     */
    fun update(basket: Basket): Basket {
        findById(basket.id)
            ?.let {
                baskets[basket.id] = basket
            } ?: throw MissingItemException("basket with ID ${basket.id} does not exist")
        return basket.copy()
    }

    fun findById(basketId: String) = baskets[basketId]?.copy()
}