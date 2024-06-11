package com.dev.bayan.ibrahim.core.ui.components.action.selectable.selecatable_group

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.ui.R
import com.dev.bayan.ibrahim.core.ui.components.action.JaArAction.Checkable
import com.dev.bayan.ibrahim.core.ui.components.action.checkable.JaArCheckable
import com.dev.bayan.ibrahim.core.ui.components.action.selectable.JaArSelectable
import com.dev.bayan.ibrahim.core.ui.components.action.selectable.JaArSelectableHeader
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicVector
import com.dev.bayan.ibrahim.core.ui.res.thereModelInModelResource
import com.dev.bayan.ibrahim.core.ui.res.thereNoModel
import com.dev.bayan.ibrahim.core.ui.utils.animation.fadeVerticalExpandEnterTransition
import com.dev.bayan.ibrahim.core.ui.utils.animation.fadeVerticalShrinkExitTransition
import kotlinx.collections.immutable.PersistentList


/**
 * @param options list of options and its selection state
 * @param leadingIcon optional leading icon
 * @param label one line text that will be showed as a title of the group (next to [leadingIcon] if exists)
 * @param supportingText optional extra text shows under the [label]
 * @param collapsable if true then the group can hide its content or show it by clicking on the
 * header
 * @param itemType shape of the selecting item,
 * */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <Selectable> JaArSelectableGroup(
    modifier: Modifier = Modifier,
    fillMaxWidth: Boolean = true,
    options: PersistentList<Pair<Selectable, Boolean>>,
    leadingIcon: JaArDynamicVector? = null,
    label: JaArDynamicString,
    title: JaArDynamicString? = null,
    supportingText: JaArDynamicString? = null,
    emptyListText: JaArDynamicString = JaArDynamicString.Builder {
        thereNoModel(item = R.plurals.item)
    },
    collapsable: Boolean = true,
    itemType: Checkable,
    isList: Boolean = false,
    onSelectItem: (index: Int, wasSelected: Boolean) -> Unit,
    optionEnability: (Selectable) -> Boolean = { true },
) where Selectable : JaArSelectable {
    var expanded by rememberSaveable { mutableStateOf(!collapsable) }
    Column(
        modifier = modifier,
    ) {
        JaArSelectableHeader(
            title = title,
            fillMaxWidth = fillMaxWidth,
            leadingIcon = leadingIcon,
            label = label,
            trailingIcon = JaArDynamicVector.Vector(
                value = if (expanded) {
                    Icons.Filled.ArrowDropDown
                } else {
                    Icons.AutoMirrored.Filled.ArrowRight
                }
            ),
            supportingText = supportingText,
            onClick = if (collapsable) {
                { expanded = !expanded }
            } else null
        )

        AnimatedVisibility(
            visible = expanded,
            enter = fadeVerticalExpandEnterTransition(Alignment.Top),
            exit = fadeVerticalShrinkExitTransition(Alignment.Top)
        ) {
            if (options.isEmpty()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = emptyListText.value,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                )
            } else {
                FlowRow(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    options.forEachIndexed { i, item ->
                        JaArCheckable(
                            modifier = if (isList) Modifier.fillMaxWidth() else Modifier,
                            checked = item.second,
                            enabled = optionEnability(item.first),
                            label = item.first.label,
                            type = itemType,
                            onClick = {
                                onSelectItem(i, it)
                            }
                        )
                    }
                }
            }
        }
    }
}