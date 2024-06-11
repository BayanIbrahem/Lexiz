package com.dev.bayan.ibrahim.core.ui.components.fab


import androidx.annotation.FloatRange
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.ui.R
import com.dev.bayan.ibrahim.core.ui.components.container.JaArContainerFabOption
import com.dev.bayan.ibrahim.core.ui.components.container.JaArContainerType
import com.dev.bayan.ibrahim.core.ui.components.container.JaArFabType
import com.dev.bayan.ibrahim.core.ui.components.container.JaArFabType.EXTENDED
import com.dev.bayan.ibrahim.core.ui.components.container.JaArFabType.LARGE
import com.dev.bayan.ibrahim.core.ui.components.container.JaArFabType.NORMAL
import com.dev.bayan.ibrahim.core.ui.components.container.JaArFabType.SMALL
import com.dev.bayan.ibrahim.core.ui.components.modifier_draw.autoMirroredDrawBehind
import com.dev.bayan.ibrahim.core.ui.utils.animation.animContainerFab
import kotlin.enums.EnumEntries


@Composable
fun <Option> JaArFab(
    modifier: Modifier = Modifier,
    entries: EnumEntries<Option>,
    collapsedFabType: JaArFabType = SMALL,
    expandedFabType: JaArFabType = NORMAL,
    collapseOnClick: Boolean = true,
    containerType: JaArContainerType,
    isScrollingUp: Boolean,
    background: Color,
    onClickOption: (Option) -> Unit,
) where Option : Enum<Option>, Option : JaArContainerFabOption {
    if (entries.isNotEmpty()) {
        val containerColor = containerType.containerColor
        val verticalScale by remember {
            mutableStateOf(Animatable(1f))
        }
        var expanded by rememberSaveable {
            mutableStateOf(false)
        }
        LaunchedEffect(key1 = isScrollingUp) {
            if (isScrollingUp) {
                expanded = false
            }
            verticalScale.animateTo(
                targetValue = if (isScrollingUp) 0f else 1f,
                animationSpec = tween(
                    durationMillis = animContainerFab,
                    easing = FastOutSlowInEasing
                )
            )
        }

        val transactionSpec = tween<Dp>(animContainerFab)
        val expandedTransaction = updateTransition(targetState = expanded, label = "fab expanded")

        val height by expandedTransaction.animateDp(
            transitionSpec = { transactionSpec }, label = "",
        ) {
            if (it) {
                expandedFabType.height
            } else {
                collapsedFabType.height
            }
        }

        val minWidth by expandedTransaction.animateDp(
            transitionSpec = { transactionSpec }, label = "",
        ) {
            if (it) {
                expandedFabType.minWidth
            } else {
                collapsedFabType.minWidth
            }
        }

        val maxWidth by expandedTransaction.animateDp(
            transitionSpec = { transactionSpec }, label = "",
        ) {
            if (it) {
                expandedFabType.maxWidth
            } else {
                collapsedFabType.maxWidth
            }
        }

        val buttonCornerRadius by expandedTransaction.animateDp(
            transitionSpec = { transactionSpec }, label = "",
        ) {
            if (it) {
                expandedFabType.cornerRadius
            } else {
                collapsedFabType.cornerRadius
            }
        }

        val iconSize by expandedTransaction.animateDp(
            transitionSpec = { transactionSpec }, label = "",
        ) {
            if (it) {
                expandedFabType.iconSize
            } else {
                collapsedFabType.iconSize
            }
        }

        val padding by expandedTransaction.animateDp(
            transitionSpec = { transactionSpec }, label = "",
        ) {
            if (it) {
                expandedFabType.padding
            } else {
                collapsedFabType.padding
            }
        }

        val spacedBy = dimensionResource(id = R.dimen.main_container_content_spacedBy)
        val cornerRadius = dimensionResource(id = R.dimen.floating_primary_dialog_corner_size)
        val totalHeight by expandedTransaction.animateDp(
            transitionSpec = { transactionSpec }, label = "",
        ) {
            if (it) {
                expandedFabType.optionBoxHeight(
                    index = entries.count().dec()
                ) - 16.dp - SMALL.height// remove first option height which is the maing
            } else {
                collapsedFabType.height
            }
        }

        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier
               .requiredSizeIn(minWidth, totalHeight, maxWidth, totalHeight)
               .graphicsLayer { scaleX = verticalScale.value }
               .jaArFloatingActionButtonSpacer(
                  color = background,
                  spacedBy = spacedBy,
                  cornerRadius = cornerRadius,
               ),
        ) {
            entries.filterIndexed { index, _ -> index != 0 }.forEachIndexed { i, it ->
                JaArFloatingActionButtonExtraOption(
                    option = it,
                    parentExpandedType = expandedFabType,
                    parentIsExpanded = expanded,
                    containerType = containerType,
                    index = i,
                ) {
                    if (expanded) { // if not expanded then we must not do any thing when click the option
                        if (collapseOnClick) { // if clicking makes the fab collapse then make expanded false
                            expanded = false
                        }
                        onClickOption(it)
                    }
                }
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
                   .requiredSizeIn(minWidth, height, maxWidth, height)
                   .clip(RoundedCornerShape(CornerSize(buttonCornerRadius)))
                   .drawBehind {
                      drawRoundRect(
                         color = containerColor,
                      )
                   }
                   .clickable {
                      if (entries.count() > 1) { // there is more than one choice
                         expanded = !expanded
                      }
                      onClickOption(entries.first())
                   }
                   .padding(padding)

            ) {
                entries.first().run {
                    Icon(
                        modifier = Modifier.size(iconSize),
                        imageVector = icon.vector,
                        contentDescription = tooltip.value,
                    )
                }
            }
        }
    }
}

