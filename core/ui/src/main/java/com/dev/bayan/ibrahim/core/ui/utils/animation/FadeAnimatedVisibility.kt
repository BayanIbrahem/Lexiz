package com.dev.bayan.ibrahim.core.ui.utils.animation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

fun fadeAnimatedVisibilityEnterTransition(
    duration: Int = animDefaultFade,
    delay: Int = 0
): EnterTransition {
    return fadeIn(animationSpec = tween(duration, delay))
}

fun fadeAnimatedVisibilityExitTransition(
    duration: Int = animDefaultFade,
    delay: Int = 0
): ExitTransition {
    return fadeOut(animationSpec = tween(duration, delay))
}
