package com.dev.bayan.ibrahim.core.ui.components.row_column

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.layout.Layout
import kotlin.math.roundToInt

@Composable
fun JaArRowOrColumnIfNarrow(
    modifier: Modifier = Modifier,
    spacedBetween: Boolean = true,
    vararg contents: @Composable @UiComposable () -> Unit,
) {
    Layout(
        modifier = modifier,
        contents = contents.toList(),
    ) { contentsMeasurables, constrains ->
        val maxWidth = constrains.maxWidth
        val zeroMinConstrains = constrains.copy(minWidth = 0, minHeight = 0)
        val contentsPlaceables = contentsMeasurables.map {
            it.first().measure(zeroMinConstrains)
        }
        val isNarrow = maxWidth < contentsPlaceables.sumOf { it.width }
        val width: Int = maxWidth
        val height = if (isNarrow) {
            contentsPlaceables.sumOf { it.height }
        } else {
            contentsPlaceables.maxOf { it.height }
        }
        layout(width, height) {
            if (isNarrow) {// vertically placement
                contentsPlaceables.mapIndexed { index, placeable ->
                    placeable to contentsPlaceables.subList(0, index).sumOf { it.height }
                }.forEach { (p, y) ->
                    p.placeRelative(x = (width - p.width) / 2, y = y)
                }
            } else { // horizontal placement
                val extraSpaceBetweenTwoItems = (
                        if ((contentsPlaceables.isEmpty() || contentsPlaceables.count() == 1) && !spacedBetween) 0f
                        else (width - contentsPlaceables.sumOf { it.width }) /
                                (contentsPlaceables.count() - 1f)
                        ).roundToInt()
                contentsPlaceables.mapIndexed { index, placeable ->
                    placeable to if (index == 0) {
                        0
                    } else {
                        val pSum = contentsPlaceables.subList(0, index).sumOf { it.width }
                        pSum + (index * extraSpaceBetweenTwoItems)
                    }
                }.forEach { (p, x) ->
                    p.placeRelative(x = x, y = (height - p.height) / 2)
                }
            }
        }
    }
}