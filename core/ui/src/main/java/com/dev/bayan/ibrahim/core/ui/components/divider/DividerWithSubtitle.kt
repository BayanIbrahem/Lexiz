package com.dev.bayan.ibrahim.core.ui.components.divider


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.ui.utils.toVerticalHorizontalAlignment

@Composable
fun DividerWithSubtitle(
    modifier: Modifier = Modifier,
    subtitle: String,
    subtitleAlignment: Alignment = Alignment.BottomStart,
    horizontalPadding: Dp = 16.dp,
    thickness: Dp = DividerDefaults.Thickness,
    color: Color = DividerDefaults.color,
    textColor: Color = DividerDefaults.color,
    textStyle: TextStyle = MaterialTheme.typography.titleSmall,
) {
    val verticalAlignment: Alignment.Vertical
    val horizontalAlignment: Alignment.Horizontal
    subtitleAlignment.toVerticalHorizontalAlignment().also { (v, h) ->
        verticalAlignment = v
        horizontalAlignment = h
    }
    Column(
        modifier = modifier.padding(horizontal = horizontalPadding),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = horizontalAlignment,
    ) {
        if (verticalAlignment == Alignment.Top) {
            Text(
                text = subtitle,
                style = textStyle,
                color = textColor
            )
        }
        HorizontalDivider(thickness = thickness, color = color)
        if (verticalAlignment == Alignment.Bottom) {
            Text(
                text = subtitle,
                style = textStyle,
                color = textColor
            )
        }
    }
}

