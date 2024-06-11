package com.dev.bayan.ibrahim.core.ui.res

import androidx.annotation.PluralsRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import com.dev.bayan.ibrahim.core.ui.R


/**
 * return an ordinal  model like
 * 'first book' or 'the 10th apple'
 * for any number not is range 1..10 it will take this resource format
 * [R.string.xth] or [R.string.the_xth] according to [format]
 */
@Composable
fun ordinalModel(
    @PluralsRes model: Int,
    ordinal: Int,
    format: OrdinalFormal = OrdinalFormal.THE_NORMAL,
): String {
    val modelString = pluralStringResource(id = model, count = 1)
    return format.ordinalRes(ordinal)?.let {
        stringResource(id = it, modelString)
    } ?: stringResource(id = format.generalOrdinalRes, ordinal, modelString)
}

/**
 * @property NORMAL like 'first' and 'second'
 * @property THE_NORMAL like 'the first' or 'the second'
 * @property BRIEF like '1st' or '2nd' (in arabic it will use as [NORMAL])
 * @property THE_BRIEF like 'the 1st' or 'the 2nd' (in arabic it will use [THE_NORMAL])
 */
enum class OrdinalFormal(
    internal val ordinalRes: (Int) -> Int?,
    internal val generalOrdinalRes: Int,
) {
    NORMAL(ordinalRes = { it.normalOrdinalRes() }, R.string.xth),
    THE_NORMAL(ordinalRes = { it.theNormalOrdinalRes() }, R.string.the_xth),
    BRIEF(ordinalRes = { it.briefOrdinalRes() }, R.string.xth),
    THE_BRIEF(ordinalRes = { it.theBriefOrdinalRes() }, R.string.the_xth),
}

private fun Int.normalOrdinalRes(): Int? = when (this) {
    1 -> R.string.first
    2 -> R.string.second
    3 -> R.string.third
    4 -> R.string.fourth
    5 -> R.string.fifth
    6 -> R.string.sixth
    7 -> R.string.seventh
    8 -> R.string.eighth
    9 -> R.string.ninth
    10 -> R.string.tenth
    else -> null
}

private fun Int.theNormalOrdinalRes(): Int? = when (this) {
    1 -> R.string.the_first
    2 -> R.string.the_second
    3 -> R.string.the_third
    4 -> R.string.the_fourth
    5 -> R.string.the_fifth
    6 -> R.string.the_sixth
    7 -> R.string.the_seventh
    8 -> R.string.the_eighth
    9 -> R.string.the_ninth
    10 -> R.string.the_tenth
    else -> null
}

private fun Int.briefOrdinalRes(): Int? = when (this) {
    1 -> R.string._1st
    2 -> R.string._2nd
    3 -> R.string._3rd
    4 -> R.string._4th
    5 -> R.string._5th
    6 -> R.string._6th
    7 -> R.string._7th
    8 -> R.string._8th
    9 -> R.string._9th
    10 -> R.string._10th
    else -> null
}

private fun Int.theBriefOrdinalRes(): Int? = when (this) {
    1 -> R.string.the_1st
    2 -> R.string.the_2nd
    3 -> R.string.the_3rd
    4 -> R.string.the_4th
    5 -> R.string.the_5th
    6 -> R.string.the_6th
    7 -> R.string.the_7th
    8 -> R.string.the_8th
    9 -> R.string.the_9th
    10 -> R.string.the_10th
    else -> null
}
