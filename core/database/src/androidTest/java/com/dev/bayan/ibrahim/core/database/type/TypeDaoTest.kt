package com.dev.bayan.ibrahim.core.database.type

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
class TypeDaoTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val db = Room.inMemoryDatabaseBuilder(context, JaArDatabase::class.java).build()
    private val typeDao = db.getTypeDao()

    @Before
    fun init() = runTest {
        db.getLanguageDao().insertOrIgnoreAllLanguages(languagesDataSource)
        typeDao.insertOrIgnoreAllTypes(typesDataSource)
        meaningsDataSource.forEach { db.getMeaningDao().insertMeaning(it) }
        db.getCategoryDao().insertCategories(categoriesDataSource)
        db.getWordDao().apply {
            wordsDataSource.forEach { insertWord(it) }
            insertAllWordCrossCategory(wordCrossCategoriesDataSource)
        }


    }

    @Test
    fun getTypeOfWord() = runTest {
        val word = wordsDataSource.first()
        assertEquals(
            typesDataSource.first { it.id == word.typeId },
            typeDao.getTypeOfWord(word.id!!).first(),
        )
    }

    @Test
    fun getAllTypesOfLanguage() = runTest {
        assertEquals(
            typesDataSource.filter { it.language_code == "en" },
            typeDao.getAllTypesOfLanguage("en").first()
        )
    }

    @Test
    fun getAllTypesNames() = runTest {
        assertEquals(
            typesDataSource.associate { it.id to it.name },
            typeDao.getAllTypesNames().first()
        )
    }
}