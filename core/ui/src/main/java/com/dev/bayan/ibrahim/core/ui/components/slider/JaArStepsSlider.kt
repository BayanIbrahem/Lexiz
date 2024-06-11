package com.dev.bayan.ibrahim.core.ui.components.slider

import androidx.annotation.Size
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.ui.components.divider.DividerWithSubtitle
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlin.math.roundToInt

@Composable
fun JaArStepsSlider(
    modifier: Modifier = Modifier,
    sliderValue: Int,
    @Size(min = 2)
    valuesLabels: PersistentList<JaArDynamicString>,
    title: JaArDynamicString,
    hint: JaArDynamicString,
    weight: Float = 0.66f,
    onValueChange: (Int) -> Unit,
) {
    Row(modifier = modifier) {
        val steps = valuesLabels.count()
        Column(
            modifier = Modifier.weight(weight),
        ) {
            Text(
                text = "${title.value}\n(${valuesLabels[sliderValue].value})",
                style = MaterialTheme.typography.titleMedium
            )
            Slider(
                value = sliderValue.toFloat(),
                onValueChange = { onValueChange(it.roundToInt()) },
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    activeTrackColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    inactiveTrackColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.38f),
                ),
                steps = steps - 2,
                valueRange = 0f..steps.dec().toFloat()
            )
            DividerWithSubtitle(
                horizontalPadding = 0.dp,
                modifier = Modifier.alpha(0.75f),
                subtitle = hint.value,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textColor = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }
        Spacer(modifier = Modifier.weight(maxOf(1 - weight, 0.01f)))
    }

}

@Preview(showBackground = false)
@Composable
private fun JaArSlider() {
    var sliderValue by remember { mutableIntStateOf(0) }
    Surface {
        Column {
            JaArStepsSlider(
                title = JaArDynamicString.Str("Title"),
                hint = JaArDynamicString.Str("hint "),
                sliderValue = sliderValue,
                valuesLabels = List(5) {
                    JaArDynamicString.Str("value $it")
                }.toPersistentList(),
                onValueChange = { sliderValue = it }
            )
        }
    }
}
