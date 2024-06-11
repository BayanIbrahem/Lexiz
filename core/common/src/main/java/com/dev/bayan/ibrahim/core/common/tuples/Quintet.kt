package com.qubit_team.apps.dental_clinic.core.common.tuples

import java.io.Serializable

/**
 * Represents a quintet of values
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 * Quintet exhibits value semantics, i.e. two quintets are equal if all five components are equal.
 * An example of decomposing it into values:
 *
 * @param A type of the first value.
 * @param B type of the second value.
 * @param C type of the third value.
 * @param D type of the forth value.
 * @param E type of the fifth value.
 * @property first First value.
 * @property second Second value.
 * @property third Third value.
 * @property forth Forth value.
 * @property fifth Fifth value.
 */
public data class Quintet<out A, out B, out C, out D, out E>(
    public val first: A,
    public val second: B,
    public val third: C,
    public val forth: D,
    public val fifth: E
) : Serializable {

    /**
     * Returns string representation of the [Quintet] including its [first], [second], [third], [forth] and [fifth] values.
     */
    public override fun toString(): String = "($first, $second, $third, $forth, $fifth)"
}

/**
 * Converts this quintet into a list.
 */
public fun <T> Quintet<T, T, T, T, T>.toList(): List<T> = listOf(first, second, third, forth, fifth)

/**
 * Creates a tuple of type [Quintet] from this and [that].
 *
 * @see [to]
 * @see [to2]
 * @see [to3]
 * @see [to4]
 * @see [to5]
 */
infix fun <A, B, C, D, E> Quadruple<A, B, C, D>.to5(that: E): Quintet<A, B, C, D, E> {
    return Quintet(first, second, third, forth, that)
}

@JvmName("quadruple_single_flatten_to_quintet")
fun <A, B, C, D, E> Pair<Quadruple<A, B, C, D>, E>.flatten(): Quintet<A, B, C, D, E> {
    return Quintet(first.first, first.second, first.third, first.forth, second)
}

@JvmName("single_quadruple_flatten_to_quintet")
fun <A, B, C, D, E> Pair<A, Quadruple<B, C, D, E>>.flatten(): Quintet<A, B, C, D, E> {
    return Quintet(first, second.first, second.second, second.third, second.forth)
}
