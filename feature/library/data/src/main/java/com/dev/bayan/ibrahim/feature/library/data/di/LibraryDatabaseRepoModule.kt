package com.dev.bayan.ibrahim.feature.library.data.di

import com.dev.bayan.ibrahim.core.database.dao.CategoryDao
import com.dev.bayan.ibrahim.core.database.dao.LanguageDao
import com.dev.bayan.ibrahim.core.database.dao.MeaningDao
import com.dev.bayan.ibrahim.core.database.dao.SafeWordDao
import com.dev.bayan.ibrahim.core.database.dao.TypeDao
import com.dev.bayan.ibrahim.core.database.dao.WordDao
import com.dev.bayan.ibrahim.feature.library.data.implementaion.LibraryDbRepoImpl
import com.dev.bayan.ibrahim.feature.library.data.repository.db.LibraryDbRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class LibraryDatabaseRepoModule {
    @Provides
    @Singleton
    fun provideLibraryDbRepo(
        categoryDao: CategoryDao,
        languageDao: LanguageDao,
        typeDao: TypeDao,
        wordDao: WordDao,
        safeWordDao: SafeWordDao,
    ): LibraryDbRepo {
        return LibraryDbRepoImpl(
            categoryDao = categoryDao,
            languageDao = languageDao,
            typeDao = typeDao,
            wordDao = wordDao,
            safeWordDao = safeWordDao
        )
    }
}