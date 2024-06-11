package com.dev.bayan.ibrahim.core.ui.utils

import androidx.compose.ui.Alignment


fun Alignment.toVerticalHorizontalAlignment(): Pair<Alignment.Vertical, Alignment.Horizontal> {
    val verticalAlignment: Alignment.Vertical
    val horizontalAlignment: Alignment.Horizontal
    when (this) {
        Alignment.TopStart -> {
            verticalAlignment = Alignment.Top
            horizontalAlignment = Alignment.Start
        }

        Alignment.TopCenter -> {
            verticalAlignment = Alignment.Top
            horizontalAlignment = Alignment.CenterHorizontally
        }

        Alignment.TopEnd -> {
            verticalAlignment = Alignment.Top
            horizontalAlignment = Alignment.End
        }

        Alignment.CenterStart -> {
            verticalAlignment = Alignment.Top
            horizontalAlignment = Alignment.Start
        }

        Alignment.Center -> {
            verticalAlignment = Alignment.Top
            horizontalAlignment = Alignment.CenterHorizontally
        }

        Alignment.CenterEnd -> {
            verticalAlignment = Alignment.Top
            horizontalAlignment = Alignment.End
        }

        Alignment.BottomStart -> {
            verticalAlignment = Alignment.Bottom
            horizontalAlignment = Alignment.Start
        }

        Alignment.BottomCenter -> {
            verticalAlignment = Alignment.Bottom
            horizontalAlignment = Alignment.CenterHorizontally
        }

        Alignment.BottomEnd -> {
            verticalAlignment = Alignment.Bottom
            horizontalAlignment = Alignment.End
        }

        else -> {
            verticalAlignment = Alignment.Bottom
            horizontalAlignment = Alignment.Start
        }
    }
    return verticalAlignment to horizontalAlignment
}