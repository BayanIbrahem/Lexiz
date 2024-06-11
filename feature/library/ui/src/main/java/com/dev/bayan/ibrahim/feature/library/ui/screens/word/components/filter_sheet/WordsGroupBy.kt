package com.dev.bayan.ibrahim.feature.library.ui.screens.word.components.filter_sheet

import com.dev.bayan.ibrahim.core.common.word_db_group_sort.WordsGroupBy
import com.dev.bayan.ibrahim.core.common.word_db_group_sort.WordsGroupBy.*
import com.dev.bayan.ibrahim.core.ui.components.action.selectable.JaArSelectableItem
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.core.ui.R
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

internal val WordsGroupBy.label: JaArDynamicString
    get() = when (this) {
        LANGUAGE -> JaArDynamicString.PluralRes(R.plurals.language, 1)
        CATEGORY -> JaArDynamicString.PluralRes(R.plurals.category, 1)
        MEANING -> JaArDynamicString.PluralRes(R.plurals.meaning, 1)
        TYPE -> JaArDynamicString.PluralRes(R.plurals.type, 1)
        NONE -> JaArDynamicString.StrRes(R.string.none)
    }

@JvmName("WordsGroupByAsSelectable")
internal fun WordsGroupBy.asSelectable(): JaArSelectableItem {
    return JaArSelectableItem(
        id = ordinal.toLong(),
        label = label,
    )
}

internal val Companion.selectableEntries: PersistentList<JaArSelectableItem>
    get() = entries.map { it.asSelectable() }.toPersistentList()