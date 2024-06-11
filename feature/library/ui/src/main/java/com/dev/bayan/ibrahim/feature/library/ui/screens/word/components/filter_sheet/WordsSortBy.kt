package com.dev.bayan.ibrahim.feature.library.ui.screens.word.components.filter_sheet

import com.dev.bayan.ibrahim.core.common.word_db_group_sort.WordsGroupSortBy
import com.dev.bayan.ibrahim.core.common.word_db_group_sort.WordsGroupSortBy.*
import com.dev.bayan.ibrahim.core.ui.components.action.selectable.JaArSelectableItem
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.core.ui.R
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

internal val WordsGroupSortBy.label: JaArDynamicString
    get() = when (this) {
        LANGUAGE -> JaArDynamicString.PluralRes(R.plurals.language, 1)
        TYPE -> JaArDynamicString.PluralRes(R.plurals.type, 1)
        WORD -> JaArDynamicString.PluralRes(R.plurals.word, 1)
    }

@JvmName("WordsGroupSortByAsSelectable")
internal fun WordsGroupSortBy.asSelectable(): JaArSelectableItem {
    return JaArSelectableItem(
        id = ordinal.toLong(),
        label = label,
    )
}

internal val Companion.selectableEntries: PersistentList<JaArSelectableItem>
    get() = entries.map { it.asSelectable() }.toPersistentList()
