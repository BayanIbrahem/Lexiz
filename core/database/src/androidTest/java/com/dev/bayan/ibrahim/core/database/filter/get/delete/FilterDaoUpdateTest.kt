package com.dev.bayan.ibrahim.core.database.filter.get.delete

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dev.bayan.ibrahim.core.database.data_source.categoriesDataSource
import com.dev.bayan.ibrahim.core.database.data_source.filterCategoryTypeRelationDataSource
import com.dev.bayan.ibrahim.core.database.data_source.filterCrossTypesDataSource
import com.dev.bayan.ibrahim.core.database.data_source.filtersCrossCategoriesDataSource
import com.dev.bayan.ibrahim.core.database.data_source.filtersTemplatesDataSource
import com.dev.bayan.ibrahim.core.database.data_source.languagesDataSource
import com.dev.bayan.ibrahim.core.database.data_source.typesDataSource
import com.dev.bayan.ibrahim.core.database.db.JaArDatabase
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FilterDaoUpdateTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val db = Room.inMemoryDatabaseBuilder(context, JaArDatabase::class.java).build()
    private val filterDao = db.getFilterDao()

    @Before
    fun init() = runTest {
        db.getLanguageDao().insertOrIgnoreAllLanguages(languagesDataSource)
        db.getTypeDao().insertOrIgnoreAllTypes(typesDataSource)
        db.getCategoryDao().insertCategories(categoriesDataSource)
        runTest {
            filtersTemplatesDataSource.forEach { filterEntity ->
                filterDao.insertFilterWithTypeCategory(
                    filterEntity,
                    filterCrossTypesDataSource.mapNotNull {
                        if (it.filter_id == filterEntity.id) {
                            it.type_id
                        } else null
                    },
                    filtersCrossCategoriesDataSource.mapNotNull {
                        if (it.filter_id == filterEntity.id) {
                            it.category_id
                        } else null
                    },
                )
            }
        }
    }

    @Test
    fun filterDaoUpdate_updateExistedId_updatedSuccessfully() = runTest {
        val firstFilter = filterCategoryTypeRelationDataSource.first()
        val newValueOfFilter = firstFilter.copy(
            filter = firstFilter.filter.copy(name = "newName")
        )
        filterDao.updateFilterWithTypeCategory(
            entity = newValueOfFilter.filter,
            newTypes = newValueOfFilter.types.map { it.id },
            newCategories = newValueOfFilter.categories.mapNotNull { it.id }
        )
        assertEquals(
            filterCategoryTypeRelationDataSource.toPersistentList().set(0, newValueOfFilter)
                .filter {
                    it.filter.template
                },
            filterDao.getAllTemplateFilters().first(),
        )
    }

    @Test
    fun filterDaoUpdate_updateFilterWithNullId_doesNotUpdate() = runTest {
        val firstFilter = filterCategoryTypeRelationDataSource.first()
        val newValueOfFilter = firstFilter.copy(
            filter = firstFilter.filter.copy(name = "newName", id = null)
        )
        filterDao.updateFilterWithTypeCategory(
            entity = newValueOfFilter.filter,
            newTypes = newValueOfFilter.types.map { it.id },
            newCategories = newValueOfFilter.categories.mapNotNull { it.id }
        )
        assertEquals(
            filterCategoryTypeRelationDataSource.filter {
                it.filter.template
            },
            filterDao.getAllTemplateFilters().first(),
        )
    }

}

