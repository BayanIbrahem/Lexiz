package com.qubit_team.apps.dental_clinic.core.common.tuples

import java.io.Serializable


/**
 * Represents a triad of values
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 * Quadruple exhibits value semantics, i.e. two triples are equal if all four components are equal.
 * An example of decomposing it into values:
 *
 * @param A type of the first value.
 * @param B type of the second value.
 * @param C type of the third value.
 * @param D type of the forth value
 * @property first First value.
 * @property second Second value.
 * @property third Third value.
 * @property forth Forth value.
 */
public data class Quadruple<out A, out B, out C, out D>(
    public val first: A,
    public val second: B,
    public val third: C,
    public val forth: D
) : Serializable {

    /**
     * Returns string representation of the [Quadruple] including its [first], [second] and [third] values.
     */
    public override fun toString(): String = "($first, $second, $third, $forth)"
}

/**
 * Converts this triple into a list.
 */
public fun <T> Quadruple<T, T, T, T>.toList(): List<T> = listOf(first, second, third, forth)

/**
 * Creates a tuple of type [Quadruple] from this and [that].
 *
 * @see [to]
 * @see [to2]
 * @see [to3]
 * @see [to4]
 * @see [to5]
 */
infix fun <A, B, C, D> Triple<A, B, C>.to4(that: D): Quadruple<A, B, C, D> {
    return Quadruple(first, second, third, that)
}


@JvmName("triple_single_flatten_to_quadruple")
fun <A, B, C, D> Pair<Triple<A, B, C>, D>.flatten(): Quadruple<A, B, C, D> {
    return Quadruple(first.first, first.second, first.third, second)
}

@JvmName("single_triple_flatten_to_quadruple")
fun <A, B, C, D> Pair<A, Triple<B, C, D>>.flatten(): Quadruple<A, B, C, D> {
    return Quadruple(first, second.first, second.second, second.third)
}
