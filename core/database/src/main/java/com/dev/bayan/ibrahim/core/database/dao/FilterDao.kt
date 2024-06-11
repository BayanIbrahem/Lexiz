package com.dev.bayan.ibrahim.core.database.dao

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.MapColumn
import androidx.room.MapInfo
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.dev.bayan.ibrahim.core.database.entities.relation.filter.FilterCrossCategoryTypeRelation
import com.dev.bayan.ibrahim.core.database.entities.table.FilterCrossCategoryEntity
import com.dev.bayan.ibrahim.core.database.entities.table.FilterCrossTypeEntity
import com.dev.bayan.ibrahim.core.database.entities.table.FilterEntity
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@Dao
abstract class FilterDao : FilterCrossCategoryTypeDao() {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract suspend fun insertFilterEntity(filterEntity: FilterEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract suspend fun insertAllFiltersEntities(entities: List<FilterEntity>): List<Long>

    @Transaction
    open suspend fun insertFilterWithTypeCategory(
        entity: FilterEntity,
        types: List<Long>,
        categories: List<Long>,
    ): Long? {
        if (entity.id != null) return null// if the entity already has an id then no action is done
        val id = insertFilterEntity(entity)
        types.map { FilterCrossTypeEntity(it, id) }.also {
            insertFilterCrossTypes(it)
        }
        categories.map { FilterCrossCategoryEntity(it, id) }.also {
            insertFilterCrossCategories(it)
        }
        return id
    }

    @Update(onConflict = OnConflictStrategy.IGNORE)
    protected abstract suspend fun updateFilterEntity(filterEntity: FilterEntity): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract suspend fun updateAllFiltersEntities(entities: List<FilterEntity>): List<Long>

    @Transaction
    open suspend fun updateFilterWithTypeCategory(
        entity: FilterEntity,
        newTypes: List<Long>,
        newCategories: List<Long>,
    ): Long? {
        return withContext(Dispatchers.IO) {
            if (entity.id == null) return@withContext null// if the entity doesn't has an id then no action is done
            updateFilterEntity(entity)
            newTypes.map { FilterCrossTypeEntity(it, entity.id) }.also {
                insertFilterCrossTypes(it)
            }
            newCategories.map { FilterCrossCategoryEntity(it, entity.id) }.also {
                insertFilterCrossCategories(it)
            }
            deleteExtraTypesOfFilter(entity.id, newTypes)
            deleteExtraCategoriesOfFilter(entity.id, newCategories)
            entity.id
        }
    }


    @Delete
    protected abstract suspend fun deleteFilter(filter: FilterEntity): Int

    @Query(
        """
         DELETE FROM ${DB_NAMES.FILTER.table}
         WHERE ${DB_NAMES.FILTER.id} = :id
   """
    )
    protected abstract suspend fun deleteFilterById(id: Long): Int

    @Query(
        """
         DELETE FROM ${DB_NAMES.FILTER.table}
         WHERE NOT (
            ${DB_NAMES.FILTER.id} IN (
            SELECT ${DB_NAMES.FILTER_CROSS_GROUP.filter_id}
            FROM ${DB_NAMES.FILTER_CROSS_GROUP.table}
            ) OR ${DB_NAMES.FILTER.template}
        )
      """
    )
    protected abstract suspend fun deleteNonTemplateNotUsedFilters(): Int


    @Query(
        """
         SELECT *
         FROM ${DB_NAMES.FILTER.table}
         WHERE ${DB_NAMES.FILTER.id} = :id
      """
    )
    protected abstract suspend fun getFilterEntityById(id: Long): FilterEntity?

    @Query(
        """
         SELECT ${DB_NAMES.FILTER_CROSS_GROUP.group_id}
         FROM ${DB_NAMES.FILTER_CROSS_GROUP.table}
         WHERE ${DB_NAMES.FILTER_CROSS_GROUP.filter_id} = :filterId
      """
    )
    protected abstract fun getGroupsIdsRelatedToFilter(filterId: Long): List<Long>

    @Transaction
    @Query(
        """
      SELECT * 
      FROM ${DB_NAMES.FILTER.table}
      WHERE ${DB_NAMES.FILTER.template}
   """
    )
    abstract fun getAllTemplateFilters(): Flow<List<FilterCrossCategoryTypeRelation>>

    @Query(
        """
         SELECT
            ${DB_NAMES.FILTER.type},
            count(*) as 'count'
         FROM
            ${DB_NAMES.FILTER.table}
         WHERE
            ${DB_NAMES.FILTER.template}
         GROUP BY ${DB_NAMES.FILTER.type}
      """
    )
    abstract fun getTemplateFiltersCount(): Flow<Map<@MapColumn(DB_NAMES.FILTER.type) Int, @MapColumn(
        "count"
    ) Int>>

    @Transaction
    open suspend fun deleteFilterOrMakeNonTemplate(id: Long): Int {
        val filter = getFilterEntityById(id) ?: return 0
        val groupsIds = getGroupsIdsRelatedToFilter(filter.id!!)
        return if (groupsIds.isEmpty()) {
            deleteFilter(filter)
        } else {
            updateFilterEntity(filter.copy(template = false))
        }
    }
}