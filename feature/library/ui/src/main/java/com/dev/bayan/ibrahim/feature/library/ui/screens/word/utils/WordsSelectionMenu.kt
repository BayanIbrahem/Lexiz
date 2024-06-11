package com.dev.bayan.ibrahim.feature.library.ui.screens.word.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import com.dev.bayan.ibrahim.core.ui.R
import com.dev.bayan.ibrahim.core.ui.components.action.selectable.JaArSelectable
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicVector

internal enum class WordsSelectionMenu(
    override val leadingIcon: JaArDynamicVector,
    override val label: JaArDynamicString,
    override val supportingText: JaArDynamicString,
    val cancelSelectionMode: Boolean,
) : JaArSelectable {
    DELETE(
        leadingIcon = JaArDynamicVector.Vector(Icons.Filled.Delete),
        label = JaArDynamicString.StrRes(R.string.delete),
        supportingText = JaArDynamicString.StrRes(R.string.delete),
        cancelSelectionMode = true
    ),
    UPDATE(
        leadingIcon = JaArDynamicVector.Vector(Icons.Filled.Edit),
        label = JaArDynamicString.StrRes(R.string.update),
        supportingText = JaArDynamicString.StrRes(R.string.update),
        cancelSelectionMode = true
    ),
    DESELECT(
        leadingIcon = JaArDynamicVector.Res(R.drawable.deselect),
        label = JaArDynamicString.StrRes(R.string.deselect),
        supportingText = JaArDynamicString.StrRes(R.string.deselect),
        cancelSelectionMode = false
    );

    override val id: Long = this.ordinal.toLong()
}