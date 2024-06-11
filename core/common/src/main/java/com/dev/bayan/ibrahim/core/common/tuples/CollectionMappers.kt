package com.qubit_team.apps.dental_clinic.core.common.tuples


/**
 * @param exclusive if the list count is more that 2 then it will throw an exception
 * @throws IllegalArgumentException if the size of the list isn't suitable
 * (short, or large with flag [exclusive] on)
 * @return Pair of type [T] for first and second
 */
@JvmName("CollectionToPair")
fun <T> Collection<T>.toPair(exclusive: Boolean = false): Pair<T, T> {
    require(count() >= 2) { "insufficient size (${count()}) to fit into pair" }
    if (exclusive) {
        require(count() == 2) { "large size (${count()}) to fit into pair" }
    }
    return Pair(elementAt(0), elementAt(1))
}

/**
 * @param exclusive if the list count is more that 3 then it will throw an exception
 * @throws IllegalArgumentException if the size of the list isn't suitable
 * (short, or large with flag [exclusive] on)
 * @return Triple of type [T] for first, second and third
 */
@JvmName("CollectionToTriple")
fun <T> Collection<T>.toTriple(exclusive: Boolean = false): Triple<T, T, T> {
    require(count() >= 3) { "insufficient size (${count()}) to fit into triple" }
    if (exclusive) {
        require(count() == 3) { "large size (${count()}) to fit into triple" }
    }
    return Triple(elementAt(0), elementAt(1), elementAt(2))
}

/**
 * @param exclusive if the list count is more that 4 then it will throw an exception
 * @throws IllegalArgumentException if the size of the list isn't suitable
 * (short, or large with flag [exclusive] on)
 * @return Quadruple of type [T] for first, second, third and fourth
 */
@JvmName("CollectionToQuadruple")
fun <T> Collection<T>.toQuadruple(exclusive: Boolean = false): Quadruple<T, T, T, T> {
    require(count() >= 4) { "insufficient size (${count()}) to fit into quadruple" }
    if (exclusive) {
        require(count() == 4) { "large size (${count()}) to fit into quadruple" }
    }
    return Quadruple(elementAt(0), elementAt(1), elementAt(2), elementAt(3))
}

/**
 * @param exclusive if the list count is more that 5 then it will throw an exception
 * @throws IllegalArgumentException if the size of the list isn't suitable
 * (short, or large with flag [exclusive] on)
 * @return Quintet of type [T] for first, second, third, fourth and fifth
 */
@JvmName("CollectionToQuintet")
fun <T> Collection<T>.toQuintet(exclusive: Boolean = false): Quintet<T, T, T, T, T> {
    require(count() >= 5) { "insufficient size (${count()}) to fit into quintet" }
    if (exclusive) {
        require(count() == 5) { "large size (${count()}) to fit into quintet" }
    }
    return Quintet(elementAt(0), elementAt(1), elementAt(2), elementAt(3), elementAt(4))
}

/**
 * @param exclusive if the list count is more than 2, returns null instead of throwing an exception
 * @return Pair of type [T] for first and second, or null if the list size is unsuitable
 */
@JvmName("CollectionToPairOrNull")
fun <T> Collection<T>.toPairOrNull(exclusive: Boolean = false): Pair<T, T>? {
    return if (count() >= 2 && (!exclusive || count() == 2)) {
        Pair(elementAt(0), elementAt(1))
    } else {
        null
    }
}

/**
 * @param exclusive if the list count is more than 3, returns null instead of throwing an exception
 * @return Triple of type [T] for first, second, and third, or null if the list size is unsuitable
 */
@JvmName("CollectionToTripleOrNull")
fun <T> Collection<T>.toTripleOrNull(exclusive: Boolean = false): Triple<T, T, T>? {
    return if (count() >= 3 && (!exclusive || count() == 3)) {
        Triple(elementAt(0), elementAt(1), elementAt(2))
    } else {
        null
    }
}

/**
 * @param exclusive if the list count is more than 4, returns null instead of throwing an exception
 * @return Quadruple of type [T] for first, second, third, and fourth, or null if the list size is unsuitable
 */
@JvmName("CollectionToQuadrupleOrNull")
fun <T> Collection<T>.toQuadrupleOrNull(exclusive: Boolean = false): Quadruple<T, T, T, T>? {
    return if (count() >= 4 && (!exclusive || count() == 4)) {
        Quadruple(elementAt(0), elementAt(1), elementAt(2), elementAt(3))
    } else {
        null
    }
}

/**
 * @param exclusive if the list count is more than 5, returns null instead of throwing an exception
 * @return Quintet of type [T] for first, second, third, fourth, and fifth, or null if the list size is unsuitable
 */
@JvmName("CollectionToQuintetOrNull")
fun <T> Collection<T>.toQuintetOrNull(exclusive: Boolean = false): Quintet<T, T, T, T, T>? {
    return if (count() >= 5 && (!exclusive || count() == 5)) {
        Quintet(elementAt(0), elementAt(1), elementAt(2), elementAt(3), elementAt(4))
    } else {
        null
    }
}
