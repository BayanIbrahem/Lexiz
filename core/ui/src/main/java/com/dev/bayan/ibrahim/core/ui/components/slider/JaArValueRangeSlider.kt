package com.dev.bayan.ibrahim.core.ui.components.slider

import androidx.annotation.IntRange
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.common.range.stepsExtends
import com.dev.bayan.ibrahim.core.ui.utils.animation.animSliderLabel


@Composable
fun JaArValueRangeSlider(
    modifier: Modifier = Modifier,
    sliderValue: ClosedFloatingPointRange<Float>,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
    @IntRange(from = 3)
    steps: Int,
    addInfinityAsLastValue: Boolean = false,
    actAsIntValue: Boolean = false,
    inputValueRange: ClosedFloatingPointRange<Float>,
) {
    var valueIsChanging by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (valueIsChanging) 1f else 0f,
        animationSpec = tween(animSliderLabel, easing = FastOutSlowInEasing), label = ""
    )
    val valueRange = inputValueRange.run {
        if (addInfinityAsLastValue) {
            inputValueRange.stepsExtends(steps, 1)
        } else {
            inputValueRange
        }
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom
    ) {
        RangeLabel(value = valueRange.start)
        val thumbColor = MaterialTheme.colorScheme.onPrimaryContainer
        val onThumbColor = MaterialTheme.colorScheme.onPrimary
        val textMeasurer = rememberTextMeasurer()
        val textStyle = MaterialTheme.typography.labelSmall
        val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
        RangeSlider(
            modifier = Modifier
               .weight(1f)
               .drawBehind {
                  drawCurrentValueRangePopup(
                     alpha = alpha,
                     start = sliderValue.start,
                     end = sliderValue.endInclusive,
                     range = valueRange,
                     containerColor = thumbColor,
                     textColor = onThumbColor,
                     textMeasurer = textMeasurer,
                     maxValue = inputValueRange.endInclusive,
                     actAsIntValue = actAsIntValue,
                     style = textStyle,
                     isRtl = isRtl,
                  )
               },
            value = sliderValue,
            onValueChange = {
                onValueChange(it)
                valueIsChanging = true
            },
            onValueChangeFinished = {
                valueIsChanging = false
            },
            colors = SliderDefaults.colors(
                thumbColor = thumbColor,
                activeTrackColor = thumbColor,
                inactiveTrackColor = thumbColor.copy(alpha = 0.38f),
            ),
            steps = steps - if (addInfinityAsLastValue) 1 else 2,
            valueRange = valueRange,
        )
        RangeLabel(value = if (addInfinityAsLastValue) null else valueRange.endInclusive)
    }
}

@Preview(showBackground = false)
@Composable
private fun JaArRangeSlider() {
    var sliderValue by remember { mutableStateOf(0f..1f) }
    Surface(
        modifier = Modifier
           .height(200.dp)
           .fillMaxWidth()
    ) {
        JaArValueRangeSlider(
            modifier = Modifier,
            sliderValue = sliderValue,
            onValueChange = { sliderValue = it },
            inputValueRange = 1f..100f,
            steps = 100,
        )
    }
}
