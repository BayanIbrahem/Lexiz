package com.dev.bayan.ibrahim.core.database.meaning

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
import com.dev.bayan.ibrahim.core.database.entities.table.MeaningEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MeaningDaoDeleteTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val db = Room.inMemoryDatabaseBuilder(context, JaArDatabase::class.java).build()
    private val meaningDao = db.getMeaningDao()
    private val wordDao = db.getWordDao()

    @Before
    fun init() = runTest {
        db.getLanguageDao().insertOrIgnoreAllLanguages(languagesDataSource)
        db.getTypeDao().insertOrIgnoreAllTypes(typesDataSource)
        db.getCategoryDao().insertCategories(categoriesDataSource)
        meaningsDataSource.forEach { meaningDao.insertMeaning(it) }
        wordsDataSource.forEach { wordDao.insertWord(it) }
        wordDao.insertAllWordCrossCategory(wordCrossCategoriesDataSource)
    }

    @Test
    fun deleteMeanings() = runTest {
        meaningDao.deleteMeanings(setOf(0, 1, 2, 3, 4, 5))
        assertTrue(
            wordDao.getAllWords().first().isEmpty()
        )
    }

    @Test
    fun deletePossibleEmptyMeaning_emptyMeaningWillBeDeleted() = runTest {
        wordDao.deleteWords(setOf(1))
        assertEquals(
            wordsDataSource.filterNot { it.id == 1L },
            wordDao.getAllWords().first(),
        )
        meaningDao.deletePossibleEmptyMeaning(1)
        assertEquals(
            meaningsDataSource.filter { it.id != 1L },
            meaningDao.getAllMeanings().first()
        )
    }

    @Test
    fun deletePossibleEmptyMeaning_notEmptyMeaningWillNotBeDeleted() = runTest {
        meaningDao.deletePossibleEmptyMeaning(1)
        assertEquals(wordsDataSource, wordDao.getAllWords().first())
        assertEquals(
            meaningsDataSource,
            meaningDao.getAllMeanings().first()
        )
    }

    @Test
    fun deletePossibleEmptyMeanings() = runTest {
        meaningDao.insertMeaning(MeaningEntity(3))
        meaningDao.insertMeaning(MeaningEntity(4))
        meaningDao.insertMeaning(MeaningEntity(5))
        meaningDao.insertMeaning(MeaningEntity(6))
        meaningDao.deletePossibleEmptyMeanings()
        assertEquals(wordsDataSource, wordDao.getAllWords().first())
        assertEquals(
            meaningsDataSource,
            meaningDao.getAllMeanings().first()
        )
    }
}