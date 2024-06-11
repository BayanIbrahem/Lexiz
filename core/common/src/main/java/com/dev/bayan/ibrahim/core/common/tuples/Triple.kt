package com.qubit_team.apps.dental_clinic.core.common.tuples


/**
 * Creates a tuple of type [Triple] from this and [that].
 *
 * @see [to]
 * @see [to2]
 * @see [to3]
 * @see [to4]
 * @see [to5]
 */
infix fun <A, B, C> Pair<A, B>.to3(that: C): Triple<A, B, C> {
    return Triple(first, second, that)
}

