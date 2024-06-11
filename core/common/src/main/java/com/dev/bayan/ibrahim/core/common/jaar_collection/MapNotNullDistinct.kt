package com.dev.bayan.ibrahim.core.common.jaar_collection

/**
 * Returns a list containing the results (without coupeling of applying the given [transform] function
 * to each element in the original collection.
 *
 */
public inline fun <T, R> Iterable<T>.mapNotNullDistinct(transform: (T) -> R?): List<R> {
    val set = HashSet<T>()
    return this.mapNotNull {
        if (set.add(it)) {
            transform(it)
        } else null
    }
}
