package com.dev.bayan.ibrahim.core.database.word

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
import com.dev.bayan.ibrahim.core.database.entities.relation.word_category.WordWithCategoriesRelation
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WordDaoGetTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val db = Room.inMemoryDatabaseBuilder(context, JaArDatabase::class.java).build()
    private val wordDao = db.getWordDao()

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
        wordDao.insertAllWordCrossCategory(wordCrossCategoriesDataSource)
    }

    @Test
    fun getWord() = runTest {
        val id = wordsDataSource.first().id!!
        val word = wordDao.getWord(id)
        assertEquals(wordsDataSource.first(), word.first())
    }

    @Test
    fun getMeaningsCategoriesCount() = runTest {
        val count = wordsDataSource.groupBy {
            it.meaningId
        }.mapValues {
            it.value.map {
                it.id
            }.sumOf { wordId ->
                wordCrossCategoriesDataSource.count { it.word_id == wordId }
            }
        }

        val map = wordDao.getMeaningsCategoriesCount()
        assertEquals(count, map.first())
    }

    @Test
    fun getLanguagesCategoriesCount() = runTest {
        val count = wordsDataSource.groupBy {
            it.language_code
        }.mapValues {
            it.value.map {
                it.id
            }.sumOf { wordId ->
                wordCrossCategoriesDataSource.count { it.word_id == wordId }
            }
        }
        val map = wordDao.getLanguagesCategoriesCount()
        assertEquals(count, map.first())
    }

    @Test
    fun getWordLastEditDate() = runTest {
        val lastEditDate = wordDao.getWordLastEditDate()
        assertEquals(
            wordsDataSource.maxOf { it.lastEditDate },
            lastEditDate.first()
        )
    }

    @Test
    fun getSameMeaningWords() = runTest {
        val sameMeaningWords = wordDao.getSameMeaningWords(wordsDataSource.first().id!!)
        assertEquals(
            wordsDataSource.filter {
                it.meaningId == wordsDataSource.first().meaningId &&
                        it.id != wordsDataSource.first().id
            },
            sameMeaningWords.first()
        )
    }

    @Test
    fun getWordWithCategories() = runTest {
        val wordWithCategories = wordDao.getWordWithCategories(wordsDataSource.first().id!!)
        assertEquals(
            wordCrossCategoriesDataSource.map { cross ->
                wordsDataSource.first { it.id == cross.word_id } to
                        categoriesDataSource.first { it.id == cross.category_id }
            }.groupBy { it.first }.map {
                WordWithCategoriesRelation(
                    word = it.key,
                    categories = it.value.map { it.second }
                )
            }.first {
                it.word.id == wordsDataSource.first().id!!
            },
            wordWithCategories.first()
        )
    }

    @Test
    fun getAllWordsWithCategories() = runTest {
        val allWordsWithCategories = wordDao.getAllWordsWithCategories()
        assertEquals(
            wordCrossCategoriesDataSource.map { cross ->
                wordsDataSource.first { it.id == cross.word_id } to
                        categoriesDataSource.first { it.id == cross.category_id }
            }.groupBy { it.first }.map {
                WordWithCategoriesRelation(
                    word = it.key,
                    categories = it.value.map { it.second }
                )
            },
            allWordsWithCategories.first()
        )
    }

    @Test
    fun getAllWordsWithNames() = runTest {
        val allWordsWithNames = wordDao.getAllWordsWithNames()
        assertEquals(
            wordsDataSource.filter { it.name != null },
            allWordsWithNames.first()
        )
    }

    @Test
    fun getAllWords() = runTest {
        val allWords = wordDao.getAllWords()
        assertEquals(wordsDataSource, allWords.first())
    }

    @Test
    fun getLanguagesLastEditDate() = runTest {
        val languagesLastEditDate = wordDao.getLanguagesLastEditDate()
        assertEquals(
            wordsDataSource.groupBy {
                it.language_code
            }.mapValues {
                it.value.maxOf { it.lastEditDate }
            },
            languagesLastEditDate.first()
        )
    }
}