package com.dev.bayan.ibrahim.feature.library.data.repository.db.components.words.delete

/**
 * deleting stuff in the content type screen
 * delete words stuff
 */
interface LibraryDbWordDeleteRepo {
    suspend fun contentDeleteWords(ids: Set<Long>): Int
}