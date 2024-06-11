package com.dev.bayan.ibrahim.feature.quiz_builder.domain.di

import com.dev.bayan.ibrahim.core.database.dao.*
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.name_index.QuizBuilderNewFilterNameIndexUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.name_index.QuizBuilderNewGroupNameIndexUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.name_index.QuizBuilderNewQuizNameIndexUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.raw.QuizBuilderAllCategoriesUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.raw.QuizBuilderAllLanguagesUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.raw.QuizBuilderAllTypesUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.raw.QuizBuilderFailurePeakUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.raw.QuizBuilderLengthPeakUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.raw.QuizBuilderSelectedLanguageCategoriesUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.raw.QuizBuilderSelectedLanguageTypesUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.raw.QuizBuilderSuccessPeakUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.template.delete.QuizBuilderDeleteFilterUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.template.delete.QuizBuilderDeleteGroupUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.template.get.QuizBuilderTemplateFilterUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.template.get.QuizBuilderTemplateGroupUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.template.get.QuizBuilderTemplateQuizUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.template.upsert.QuizBuilderUpsertFilterUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.template.upsert.QuizBuilderUpsertGroupUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.QuizBuilderRepo
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.name_index.QuizBuilderNewFilterNameIndexRepo
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.name_index.QuizBuilderNewGroupNameIndexRepo
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.name_index.QuizBuilderNewQuizNameIndexRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class QuizBuilderDomainModule {
    @Singleton
    @Provides
    fun provideRawAllLanguagesUseCase(
        repo: QuizBuilderRepo,
    ): QuizBuilderAllLanguagesUseCase {
        return QuizBuilderAllLanguagesUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideRawAllTypesUseCase(
        repo: QuizBuilderRepo,
    ): QuizBuilderAllTypesUseCase {
        return QuizBuilderAllTypesUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideRawAllCategoriesUseCase(
        repo: QuizBuilderRepo,
    ): QuizBuilderAllCategoriesUseCase {
        return QuizBuilderAllCategoriesUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideRawSelectedLanguageTypesUseCase(
        repo: QuizBuilderRepo,
    ): QuizBuilderSelectedLanguageTypesUseCase {
        return QuizBuilderSelectedLanguageTypesUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideRawSelectedLanguageCategoriesUseCase(
        repo: QuizBuilderRepo,
    ): QuizBuilderSelectedLanguageCategoriesUseCase {
        return QuizBuilderSelectedLanguageCategoriesUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideRawLengthPeakUseCase(
        repo: QuizBuilderRepo,
    ): QuizBuilderLengthPeakUseCase {
        return QuizBuilderLengthPeakUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideRawFailurePeakUseCase(
        repo: QuizBuilderRepo,
    ): QuizBuilderFailurePeakUseCase {
        return QuizBuilderFailurePeakUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideRawSuccessPeakUseCase(
        repo: QuizBuilderRepo,
    ): QuizBuilderSuccessPeakUseCase {
        return QuizBuilderSuccessPeakUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideTemplateFilterUseCase(
        repo: QuizBuilderRepo,
    ): QuizBuilderTemplateFilterUseCase {
        return QuizBuilderTemplateFilterUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideTemplateGroupUseCase(
        repo: QuizBuilderRepo,
    ): QuizBuilderTemplateGroupUseCase {
        return QuizBuilderTemplateGroupUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideTemplateQuizUseCase(
        repo: QuizBuilderRepo,
    ): QuizBuilderTemplateQuizUseCase {
        return QuizBuilderTemplateQuizUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideUpsertFilterUseCase(
        repo: QuizBuilderRepo,
    ): QuizBuilderUpsertFilterUseCase {
        return QuizBuilderUpsertFilterUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideUpsertGroupUseCase(
        repo: QuizBuilderRepo,
    ): QuizBuilderUpsertGroupUseCase {
        return QuizBuilderUpsertGroupUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideDeleteFilterUseCase(
        repo: QuizBuilderRepo,
    ): QuizBuilderDeleteFilterUseCase {
        return QuizBuilderDeleteFilterUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideDeleteGroupUseCase(
        repo: QuizBuilderRepo,
    ): QuizBuilderDeleteGroupUseCase {
        return QuizBuilderDeleteGroupUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideNewQuizNameIndexUseCase(
        repo: QuizBuilderRepo,
    ): QuizBuilderNewQuizNameIndexUseCase {
        return QuizBuilderNewQuizNameIndexUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideNewGroupNameIndexUseCase(
        repo: QuizBuilderRepo,
    ): QuizBuilderNewGroupNameIndexUseCase {
        return QuizBuilderNewGroupNameIndexUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideNewFilterNameIndexUseCase(
        repo: QuizBuilderRepo,
    ): QuizBuilderNewFilterNameIndexUseCase {
        return QuizBuilderNewFilterNameIndexUseCase(repo)
    }
}