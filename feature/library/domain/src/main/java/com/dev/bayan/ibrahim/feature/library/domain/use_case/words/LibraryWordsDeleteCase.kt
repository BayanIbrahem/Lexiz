package com.dev.bayan.ibrahim.feature.library.domain.use_case.words

import com.dev.bayan.ibrahim.feature.library.data.repository.db.components.words.delete.LibraryDbWordDeleteRepo
import javax.inject.Inject

/**
 * use case for intro screen, which returns update data of last edit time of each content type
 */
class LibraryWordsDeleteCase @Inject constructor(
    private val repo: LibraryDbWordDeleteRepo,
) {
    suspend operator fun invoke(ids: Set<Long>): Int {
        return repo.contentDeleteWords(ids)
    }
}

