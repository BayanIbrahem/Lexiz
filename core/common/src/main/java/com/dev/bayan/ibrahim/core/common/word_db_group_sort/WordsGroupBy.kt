package com.dev.bayan.ibrahim.core.common.word_db_group_sort

import kotlinx.collections.immutable.PersistentList

/**
 * this contains the group by options for the words when i get them from database
 */
enum class WordsGroupBy {
    LANGUAGE,
    CATEGORY,
    MEANING,
    TYPE,
    NONE;

    // todo, add another grouping options like day, or month
    companion object Companion {
    }
}