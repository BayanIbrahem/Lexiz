package com.dev.bayan.ibrahim.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.dev.bayan.ibrahim.core.common.jaar_annotation.JaArDbInternalApi
import com.dev.bayan.ibrahim.core.common.jaar_annotation.JaArDbPublicApi
import com.dev.bayan.ibrahim.core.database.entities.relation.filter.FilterCrossCategoryTypeRelation
import com.dev.bayan.ibrahim.core.database.entities.table.FilterCrossCategoryEntity
import com.dev.bayan.ibrahim.core.database.entities.table.FilterCrossTypeEntity
import com.dev.bayan.ibrahim.core.database.entities.table.FilterEntity
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES
import kotlinx.coroutines.flow.Flow

@Dao
abstract class FilterCrossCategoryTypeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract suspend fun insertFilterCrossTypes(entities: List<FilterCrossTypeEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract suspend fun insertFilterCrossCategories(entities: List<FilterCrossCategoryEntity>)

    @Query(
        """
         DELETE FROM ${DB_NAMES.FILTER_CROSS_TYPE.table}
         WHERE 
            ${DB_NAMES.FILTER_CROSS_TYPE.filter_id} == :filterId
            AND NOT (${DB_NAMES.FILTER_CROSS_TYPE.type_id} in (:typesIds))
      """
    )
    protected abstract suspend fun deleteExtraTypesOfFilter(filterId: Long, typesIds: List<Long>)

    @Query(
        """
         DELETE FROM ${DB_NAMES.FILTER_CROSS_CATEGORY.table}
         WHERE 
            ${DB_NAMES.FILTER_CROSS_CATEGORY.filter_id} == :filterId
            AND NOT (${DB_NAMES.FILTER_CROSS_CATEGORY.category_id} in (:categoriesIds))
      """
    )
    protected abstract suspend fun deleteExtraCategoriesOfFilter(
        filterId: Long,
        categoriesIds: List<Long>
    )
}