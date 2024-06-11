package com.dev.bayan.ibrahim.core.ui.utils.animation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.ui.Alignment

fun fadeVerticalExpandEnterTransition(
    expandFrom: Alignment.Vertical,
    duration: Int = animDefaultFadeResize,
    delay: Int = 0,
): EnterTransition =
    fadeIn(tween(duration, delay)) + expandVertically(tween(duration, delay), expandFrom)

fun fadeVerticalShrinkExitTransition(
    shrinkTowards: Alignment.Vertical,
    duration: Int = animDefaultFadeResize,
    delay: Int = 0,
): ExitTransition {
    return fadeOut(tween(duration, delay)) + shrinkVertically(tween(duration, delay), shrinkTowards)
}

fun fadeHorizontalExpandEnterTransition(
    expandFrom: Alignment.Horizontal,
    duration: Int = animDefaultFadeResize,
    delay: Int = 0,
): EnterTransition =
    fadeIn(tween(duration, delay)) + expandHorizontally(tween(duration, delay), expandFrom)

fun fadeHorizontalShrinkExitTransition(
    shrinkTowards: Alignment.Horizontal,
    duration: Int = animDefaultFadeResize,
    delay: Int = 0,
): ExitTransition {
    return fadeOut(tween(duration, delay)) + shrinkHorizontally(
        tween(duration, delay),
        shrinkTowards
    )
}
