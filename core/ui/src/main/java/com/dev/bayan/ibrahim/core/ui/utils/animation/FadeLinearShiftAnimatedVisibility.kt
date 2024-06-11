package com.dev.bayan.ibrahim.core.ui.utils.animation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalLayoutDirection

fun fadeVerticalSlideEnterTransition(
    slideFrom: Alignment.Vertical,
    duration: Int = animDefaultFadeResize,
    delay: Int = 0,
): EnterTransition = fadeIn(
    animationSpec = tween(duration, delay)
) + slideInVertically(
    animationSpec = tween(duration, delay)
) {
    slideFrom.align(1, it)
}

fun fadeVerticalSlideExitTransition(
    slideTo: Alignment.Vertical,
    duration: Int = animDefaultFadeResize,
    delay: Int = 0,
): ExitTransition = fadeOut(
    animationSpec = tween(duration, delay)
) + slideOutVertically(
    animationSpec = tween(duration, delay)
) {
    slideTo.align(1, 1)
}

@Composable
fun fadeHorizontalSlideEnterTransition(
    slideFrom: Alignment.Horizontal,
    duration: Int = animDefaultFadeResize,
    delay: Int = 0,
): EnterTransition {
    val direction = LocalLayoutDirection.current
    return fadeIn(
        animationSpec = tween(duration, delay)
    ) + slideInHorizontally(
        animationSpec = tween(duration, delay)
    ) {
        slideFrom.align(1, it, direction)
    }
}

@Composable
fun fadeHorizontalSlideExitTransition(
    slideTo: Alignment.Horizontal,
    duration: Int = animDefaultFadeResize,
    delay: Int = 0,
): ExitTransition {
    val direction = LocalLayoutDirection.current
    return fadeOut(
        animationSpec = tween(duration, delay)
    ) + slideOutHorizontally(
        animationSpec = tween(duration, delay)
    ) {
        slideTo.align(1, it, direction)
    }
}
