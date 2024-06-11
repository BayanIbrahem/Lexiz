package com.dev.bayan.ibrahim.feature.library.ui.screens.editor.utils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicVector
import com.dev.bayan.ibrahim.core.ui.R as UiRes
import com.dev.bayan.ibrahim.feature.ui.R as LibRes

/**
 * type of content of input field (which set the leading icon in this field)
 */
internal enum class EditorInputFieldValueType(
    @StringRes val nameRes: Int,
    @DrawableRes val iconRes: Int,
) {
    NEW(UiRes.string.new_, UiRes.drawable.ic_new_item),
    TRANSLATION(LibRes.string.translation, UiRes.drawable.ic_language),
    EXISTED(LibRes.string.in_library, UiRes.drawable.ic_library_outline),
    NONE(UiRes.string.none, UiRes.drawable.ic_new_item),
}