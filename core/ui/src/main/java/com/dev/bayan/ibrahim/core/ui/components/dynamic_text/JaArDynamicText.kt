package com.dev.bayan.ibrahim.core.ui.components.dynamic_text

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit

@Composable
fun JaArDynamicText(
    @StringRes textRes: Int,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    style: TextStyle = LocalTextStyle.current
) {
    JaArDynamicText(
        text = stringResource(id = textRes),
        modifier = modifier,
        color = color,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        style = style
    )
}

/**
 * take text as string (see [JaArDynamicText])
 * this is copy of the normal text, but it shrinks the size of text if there is an text overflow
 */
@Composable
fun JaArDynamicText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontWeight: FontWeight? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    style: TextStyle = LocalTextStyle.current
) {
    // TODO improve performance
    BoxWithConstraints(
        modifier = modifier,
    ) {
        var readyToDraw by remember(constraints.maxWidth) {
            mutableStateOf(false)
        }
        var dynamicStyle by remember {
            mutableStateOf(style)
        }
        Log.d("dynamicText", "readyToDraw $readyToDraw")
        Text(
            text = text,
            modifier = Modifier.drawWithContent {
                Log.d("dynamicText", "draw with content $readyToDraw")
                if (readyToDraw) {
                    drawContent()
                }
            },
            color = color,
            letterSpacing = letterSpacing,
            fontWeight = fontWeight,
            textDecoration = textDecoration,
            textAlign = textAlign,
            lineHeight = lineHeight,
            softWrap = softWrap,
            maxLines = maxLines,
            minLines = minLines,
            onTextLayout = {
                Log.d("dynamicText", "readyToDraw $readyToDraw, overflow ${it.hasVisualOverflow}")
                if (it.hasVisualOverflow) {
                    dynamicStyle = dynamicStyle.copy(fontSize = dynamicStyle.fontSize * 0.9f)
                } else {
                    readyToDraw = true
                }

            },
            style = dynamicStyle,
        )
    }
}
