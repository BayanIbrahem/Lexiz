package com.dev.bayan.ibrahim.feature.library.data.repository.db

import com.dev.bayan.ibrahim.feature.library.data.implementaion.LibraryDbRepoImpl
import com.dev.bayan.ibrahim.core.data.repo.JaArCurrentLocaleRepo

/**
 * this is database repository for library feature
 * it provides the following functionalities:
 * @see LibraryDbRepoImpl
 */
interface LibraryDbRepo :
    LibraryDbWordsRepo,
    LibraryDbEditorRepo,
    JaArCurrentLocaleRepo {
}