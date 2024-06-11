package com.dev.bayan.ibrahim.core.common.word_db_group_sort

/**
 * this is the options of sorting a word inside each group
 * @param contextualGroup this is the group when makes this string excluded from options,
 * like if i group words by [WordsGroupBy.LANGUAGE] then i can't resort each group by [WordsGroupSortBy.LANGUAGE]
 */
enum class WordsGroupSortBy(
    val contextualGroup: WordsGroupBy?,
) {
    LANGUAGE(WordsGroupBy.LANGUAGE),
    TYPE(WordsGroupBy.TYPE),
    WORD(null);
    // todo add extra sorting options

    companion object Companion
}