package com.qubit_team.apps.dental_clinic.core.common.tuples


/**
 * Creates a tuple of type [Pair] from this and [that]. like [to]
 *
 * This can be useful for creating [Map] literals with less noise, for example:
 * @see [to]
 * @see [to2]
 * @see [to3]
 * @see [to4]
 * @see [to5]
 */
infix fun <A, B> A.to2(that: B): Pair<A, B> {
    return Pair(this, that)
}