@Composable
private fun <Option> JaArFloatingActionButtonExtraOption(
    modifier: Modifier = Modifier,
    option: Option,
    parentExpandedType: JaArFabType,
    parentIsExpanded: Boolean,
    containerType: JaArContainerType,
    index: Int,
    onClick: () -> Unit,
) where Option : Enum<Option>, Option : JaArContainerFabOption {
    val endPadding = parentExpandedType.optionEndPaddingOf()
    val width = endPadding + SMALL.minWidth
    val height = parentExpandedType.optionBoxHeight(index)
    val expandedTransaction = updateTransition(targetState = parentIsExpanded, label = "")
    val positionY by expandedTransaction.animateDp(
        transitionSpec = { tween(animContainerFab, easing = FastOutSlowInEasing) }, label = ""
    ) { if (it) 0.dp else height - SMALL.height }
    val alpha by expandedTransaction.animateFloat(
        transitionSpec = { tween(animContainerFab, easing = FastOutSlowInEasing) }, label = ""
    ) { if (it) 1f else 0f }
    Box(
        modifier = modifier
           .size(width, height)
           .padding(end = endPadding),
    ) {
        SmallFloatingActionButton(
            onClick = onClick,
            containerColor = containerType.containerColor,
            contentColor = containerType.onContainerColor,
            elevation = FloatingActionButtonDefaults.elevation(0.dp),
            modifier = Modifier
                .graphicsLayer {
                    translationY = positionY.toPx()
                    this.alpha = alpha
                }
        ) {
            Icon(imageVector = option.icon.vector, contentDescription = option.tooltip.value)
        }
    }
}

private fun JaArFabType.optionEndPaddingOf() = when (this) {
    SMALL -> 0.dp
    NORMAL -> (NORMAL.minWidth - SMALL.minWidth) / 2
    LARGE -> (LARGE.minWidth - SMALL.minWidth) / 2
    EXTENDED -> 0.dp // TODO set end padding correctly
}

private fun JaArFabType.optionBoxHeight(index: Int): Dp {
    val parentHeight = height + 24.dp
    val optionsTotalPadding = 16.dp * index
    val optionsTotalHeight = SMALL.height * index.inc()
    return parentHeight + optionsTotalPadding + optionsTotalHeight
}

@Composable
private fun Modifier.jaArFloatingActionButtonSpacer(
    color: Color,
    @FloatRange(from = 0.0, to = 1.0)
    spacedBy: Dp,
    cornerRadius: Dp,
): Modifier {
    return this.autoMirroredDrawBehind {
        val spacedByPx = spacedBy.toPx()
        val cornerPx = cornerRadius.toPx()

        val pWPx = size.width
        val pHPx = size.height
        val path = Path()
        val startY = size.height - pHPx - spacedByPx - 2 * cornerPx
        path.moveTo(
            x = size.width,
            y = size.height - pHPx - spacedByPx - 2 * cornerPx,
        )
        path.addArc(
            oval = Rect(
                left = size.width - 2 * cornerPx,
                top = startY,
                right = size.width,
                bottom = startY + 2 * cornerPx
            ),
            startAngleDegrees = 0f,
            sweepAngleDegrees = 90f,
        )
        path.relativeLineTo(
            dx = -(pWPx + spacedByPx - cornerPx - cornerPx),
            dy = 0f
        )
        path.addArc(
            oval = Rect(
                left = size.width - (pWPx + spacedByPx),
                top = size.height - pHPx - spacedByPx,
                right = size.width - (pWPx + spacedByPx - 2 * cornerPx),
                bottom = size.height - pHPx - spacedByPx + 2 * cornerPx,
            ),
            startAngleDegrees = -90f,
            sweepAngleDegrees = -90f,
        )
        path.relativeLineTo(
            dx = 0f,
            dy = pHPx + spacedByPx - 2 * cornerPx
        )
        path.lineTo(
            x = size.width,
            y = size.height,
        )
        path.lineTo(
            x = size.width,
            y = size.height - pHPx - spacedByPx - cornerPx,
        )
        path.close()
        drawPath(
            path = path,
            color = color,
            style = Fill,
        )
        path.reset()
        path.moveTo(
            size.width,
            size.height - cornerPx
        )
        path.relativeLineTo(
            dx = -(pWPx + spacedByPx),
            dy = 0f,
        )
        path.addArc(
            oval = Rect(
                left = size.width - (pWPx + spacedByPx + 2 * cornerPx),
                top = size.height - 2 * cornerPx,
                right = size.width - (pWPx + spacedByPx),
                bottom = size.height,
            ),
            startAngleDegrees = 0f,
            sweepAngleDegrees = 90f,
        )
        path.lineTo(
            x = size.width,
            y = size.height,
        )
        path.close()
        drawPath(
            path = path,
            color = color,
            style = Fill,
        )
    }
}

