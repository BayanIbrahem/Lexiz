package com.dev.bayan.ibrahim.core.common.tuples

import com.qubit_team.apps.dental_clinic.core.common.tuples.Quadruple
import com.qubit_team.apps.dental_clinic.core.common.tuples.Quintet

/**
 * sample 1 to 2 tof 3 returns Triple(1, 2, 3) see [Triple]
 * @return flatten version of the tuple after adding a new value
 */
@JvmName("infixFlattenToTriple")
infix fun <A, B, C> Pair<A, B>.tof(that: C): Triple<A, B, C> {
    return Triple(
        first = first,
        second = second,
        third = that,
    )
}

/**
 * sample 1 to 2 to 3 tof 4 returns Quadruple(1, 2, 3, 4) see [Quadruple]
 * @return flatten version of the tuple after adding a new value
 */
@JvmName("infixFlattenToQuadruple")
infix fun <A, B, C, D> Pair<Pair<A, B>, C>.tof(that: D): Quadruple<A, B, C, D> {
    return Quadruple(
        first = first.first,
        second = first.second,
        third = second,
        forth = that,
    )
}

/**
 * sample 1 to 2 to 3 to 4 tof 5 returns Quintet(1, 2, 3, 4, 5) see [Quintet]
 * @return flatten version of the tuple after adding a new value
 */
@JvmName("infixFlattenToQuintet")
infix fun <A, B, C, D, E> Pair<Pair<Pair<A, B>, C>, D>.tof(that: E): Quintet<A, B, C, D, E> {
    return Quintet(
        first = first.first.first,
        second = first.first.second,
        third = first.second,
        forth = second,
        fifth = that,
    )
}
