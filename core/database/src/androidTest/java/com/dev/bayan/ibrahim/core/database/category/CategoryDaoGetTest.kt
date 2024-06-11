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
import com.dev.bayan.ibrahim.core.database.entities.relation.word_category.CategoryWithWordsRelation
import com.dev.bayan.ibrahim.core.database.entities.table.CategoryEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CategoryDaoGetTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val db = Room.inMemoryDatabaseBuilder(context, JaArDatabase::class.java).build()
    private val categoryDao = db.getCategoryDao()
//    private val wordDao = db.getWordDao()

    @Before
    fun init() = runTest {
        db.getLanguageDao().insertOrIgnoreAllLanguages(languagesDataSource)
        db.getTypeDao().insertOrIgnoreAllTypes(typesDataSource)
        db.getCategoryDao().insertCategories(categoriesDataSource)
        meaningsDataSource.forEach { db.getMeaningDao().insertMeaning(it) }
        wordsDataSource.forEach { db.getWordDao().insertWord(it) }
        db.getWordDao().insertAllWordCrossCategory(wordCrossCategoriesDataSource)
    }

    @Test
    fun getCategory() = runTest {
        assertEquals(
            categoriesDataSource.first(),
            categoryDao.getCategory(categoriesDataSource.first().id!!).first()
        )
        assertNull(categoryDao.getCategory(1000).first())
    }

    @Test
    fun getCategoryWithWords() = runTest {
        val actual = categoryDao.getCategoryWithWords(categoriesDataSource.first().id!!).first()
        val expected = wordCrossCategoriesDataSource.groupBy {
            it.category_id
        }.mapKeys { (k, _) ->
            categoriesDataSource.first { it.id == k }
        }.mapValues { (_, w) ->
            w.map { e ->
                wordsDataSource.first { it.id == e.word_id }
            }
        }.map {
            CategoryWithWordsRelation(it.key, it.value)
        }.first {
            it.category.id == categoriesDataSource.first().id!!
        }
        assertEquals(expected, actual)
        assertNull(categoryDao.getCategoryWithWords(1000).first())
        categoryDao.insertCategories(
            listOf(
                CategoryEntity(4, "name", null, 0, 0)
            )
        )
        assertTrue(categoryDao.getCategoryWithWords(4).first()?.words?.isEmpty() ?: false)
    }

    @Test
    fun getCategoryLastEditDate() = runTest {
        assertEquals(
            categoriesDataSource.maxOf { it.last_edit_date },
            categoryDao.getCategoryLastEditDate().first()
        )
    }

    @Test
    fun getAllCategoriesWithWords() = runTest {
        val actual = categoryDao.getAllCategoriesWithWords().first()
        val expected = wordCrossCategoriesDataSource.groupBy {
            it.category_id
        }.mapKeys { (k, _) ->
            categoriesDataSource.first { it.id == k }
        }.mapValues { (_, w) ->
            w.map { e ->
                wordsDataSource.first { it.id == e.word_id }
            }
        }.map {
            CategoryWithWordsRelation(it.key, it.value)
        }
        assertEquals(expected, actual)
    }

    @Test
    fun getAllCategories() = runTest {
        assertEquals(
            categoriesDataSource,
            categoryDao.getAllCategories().first()
        )
    }
}