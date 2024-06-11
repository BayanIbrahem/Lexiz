package com.dev.bayan.ibrahim.feature.library.domain.di

import com.dev.bayan.ibrahim.feature.library.data.repository.db.LibraryDbRepo
import com.dev.bayan.ibrahim.feature.library.domain.use_case.words.LibraryWordsDeleteCase
import com.dev.bayan.ibrahim.feature.library.domain.use_case.words.LibraryWordsLoadCase
import com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.init.LibraryDbEditorInitCategoryUseCase
import com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.init.LibraryDbEditorInitWordUseCase
import com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.save.save_new.LibraryDbEditorSaveNewCategoryUseCase
import com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.save.save_new.LibraryDbEditorSaveNewWordUseCase
import com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.save.update.LibraryDbEditorSaveUpdateCategoryUseCase
import com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.save.update.LibraryDbEditorSaveUpdateWordUseCase
import com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.suggest.LibraryDbEditorSuggestionCategoryUseCase
import com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.suggest.LibraryDbEditorSuggestionLanguageUseCase
import com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.suggest.LibraryDbEditorSuggestionMeaningUseCase
import com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.suggest.LibraryDbEditorSuggestionTypeUseCase
import com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.suggest.LibraryDbEditorSuggestionWordUseCase
import com.dev.bayan.ibrahim.feature.library.domain.use_case.words.LibraryWordsBaseDataSetCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class LibraryUseCaseModule {
    @Provides
    @Singleton
    fun provideContentWordLoadUseCase(repo: LibraryDbRepo): LibraryWordsLoadCase {
        return LibraryWordsLoadCase(repo, repo)
    }

    @Provides
    @Singleton
    fun provideContentWordBaseDataSetUseCase(repo: LibraryDbRepo): LibraryWordsBaseDataSetCase {
        return LibraryWordsBaseDataSetCase(repo)
    }

    @Provides
    @Singleton
    fun provideContentWordDeleteUseCase(repo: LibraryDbRepo): LibraryWordsDeleteCase {
        return LibraryWordsDeleteCase(repo)
    }

    // editor:
    @Provides
    @Singleton
    fun provideEditorInitCategory(repo: LibraryDbRepo): LibraryDbEditorInitCategoryUseCase {
        return LibraryDbEditorInitCategoryUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideEditorInitWord(repo: LibraryDbRepo): LibraryDbEditorInitWordUseCase {
        return LibraryDbEditorInitWordUseCase(repo, repo)
    }

    @Provides
    @Singleton
    fun provideEditorSaveNewCategory(repo: LibraryDbRepo): LibraryDbEditorSaveNewCategoryUseCase {
        return LibraryDbEditorSaveNewCategoryUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideEditorSaveUpdateCategory(repo: LibraryDbRepo): LibraryDbEditorSaveUpdateCategoryUseCase {
        return LibraryDbEditorSaveUpdateCategoryUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideEditorSaveNewWord(repo: LibraryDbRepo): LibraryDbEditorSaveNewWordUseCase {
        return LibraryDbEditorSaveNewWordUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideEditorSaveUpdateWord(repo: LibraryDbRepo): LibraryDbEditorSaveUpdateWordUseCase {
        return LibraryDbEditorSaveUpdateWordUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideEditorSuggestionCategory(repo: LibraryDbRepo): LibraryDbEditorSuggestionCategoryUseCase {
        return LibraryDbEditorSuggestionCategoryUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideEditorSuggestionLanguage(repo: LibraryDbRepo): LibraryDbEditorSuggestionLanguageUseCase {
        return LibraryDbEditorSuggestionLanguageUseCase(repo, repo)
    }

    @Provides
    @Singleton
    fun provideEditorSuggestionType(repo: LibraryDbRepo): LibraryDbEditorSuggestionTypeUseCase {
        return LibraryDbEditorSuggestionTypeUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideEditorSuggestionWord(repo: LibraryDbRepo): LibraryDbEditorSuggestionWordUseCase {
        return LibraryDbEditorSuggestionWordUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideEditorSuggestionMeaning(repo: LibraryDbRepo): LibraryDbEditorSuggestionMeaningUseCase {
        return LibraryDbEditorSuggestionMeaningUseCase(repo)
    }
}