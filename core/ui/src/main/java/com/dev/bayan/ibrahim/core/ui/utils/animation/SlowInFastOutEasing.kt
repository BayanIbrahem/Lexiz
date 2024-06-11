package com.dev.bayan.ibrahim.core.ui.utils.animation

import androidx.compose.animation.core.Easing

val SlowInFastOutEasing = Easing { fraction ->
    // Use a quadratic function to create a parabolic curve
    // The curve starts slow, reaches the peak at 0.5, and ends fast
    val x = fraction * 2f // Scale the fraction to [0, 2]
    val y = if (x < 1f) {
        // For the first half, use y = x^2 / 2
        x * x / 2f
    } else {
        // For the second half, use y = - (x - 2)^2 / 2 + 1
        -(x - 2f) * (x - 2f) / 2f + 1f
    }
    y // Return the fraction of the animation value
}
