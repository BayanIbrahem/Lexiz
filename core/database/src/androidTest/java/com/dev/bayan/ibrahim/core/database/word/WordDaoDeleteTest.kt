package com.dev.bayan.ibrahim.core.database.word

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dev.bayan.ibrahim.core.database.data_source.categoriesDataSource
import com.dev.bayan.ibrahim.core.database.data_source.languagesDataSource
import com.dev.bayan.ibrahim.core.database.data_source.meaningsDataSource
import com.dev.bayan.ibrahim.core.database.data_source.typesDataSource
import com.dev.bayan.ibrahim.core.database.data_source.wordsDataSource
import com.dev.bayan.ibrahim.core.database.db.JaArDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WordDaoDeleteTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val db = Room.inMemoryDatabaseBuilder(context, JaArDatabase::class.java).build()
    private val wordDao = db.getWordDao()
    private val allWordsFlow = wordDao.getAllWords()

    @Before
    fun init() = runTest {
        db.getLanguageDao().insertOrIgnoreAllLanguages(languagesDataSource)
        db.getTypeDao().insertOrIgnoreAllTypes(typesDataSource)
        db.getCategoryDao().insertCategories(categoriesDataSource)
        meaningsDataSource.forEach {
            db.getMeaningDao().insertMeaning(it)
        }
        wordsDataSource.forEach {
            wordDao.insertWord(it)
        }
    }

    @Test
    fun deleteWords() = runTest {
        wordDao.deleteWords(setOf(wordsDataSource.first().id!!))
        assertEquals(
            wordsDataSource.filterNot { it.id == wordsDataSource.first().id },
            allWordsFlow.first()
        )
    }

    @Test
    fun deleteInExistedWords() = runTest {
        wordDao.deleteWords(setOf(10, 11, 12))
        assertEquals(wordsDataSource, allWordsFlow.first())
    }
}