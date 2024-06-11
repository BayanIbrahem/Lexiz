package com.dev.bayan.ibrahim.core.database.filter.get

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
import com.dev.bayan.ibrahim.core.database.entities.relation.filter.FilterCrossCategoryTypeRelation
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FilterDaoGetTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val db = Room.inMemoryDatabaseBuilder(context, JaArDatabase::class.java).build()
    private val filterDao = db.getFilterDao()

    @Before
    fun init() = runTest {
        db.getLanguageDao().insertOrIgnoreAllLanguages(languagesDataSource)
        db.getTypeDao().insertOrIgnoreAllTypes(typesDataSource)
        db.getCategoryDao().insertCategories(categoriesDataSource)
    }

    @Test
    fun filterDaoGet_getAllTemplateFilters_getTemplateFiltersOnly() = runTest {
        val filters = mutableListOf<FilterCrossCategoryTypeRelation>()
        filterDao.getAllTemplateFilters().collect { newFilterTemplates ->
            filters.clear()
            filters.addAll(newFilterTemplates)
        }
        assertEquals(0, filters.count())
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
        assertEquals(filtersTemplatesDataSource.count(), filters.count())
        assertEquals(
            filterCategoryTypeRelationDataSource.filter { it.filter.template },
            filters,
        )
    }

}