package com.dev.bayan.ibrahim.core.database.entities.sub_tables

import androidx.room.ColumnInfo
import com.dev.bayan.ibrahim.core.common.model.TypeItem
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES

data class TypeNameLanguageId(
    @ColumnInfo(DB_NAMES.TYPE.id)
    val id: Long,
    @ColumnInfo(DB_NAMES.TYPE.name)
    val name: String,
    @ColumnInfo(DB_NAMES.TYPE.language)
    val language: String
)

fun TypeNameLanguageId.asTypeItem(): TypeItem = TypeItem(
    id = id,
    languageCode = language,
    name = name,
    description = ""
)
