package com.dev.bayan.ibrahim.core.ui.utils.animation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.ui.Alignment

fun fadeExpandAnimatedVisibilityEnterTransition(
    duration: Int = animDefaultFadeResize,
    delay: Int = 0,
    expandFrom: Alignment
): EnterTransition = fadeIn(tween(duration, delay)) + expandIn(tween(duration, delay), expandFrom)

fun fadeShrinkAnimatedVisibilityExitTransition(
    duration: Int = animDefaultFadeResize,
    delay: Int = 0,
    shrinkTowards: Alignment
): ExitTransition {
    return fadeOut(tween(duration, delay)) + shrinkOut(tween(duration, delay), shrinkTowards)
}
