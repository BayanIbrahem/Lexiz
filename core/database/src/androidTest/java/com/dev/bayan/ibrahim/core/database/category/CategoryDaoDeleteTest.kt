package com.dev.bayan.ibrahim.core.database.category

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
import com.dev.bayan.ibrahim.core.database.entities.table.WordCrossCategoryEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CategoryDaoDeleteTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val db = Room.inMemoryDatabaseBuilder(context, JaArDatabase::class.java).build()
    private val categoryDao = db.getCategoryDao()
    private val wordDao = db.getWordDao()
    private val allCategoriesFlow = categoryDao.getAllCategories()

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
    fun deleteCategoryExtraWords() = runTest {
        val largeWordCrossCategoryDataSource = wordCrossCategoriesDataSource.toMutableList()
        largeWordCrossCategoryDataSource.addAll(
            listOf(
                WordCrossCategoryEntity(1, 1),
                WordCrossCategoryEntity(2, 1),
                WordCrossCategoryEntity(3, 1),
            )
        )
        wordDao.insertAllWordCrossCategory(largeWordCrossCategoryDataSource.distinct())
        categoryDao.deleteCategoryExtraWords(
            categoryId = 1,
            newWordsSet = wordCrossCategoriesDataSource.filter {
                it.category_id == 1L
            }.map {
                it.word_id
            }.toSet()
        )
        val categoryWithWords = categoryDao.getCategoryWithWords(1)
        assertEquals(
            wordCrossCategoriesDataSource.mapNotNull {
                if (it.category_id == 1L) {
                    it.word_id
                } else null
            }.toSet(),
            categoryWithWords.first()?.words?.map { it.id }?.toSet()
        )
    }

    @Test
    fun deleteCategories() = runTest {
        wordDao.insertAllWordCrossCategory(wordCrossCategoriesDataSource)
        categoryDao.deleteCategories(setOf(1, 2, 3))
        assertTrue(allCategoriesFlow.first().isEmpty())
        assertTrue(wordDao.getAllWordsWithCategories().first().all { it.categories.isEmpty() })
    }
}