package com.dev.bayan.ibrahim.core.database.entities.sub_tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.dev.bayan.ibrahim.core.common.model.CategoryItem
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES

data class CategoryNameId(
    @ColumnInfo(DB_NAMES.CATEGORY.id)
    val id: Long,
    @ColumnInfo(DB_NAMES.CATEGORY.name)
    val name: String,
)

fun CategoryNameId.asCategoryItem(): CategoryItem = CategoryItem(
    id = id,
    name = name,
    description = ""
)
