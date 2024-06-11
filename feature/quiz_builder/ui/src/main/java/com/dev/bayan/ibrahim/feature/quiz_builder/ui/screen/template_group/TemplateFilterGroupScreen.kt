package com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.template_group


import androidx.activity.compose.BackHandler
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.dev.bayan.ibrahim.core.ui.components.action.JaArAction
import com.dev.bayan.ibrahim.core.ui.components.action.JaArActionStyle
import com.dev.bayan.ibrahim.core.ui.components.action.clicable.JaArClickable
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.R
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.core.BuilderSetupSubscreenTemplate
import kotlinx.collections.immutable.PersistentSet
import com.dev.bayan.ibrahim.core.ui.R as UiRes

@Composable
internal fun TemplateFilterGroupScreen(
    modifier: Modifier = Modifier,
    shortScreen: Boolean,
    uiState: TemplateFilterGroupUiState,
    onEvent: (TemplateFilterGroupEvent) -> Unit,
    quizSelectedIds: PersistentSet<Long>,
) {
    val onClickGroup: (Int) -> Unit by remember {
        derivedStateOf { { onEvent(TemplateFilterGroupEvent.OnClickGroup(it)) } }
    }
    val onEditGroup: (Int) -> Unit by remember {
        derivedStateOf { { onEvent(TemplateFilterGroupEvent.OnEditGroup(it)) } }
    }
    val onRemoveGroup: (Int) -> Unit by remember {
        derivedStateOf { { onEvent(TemplateFilterGroupEvent.OnRemoveGroup(it)) } }
    }
    val onCancel: () -> Unit by remember {
        derivedStateOf { { onEvent(TemplateFilterGroupEvent.OnCancel) } }
    }
    BackHandler(onBack = onCancel)
    BuilderSetupSubscreenTemplate(
        modifier = modifier,
        shortScreen = shortScreen,
        title = {
            Text(
                text = stringResource(R.string.template_filter_groups)
            )
        },
        description = R.string.quiz_filterGroup_description,
        flags = {},
        primaryAction = {
            JaArClickable(
                type = JaArAction.Clickable.TEXT_BUTTON,
                style = JaArActionStyle.PRIMARY_CONTAINER,
                label = JaArDynamicString.StrRes(UiRes.string.cancel),
                onClick = onCancel,
            )
        },
        secondaryAction = {},
        tertiaryAction = {},
    ) {
        TemplateFilterGroupContent(
            groups = uiState.groups,
            onClickGroup = onClickGroup,
            onEditGroup = onEditGroup,
            onRemoveGroup = onRemoveGroup,
            quizSelectedIds = quizSelectedIds,
        )
    }
}
