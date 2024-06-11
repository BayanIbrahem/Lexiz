package com.dev.bayan.ibrahim.core.common.jaar_collection

inline fun <T, K> Iterable<T>.countDistinctBy(selector: (T) -> K): Int {
    val set = HashSet<K>()
    for (e in this) {
        val key = selector(e)
        set.add(key)
    }
    return set.count()
}
