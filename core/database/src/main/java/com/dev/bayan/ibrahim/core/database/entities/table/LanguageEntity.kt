package com.dev.bayan.ibrahim.core.database.entities.table

import androidx.annotation.Size
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dev.bayan.ibrahim.core.common.model.LanguageItem
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.LANGUAGE
import java.util.Locale

/**
 * ja ar database, language table,
 * this table stores used languages in the application, any new item is added from [Locale.getLanguage]
 * @param language_code language 2 chars code
 * @param valid_characters valid characters for names in this languages
 * @param search_ignorable_characters characters will be ignored while searching
 */
@Entity(
    tableName = LANGUAGE.table,
)
data class LanguageEntity(
    @Size(value = 2)
    @PrimaryKey
    @ColumnInfo(name = LANGUAGE.code)
    val language_code: String,
    @ColumnInfo(name = LANGUAGE.version)
    val language_version: Int,
    @ColumnInfo(name = LANGUAGE.valid_characters)
    val valid_characters: String,
    @ColumnInfo(name = LANGUAGE.search_ignorable_characters)
    val search_ignorable_characters: String,
    @ColumnInfo(name = LANGUAGE.interchangable_characters)
    val interchangable_characters_groups: List<List<Char>>,

    @ColumnInfo(name = LANGUAGE.package_id)
    val package_id: Long,
    @ColumnInfo(name = LANGUAGE.package_name)
    val package_name: String,
    @ColumnInfo(name = LANGUAGE.package_version)
    val package_version: Int,
    @ColumnInfo(name = LANGUAGE.package_key)
    val package_key: String,
)
