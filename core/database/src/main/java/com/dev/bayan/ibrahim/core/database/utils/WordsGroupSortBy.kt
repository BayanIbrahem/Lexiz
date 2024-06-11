package com.dev.bayan.ibrahim.core.database.utils

import com.dev.bayan.ibrahim.core.common.word_db_group_sort.WordsGroupSortBy
import com.dev.bayan.ibrahim.core.common.word_db_group_sort.WordsGroupSortBy.*

val WordsGroupSortBy.columnName: String
    get() = when (this) {
        LANGUAGE -> DB_NAMES.WORD.language_code
        TYPE -> DB_NAMES.TYPE.name
        WORD -> DB_NAMES.WORD.name
    }