package com.dev.bayan.ibrahim.core.database.language

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dev.bayan.ibrahim.core.database.data_source.categoriesDataSource
import com.dev.bayan.ibrahim.core.database.data_source.languagesDataSource
import com.dev.bayan.ibrahim.core.database.data_source.meaningsDataSource
import com.dev.bayan.ibrahim.core.database.data_source.typesDataSource
import com.dev.bayan.ibrahim.core.database.data_source.wordCrossCategoriesDataSource
import com.dev.bayan.ibrahim.core.database.data_source.wordsDataSource
import com.dev.bayan.ibrahim.core.database.db.JaArDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LanguageDaoDeleteTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val db = Room.inMemoryDatabaseBuilder(context, JaArDatabase::class.java).build()
    private val languageDao = db.getLanguageDao()

    @Before
    fun init() = runTest {
        db.getLanguageDao().insertOrIgnoreAllLanguages(languagesDataSource)
        db.getTypeDao().insertOrIgnoreAllTypes(typesDataSource)
        db.getCategoryDao().insertCategories(categoriesDataSource)
        meaningsDataSource.forEach {
            db.getMeaningDao().insertMeaning(it)
        }
        wordsDataSource.forEach {
            db.getWordDao().insertWord(it)
        }
        db.getWordDao().insertAllWordCrossCategory(wordCrossCategoriesDataSource)
    }

    @Test
    fun deleteAllWordsOfLanguage() = runTest {
        languageDao.deleteContentOfLanguage("en")
        assertEquals(
            wordsDataSource.filterNot { it.language_code == "en" },
            db.getWordDao().getAllWords().first(),
        )
    }
}