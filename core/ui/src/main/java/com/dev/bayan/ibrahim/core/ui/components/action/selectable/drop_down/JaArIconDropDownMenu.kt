package com.dev.bayan.ibrahim.core.ui.components.action.selectable.drop_down

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.ui.components.action.JaArAction
import com.dev.bayan.ibrahim.core.ui.components.action.JaArActionStyle
import com.dev.bayan.ibrahim.core.ui.components.action.clicable.JaArClickable
import com.dev.bayan.ibrahim.core.ui.components.action.selectable.JaArSelectable
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicVector
import kotlinx.collections.immutable.PersistentList


/**
 * in icon button that shows a drop down menu on click
 */
@Composable
fun <Selectable> JaArIconDropDownMenu(
    modifier: Modifier = Modifier,
    icon: JaArDynamicVector,
    selected: Selectable?,
    options: PersistentList<Selectable>,
    filledIconButton: Boolean = false,
    style: JaArActionStyle,
    enabled: Boolean = true,
    onSelectItem: (Selectable, Boolean) -> Unit,
    optionEnability: (Selectable) -> Boolean,
) where Selectable : JaArSelectable {
    var show by rememberSaveable { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .size(48.dp)
            .then(modifier),
    ) {
        JaArClickable(
            type = JaArAction.Clickable.ICON_BUTTON,
            label = JaArDynamicString.Blank,
            icon = icon,
            filledIconButton = filledIconButton,
            style = style,
            enabled = enabled,
            onClick = { show = true }
        )
        JaArDropDownMenu(
            options = options,
            selected = selected,
            onSelectItem = onSelectItem,
            show = show,
            optionEnability = optionEnability,
        ) { show = false }
    }
}
