package com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.quiz_setup.core.top_app_bar


import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dev.bayan.ibrahim.core.ui.components.action.JaArActionStyle
import com.dev.bayan.ibrahim.core.ui.components.action.selectable.drop_down.JaArIconDropDownMenu
import com.dev.bayan.ibrahim.core.ui.components.container.JaArContainer
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.core.ui.components.topAppBar.JaArTopAppBar
import com.dev.bayan.ibrahim.core.ui.components.container.JaArContainerType
import com.dev.bayan.ibrahim.core.ui.components.dynamic.asDynamicVector
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.util.BuilderItemSaveStatus
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.R
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.utils.top_app_bar.QuizTopAppBarDropDownMenu
import kotlinx.collections.immutable.toPersistentList

@Composable
internal fun QuizTopAppBar(
    modifier: Modifier = Modifier,
    isTemplateQuiz: Boolean,
    savingState: BuilderItemSaveStatus,
    consumeTemplate: Boolean,
    onClickMenuItem: (QuizTopAppBarDropDownMenu) -> Unit,
    onNavigateUp: () -> Unit,
) {
    JaArContainer(
        type = JaArContainerType.PRIMARY,
        gradient = true,
    ) {
        JaArTopAppBar(
            modifier = modifier,
            title = JaArDynamicString.StrRes(R.string.quiz_setup),
            subtitle = JaArDynamicString.StrRes(savingState.resName),
            isSelectionMode = false,
            normalActions = {
                QuizActions(
                    isTemplate = isTemplateQuiz,
                    consumeTemplate = consumeTemplate,
                    onClickMenuItem = onClickMenuItem
                )
            },
            selectionActions = null,
            onNavigateUp = onNavigateUp,
        )
    }
}


@Composable
private fun RowScope.QuizActions(
    modifier: Modifier = Modifier,
    isTemplate: Boolean,
    consumeTemplate: Boolean,
    onClickMenuItem: (QuizTopAppBarDropDownMenu) -> Unit,
) {
    JaArIconDropDownMenu(
        modifier = modifier,
        icon = Icons.Filled.MoreVert.asDynamicVector(),
        selected = if (consumeTemplate) QuizTopAppBarDropDownMenu.CONSUME else null,
        options = QuizTopAppBarDropDownMenu.entries.toPersistentList(),
        style = JaArActionStyle.SECONDARY,
        onSelectItem = { it, _ ->
            onClickMenuItem(QuizTopAppBarDropDownMenu.entries[it.id.toInt()])
        },
        optionEnability = {
            it.validOnNew || isTemplate
        }
    )
}