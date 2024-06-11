package com.dev.bayan.ibrahim.feature.library.domain.model.words

import com.dev.bayan.ibrahim.core.common.model.CategoryItem
import com.dev.bayan.ibrahim.core.common.word_db_group_sort.WordsGroupBy
import com.dev.bayan.ibrahim.core.common.model.LanguageItem
import com.dev.bayan.ibrahim.core.common.model.TypeItem
import kotlinx.datetime.Instant


/**
 * this represent the word model
 * @param groupName the name of the group which the word is coming from, like if i grouped items by
 * [WordsGroupBy.LANGUAGE] then the [groupName] will be the language name
 * @param wordId the id of the word
 * @param name name of the word if existed
 * @param description description of the word if existed
 * @param language language of the word of type [LanguageItem]
 * @param meaningId id of its meaning
 * @param categories list of categories of this word
 * @param type list of types of this word
 * @param addDate adding date of this word
 * @param lastEditDate lastUpdateDate of the word
 */
data class Word(
    val groupId: Long,
    val groupName: String?,
    val wordId: Long,

    val name: String?,
    val description: String?,

    val language: LanguageItem,
    val meaningId: Long,

    val categories: List<CategoryItem>,
    val type: TypeItem,

    val addDate: Instant,
    val lastEditDate: Instant,

//    todo, add this data for words...
//    val quizzesCount: Int,
//    val failureTestsCount: Int,
//    val successTimesCount: Int,
) {
    val id: String = String.format("%016x%016x", groupId, wordId)
}
