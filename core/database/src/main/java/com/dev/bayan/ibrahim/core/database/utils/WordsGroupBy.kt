package com.dev.bayan.ibrahim.core.database.utils

import com.dev.bayan.ibrahim.core.common.word_db_group_sort.WordsGroupBy
import com.dev.bayan.ibrahim.core.common.word_db_group_sort.WordsGroupBy.*
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.WORD

val WordsGroupBy.columnName: String
    get() = when (this) {
        LANGUAGE -> WORD.language_code
        CATEGORY -> DB_NAMES.CATEGORY.name
        MEANING -> WORD.meaning_id
        TYPE -> DB_NAMES.TYPE.name
        NONE -> WORD.name
    }