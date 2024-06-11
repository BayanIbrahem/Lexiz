package com.dev.bayan.ibrahim.core.ui.components.slider

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.common.format.asSimpleString
import com.dev.bayan.ibrahim.core.common.range.mapFromPercentageRange
import com.dev.bayan.ibrahim.core.common.range.mapToPercentageRange
import com.dev.bayan.ibrahim.core.common.range.mapToReversedPercentageRange
import com.dev.bayan.ibrahim.core.common.range.percentageOfValue
import com.dev.bayan.ibrahim.core.common.range.reversedPercentageOfValue
import kotlin.math.roundToInt

internal fun DrawScope.drawCurrentValueRangePopup(
    alpha: Float,
    start: Float,
    end: Float,
    range: ClosedFloatingPointRange<Float>,
    containerColor: Color,
    textColor: Color,
    textMeasurer: TextMeasurer,
    maxValue: Float,
    actAsIntValue: Boolean,
    style: TextStyle,
    isRtl: Boolean,
) {
    drawCurrentValuePopup(
        alpha = alpha,
        currentValue = start,
        range = range,
        containerColor = containerColor,
        textColor = textColor,
        textMeasurer = textMeasurer,
        maxValue = maxValue,
        actAsIntValue = actAsIntValue,
        style = style,
        isRtl = isRtl
    )
    drawCurrentValuePopup(
        alpha = alpha,
        currentValue = end,
        range = range,
        containerColor = containerColor,
        textColor = textColor,
        textMeasurer = textMeasurer,
        maxValue = maxValue,
        actAsIntValue = actAsIntValue,
        style = style,
        isRtl = isRtl
    )
}

internal fun DrawScope.drawCurrentValuePopup(
    alpha: Float,
    currentValue: Float,
    range: ClosedFloatingPointRange<Float>,
    containerColor: Color,
    textColor: Color,
    textMeasurer: TextMeasurer,
    maxValue: Float,
    actAsIntValue: Boolean,
    style: TextStyle,
    isRtl: Boolean,
) {
    val progress =
        if (isRtl) currentValue.mapToReversedPercentageRange(range) else currentValue.mapToPercentageRange(
            range
        )
    val thumbPx = 10.dp.toPx()
    val cornerPx = 4.dp.toPx()
    val paddingPx = 4.dp.toPx()
    val triPx = 4.dp.toPx()
    val valueAsString = if ((currentValue * 10).roundToInt() > (maxValue * 10).roundToInt()) {
        "..."
    } else if (actAsIntValue) {
        currentValue.roundToInt().toString()
    } else {
        currentValue.asSimpleString()
    }
    val textLayoutResult = textMeasurer.measure(
        text = valueAsString,
        style = style.copy(color = textColor.copy(alpha)),
    )
    val textSize = textLayoutResult.size
    val rectSize = Size(textSize.width + paddingPx * 2, textSize.height + paddingPx * 2)
    val centerOffset = Offset(x = (size.width - 2 * thumbPx) * progress + thumbPx, -triPx)
    val textTopLeft = Offset(
        x = centerOffset.x - textSize.width / 2,
        y = centerOffset.y - textSize.height / 2,
    )
    val rectTopLeft = textTopLeft - Offset(paddingPx, paddingPx)

    val path = Path()
    (rectTopLeft + Offset((rectSize.width - triPx) / 2, rectSize.height)).also {
        path.moveTo(it.x, it.y)
    }
    (rectTopLeft + Offset((rectSize.width + triPx) / 2, rectSize.height)).also {
        path.lineTo(it.x, it.y)
    }
    (rectTopLeft + Offset(rectSize.width / 2, rectSize.height + triPx / 2)).also {
        path.lineTo(it.x, it.y)
    }
    path.close()
    drawRoundRect(
        color = containerColor,
        topLeft = rectTopLeft,
        size = rectSize,
        cornerRadius = CornerRadius(cornerPx, cornerPx),
        alpha = alpha,
    )
    drawPath(path = path, color = containerColor, alpha = alpha, style = Fill)
    drawText(
        textLayoutResult = textLayoutResult,
        topLeft = textTopLeft,
    )
}

@Composable
internal fun RangeLabel(
    modifier: Modifier = Modifier,
    value: Float?,
) {
    Text(
        modifier = modifier,
        text = value?.asSimpleString() ?: "..."
    )
}
