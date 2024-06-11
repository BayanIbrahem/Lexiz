package com.dev.bayan.ibrahim.core.common.null_coerce

fun Int.coercedInOrNull(collection: Collection<*>): Int? {
    return coercedInOrNull(0, collection.count().dec())
}

fun <K> K.coercedInOrNull(min: K, max: K): K? where K : Comparable<K> {
    return if (this in min..max) this else null
}

fun <K> K.coercedAtLeastOrNull(min: K): K? where K : Comparable<K> {
    return if (this >= min) this else null
}

fun <K> K.coercedAtMostOrNull(max: K): K? where K : Comparable<K> {
    return if (this <= max) this else null
}
