package net.gilbert.chris.checkout.exception

/**
 * General exception to indicate an attempt to create a new item that already exists.
 */
class DuplicateItemException(message: String) : RuntimeException(message)