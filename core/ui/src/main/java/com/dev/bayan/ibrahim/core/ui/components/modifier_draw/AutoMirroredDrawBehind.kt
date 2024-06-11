package com.dev.bayan.ibrahim.core.ui.components.modifier_draw

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.LayoutDirection.Ltr
import androidx.compose.ui.unit.LayoutDirection.Rtl

@Composable
fun Modifier.autoMirroredDrawBehind(onDraw: DrawScope.(direction: LayoutDirection) -> Unit): Modifier {
    val direction = LocalLayoutDirection.current
    return this.drawBehind {
        scale(direction.scale, 1f) {
            onDraw(direction)
        }
    }
}

private val LayoutDirection.scale: Float
    get() = when (this) {
        Ltr -> 1f
        Rtl -> -1f
    }