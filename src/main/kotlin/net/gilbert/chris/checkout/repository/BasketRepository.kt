package net.gilbert.chris.checkout.repository

import net.gilbert.chris.checkout.annotation.VisibleForTesting
import net.gilbert.chris.checkout.domain.Basket
import net.gilbert.chris.checkout.exception.DuplicateItemException
import net.gilbert.chris.checkout.exception.MissingItemException

/**
 * Store of [Baskets][Basket] that are in the checkout process or have completed checkout. Note that any
 * [Baskets][Basket] accessed through the repository are detached from the stored versions, so modifying
 * a returned [Basket] will not affect the stored version - that can only be updated via
 * [BasketRepository#update]
 */
@VisibleForTesting
class BasketRepository(
    private val baskets: MutableMap<String, Basket> = mutableMapOf()
) {

    /**
     * Stores a new [Basket] based on the passed in [Basket] and returns a copy of the stored version.
     *
     * Throws [DuplicateItemException] if there is already a basket stored with the same ID.
     */
    fun save(basket: Basket): Basket {
        if (findById(basket.id) != null) {
            throw DuplicateItemException("basket with id ${basket.id} already exists")
        }
        val storedBasket = basket.copy()
        baskets[storedBasket.id] = (storedBasket)
        return storedBasket.copy()
    }

    /**
     * Replace the current version of the [Basket] with details from the passed in updated
     * version, and returns a copy of the stored version
     *
     * Throws [MissingItemException] if there is no [Basket] with the id of
     * the passed in one.
     */
    fun update(basket: Basket): Basket {
        findById(basket.id)
            ?.let {
                baskets[basket.id] = basket.copy()
            } ?: throw MissingItemException("basket with ID ${basket.id} does not exist")
        return basket.copy()
    }

    /**
     * Returns a copy of the stored [Basket] with the passed in ID, or null if it is not found.
     */
    fun findById(basketId: String) = baskets[basketId]?.copy()
}