package com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.icons_catalog

import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicVector
import com.dev.bayan.ibrahim.core.ui.components.labled_icon.LabeledIconData
import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.icons_catalog.EditorIconsCatalog
import com.dev.bayan.ibrahim.feature.ui.R
import com.dev.bayan.ibrahim.core.ui.R as UiRes

/**
 * used in steps in editor screen and icon catalog store information for some thing by icon and description
 * @property icon resource value of icon
 * @property label resource value of description
 * @see WordEditorSteps
 * @see EditorIconsCatalog
 */

internal enum class IconsCatalog(
    override val icon: JaArDynamicVector,
    override val label: JaArDynamicString,
) : LabeledIconData {
    NEW(
        icon = JaArDynamicVector.Res(UiRes.drawable.ic_new_item),
        label = JaArDynamicString.StrRes(UiRes.string.new_)
    ),
    IN_LIBRARY(
        icon = JaArDynamicVector.Res(UiRes.drawable.ic_library_outline),
        label = JaArDynamicString.StrRes(R.string.in_library)
    ),
    TRANSLATION(
        icon = JaArDynamicVector.Res(UiRes.drawable.ic_language),
        label = JaArDynamicString.StrRes(R.string.translation)
    ),
}