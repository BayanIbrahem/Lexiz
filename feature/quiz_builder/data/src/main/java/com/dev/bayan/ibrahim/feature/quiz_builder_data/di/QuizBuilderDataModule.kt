package com.dev.bayan.ibrahim.feature.quiz_builder_data.di

import com.dev.bayan.ibrahim.core.database.dao.*
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo_impl.QuizBuilderRepoImpl
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.QuizBuilderRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class QuizBuilderDataModule {
    @Singleton
    @Provides
    fun provideQuizBuilderDbRepo(
        languageDao: LanguageDao,
        typeDao: TypeDao,
        categoryDao: CategoryDao,
        wordDao: WordDao,
        wordResultDao: WordResultDao,
        filterDao: FilterDao,
        filterGroupDao: FilterGroupDao,
        quizDao: QuizDao,
    ): QuizBuilderRepo {
        return QuizBuilderRepoImpl(
            languageDao = languageDao,
            typeDao = typeDao,
            categoryDao = categoryDao,
            wordDao = wordDao,
            wordResultDao = wordResultDao,
            filterDao = filterDao,
            filterGroupDao = filterGroupDao,
            quizDao = quizDao,
        )
    }
}