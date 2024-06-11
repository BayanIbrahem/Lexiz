package com.qubit_team.apps.dental_clinic.core.common.tuples


@JvmName("flattenToTriple")
fun <A, B, C> Pair<Pair<A, B>, C>.flatten(): Triple<A, B, C> {
    return Triple(
        first = first.first,
        second = first.second,
        third = second
    )
}

@JvmName("flattenToQuadruple")
fun <A, B, C, D> Pair<Pair<Pair<A, B>, C>, D>.flatten(): Quadruple<A, B, C, D> {
    return Quadruple(
        first = first.first.first,
        second = first.first.second,
        third = first.second,
        forth = second
    )
}

@JvmName("flattenToQuintet")
fun <A, B, C, D, E> Pair<Pair<Pair<Pair<A, B>, C>, D>, E>.flatten(): Quintet<A, B, C, D, E> {
    return Quintet(
        first = first.first.first.first,
        second = first.first.first.second,
        third = first.first.second,
        forth = first.second,
        fifth = second
    )
}
