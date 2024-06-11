package com.dev.bayan.ibrahim.core.ui.components.action.selectable

import com.dev.bayan.ibrahim.core.common.model.INVALID_ID
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicVector

/**
 * data for item the selectable list
 * @property label string value as a label of the option
 * @property enabled if true then the value enabled
 * @property leadingIcon leading icon for the item
 * @property trailingIcon trailing icon for the item
 * @property supportingText optional additional text
 * @property tooltip optional tooltip on long click
 */
interface JaArSelectable {
    val label: JaArDynamicString
    val id: Long get() = INVALID_ID
    val leadingIcon: JaArDynamicVector? get() = null
    val trailingIcon: JaArDynamicVector? get() = null
    val supportingText: JaArDynamicString? get() = null
    val disabledSupportingText: JaArDynamicString? get() = supportingText
    val tooltip: JaArDynamicString? get() = null
}

data class JaArSelectableItem(
    override val label: JaArDynamicString,
    override val id: Long = INVALID_ID,
    override val leadingIcon: JaArDynamicVector? = null,
    override val trailingIcon: JaArDynamicVector? = null,
    override val supportingText: JaArDynamicString? = null,
    override val disabledSupportingText: JaArDynamicString? = supportingText,
    override val tooltip: JaArDynamicString? = null,
) : JaArSelectable