package com.dev.bayan.ibrahim.feature.library.data.repository.db.components.lastEditDate

import kotlinx.coroutines.flow.Flow
import com.dev.bayan.ibrahim.feature.library.data.repository.db.*


/**
 * this is part of [LibraryDbRepo], this sub repo provides data for the last edit date of words
 *
 * 4) last edit date of word (add date of latest word) // TODO save date of deleted word to be last edit date
 * this data is provided by [getWordLastEditDate]
 *
 * @see LibraryDbIntroRepo
 * @see LibraryDbWordsRepo
 */
interface LibraryDbWordLastEditDateRepo {
    /**
     * returns last edit date of words formatted to a string
     */
    fun getWordLastEditDate(): Flow<Long?>
}