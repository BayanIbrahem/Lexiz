package com.dev.bayan.ibrahim.core.database.dao


import androidx.room.*
import com.dev.bayan.ibrahim.core.database.entities.relation.filter.GroupCrossFilterRelation
import com.dev.bayan.ibrahim.core.database.entities.table.FilterCrossCategoryEntity
import com.dev.bayan.ibrahim.core.database.entities.table.FilterCrossTypeEntity
import com.dev.bayan.ibrahim.core.database.entities.table.FilterEntity
import com.dev.bayan.ibrahim.core.database.entities.table.FilterGroupEntity
import com.dev.bayan.ibrahim.core.database.entities.table.GroupCrossFilterEntity
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES
import com.qubit_team.apps.dental_clinic.core.common.tuples.to3
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@Dao
abstract class FilterGroupDao : FilterDao() {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract suspend fun insertGroupEntity(entity: FilterGroupEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract suspend fun insertGroupCrossFilters(entities: List<GroupCrossFilterEntity>)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    protected abstract suspend fun updateGroupEntity(entity: FilterGroupEntity)

    @Query(
        """
         DELETE FROM ${DB_NAMES.FILTER_CROSS_GROUP.table}
         WHERE ${DB_NAMES.FILTER_CROSS_GROUP.group_id} = :id
            AND NOT (${DB_NAMES.FILTER_CROSS_GROUP.filter_id} IN (:filtersIds))
   """
    )
    protected abstract suspend fun deleteExtraGroupFilters(id: Long, filtersIds: List<Long>)

    @Transaction
    open suspend fun insertGroupWithFilters(
        entity: FilterGroupEntity,
        data: List<Triple<FilterEntity, List<Long>, List<Long>>>,
    ): Long? {
        return withContext(Dispatchers.Default) {
            val id = entity.id?.run {
                return@withContext null
            } ?: insertGroupEntity(entity)
            insertTypesCategoriesGroupCrossFiltersRef(
                groupId = id,
                data = data,
                deleteExtraGroupFilters = false,
            )
            return@withContext id
        }

    }

    @Transaction
    open suspend fun updateGroupsWithFilters(
        entity: FilterGroupEntity,
        data: List<Triple<FilterEntity, List<Long>, List<Long>>>,
    ): Long? {
        return withContext(Dispatchers.Default) {
            val id = entity.id ?: return@withContext null
            updateGroupEntity(entity)
            insertTypesCategoriesGroupCrossFiltersRef(
                groupId = id,
                data = data,
                deleteExtraGroupFilters = true,
            )
            return@withContext id
        }
    }

    private suspend fun insertTypesCategoriesGroupCrossFiltersRef(
        groupId: Long,
        data: List<Triple<FilterEntity, List<Long>, List<Long>>>,
        deleteExtraGroupFilters: Boolean,
    ) {
        val dataWithIds = withContext(Dispatchers.IO) {
            data.map {
                withContext(Dispatchers.IO) {
                    (it.first.id ?: insertFilterEntity(it.first)) to it.second to3 it.third
                }
            }
        }
        // insert type category ref:
        val filtersCrossTypes = dataWithIds
            .map { (filter, type, _) ->
                type.map {
                    FilterCrossTypeEntity(type_id = it, filter_id = filter)
                }
            }.flatten()
        val filtersCrossCategories = dataWithIds
            .map { (filter, _, category) ->
                category.map {
                    FilterCrossCategoryEntity(category_id = it, filter_id = filter)
                }
            }.flatten()
        val groupCrossFilters = dataWithIds
            .map { (filter, _) ->
                GroupCrossFilterEntity(group_id = groupId, filter_id = filter)
            }
        insertFilterCrossTypes(filtersCrossTypes)
        insertFilterCrossCategories(filtersCrossCategories)
        insertGroupCrossFilters(groupCrossFilters)
        if (deleteExtraGroupFilters) {
            deleteExtraGroupFilters(groupId, dataWithIds.map { it.first })
        }
    }

    @Transaction
    open suspend fun deleteFilterGroupById(vararg ids: Long): Int {
        deleteFilterGroupEntityById(*ids)
        return deleteNonTemplateNotUsedFilters()
    }

    @Delete
    abstract fun deleteFilterGroup(group: FilterGroupEntity): Int

    @Query(
        """
      DELETE FROM ${DB_NAMES.FILTER_GROUP.table}
      WHERE ${DB_NAMES.FILTER_GROUP.id} IN (:ids)
   """
    )
    protected abstract fun deleteFilterGroupEntityById(vararg ids: Long): Int

    @Transaction
    @Query(
        """
      SELECT * 
      FROM ${DB_NAMES.FILTER_GROUP.table}
      WHERE ${DB_NAMES.FILTER_GROUP.template}
   """
    )
    abstract fun getAllTemplateGroups(): Flow<List<GroupCrossFilterRelation>>

    @Transaction
    @Query(
        """
         SELECT count(*)
         FROM ${DB_NAMES.FILTER_GROUP.table}
         WHERE ${DB_NAMES.FILTER_GROUP.template}
      """
    )
    abstract fun getTemplateGroupsCount(): Flow<Int>
}
