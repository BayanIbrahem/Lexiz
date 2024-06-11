package com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.core

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.ui.components.container.JaArContainer
import com.dev.bayan.ibrahim.core.ui.components.container.JaArContainerType
import com.dev.bayan.ibrahim.core.ui.utils.animation.fadeVerticalExpandEnterTransition
import com.dev.bayan.ibrahim.core.ui.utils.animation.fadeVerticalShrinkExitTransition

@Composable
internal fun BuilderSetupSubscreenTemplate(
    modifier: Modifier = Modifier,
    shortScreen: Boolean,

    title: @Composable () -> Unit,
    @StringRes description: Int,

    flags: @Composable ColumnScope.() -> Unit,
    primaryAction: @Composable RowScope.() -> Unit,
    secondaryAction: @Composable RowScope.() -> Unit,
    tertiaryAction: @Composable RowScope.() -> Unit,

    content: @Composable ColumnScope.() -> Unit,
) {
    JaArContainer(type = JaArContainerType.PRIMARY, gradient = true) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                title()
                AnimatedVisibility(
                    visible = shortScreen.not(),
                    enter = fadeVerticalExpandEnterTransition(Alignment.Top),
                    exit = fadeVerticalShrinkExitTransition(Alignment.Top),
                ) {
                    Text(
                        text = stringResource(description),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            content()
            Column {
                flags()
                Row {
                    tertiaryAction()
                    Spacer(modifier = Modifier.weight(1f))
                    secondaryAction()
                    primaryAction()

                }
            }
        }
    }
}
