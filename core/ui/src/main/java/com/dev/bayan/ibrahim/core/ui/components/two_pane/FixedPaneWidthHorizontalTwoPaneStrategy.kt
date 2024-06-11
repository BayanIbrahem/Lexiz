package com.dev.bayan.ibrahim.core.ui.components.two_pane

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.google.accompanist.adaptive.SplitResult
import com.google.accompanist.adaptive.TwoPaneStrategy
import java.lang.Float.compare
import java.lang.Float.max

/**
 * returns TwoPaneStrategy with conditions:
 * second pane has provided minWidth
 * second pane has maxWidth provided as a dimen,
 * second screen width will determinate the split x
 * if minWidth is larger than width from dimen then the gap will take half width from the first
 * pane and half from the second pane, otherwise, the whole space will be taken from the first pane
 * which don't has min width
 * see also [FixedFirstPaneMinWidthHorizontalTwoPaneStrategy]
 */
fun FixedSecondPaneMinWidthHorizontalTwoPaneStrategy(
    secondPaneMinWidth: Dp,
    secondPaneWidthFraction: Float,
    gapWidth: Dp = 16.dp,
): TwoPaneStrategy = TwoPaneStrategy { density, layoutDirection, layoutCoordinates ->
    val secondPaneWidthFractionalPixel = layoutCoordinates.size.width * secondPaneWidthFraction
    val secondPaneWidthPixel =
        max(with(density) { secondPaneMinWidth.toPx() }, secondPaneWidthFractionalPixel)
    val takeGapWidthFromBothPanes: Boolean =
        with(density) { secondPaneMinWidth.toPx() } < secondPaneWidthFractionalPixel
    val splitX = when (layoutDirection) {
        LayoutDirection.Ltr -> layoutCoordinates.size.width - secondPaneWidthPixel
        LayoutDirection.Rtl -> secondPaneWidthPixel
    }
    val splitWidthPixel = with(density) { gapWidth.toPx() }

    SplitResult(
        gapOrientation = Orientation.Vertical,
        gapBounds = Rect(
            left = splitX - (splitWidthPixel / if (takeGapWidthFromBothPanes) 2f else 1f),
            top = 0f,
            right = splitX + if (takeGapWidthFromBothPanes) splitWidthPixel / 2f else 0f,
            bottom = layoutCoordinates.size.height.toFloat(),
        )
    )
}

/**
 * returns TwoPaneStrategy with conditions:
 * first pane has provided minWidth
 * first pane has maxWidth provided as a dimen,
 * first screen width will determinate the split x
 * if minWidth is larger than width from dimen then the gap will take half width from the first
 * pane and half from the second pane, otherwise, the whole space will be taken from the second pane
 * which don't has min width
 * see also [FixedSecondPaneMinWidthHorizontalTwoPaneStrategy]
 */
fun FixedFirstPaneMinWidthHorizontalTwoPaneStrategy(
    firstPaneMinWidth: Dp,
    gapWidth: Dp = 0.dp,
    firstPaneWidthFraction: Float,
): TwoPaneStrategy = TwoPaneStrategy { density, layoutDirection, layoutCoordinates ->
    val firstPaneWidthFractionalPixel = layoutCoordinates.size.width * firstPaneWidthFraction
    val firstPaneWidthPixel =
        max(with(density) { firstPaneMinWidth.toPx() }, firstPaneWidthFractionalPixel)
    val takeGapWidthFromBothPanes: Boolean =
        with(density) { firstPaneMinWidth.toPx() } < firstPaneWidthFractionalPixel
    val splitX = when (layoutDirection) {
        LayoutDirection.Ltr -> firstPaneWidthPixel
        LayoutDirection.Rtl -> layoutCoordinates.size.width - firstPaneWidthPixel
    }
    val splitWidthPixel = with(density) { gapWidth.toPx() }

    SplitResult(
        gapOrientation = Orientation.Vertical,
        gapBounds = Rect(
            left = splitX - if (takeGapWidthFromBothPanes) splitWidthPixel / 2f else 0f,
            top = 0f,
            right = splitX + (splitWidthPixel / if (takeGapWidthFromBothPanes) 2f else 1f),
            bottom = layoutCoordinates.size.height.toFloat(),
        )
    )
}

