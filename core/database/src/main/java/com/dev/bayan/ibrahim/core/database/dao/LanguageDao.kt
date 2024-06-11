package com.dev.bayan.ibrahim.core.database.dao


import androidx.room.*
import com.dev.bayan.ibrahim.core.common.jaar_annotation.JaArDbInternalApi
import com.dev.bayan.ibrahim.core.common.jaar_annotation.JaArDbPublicApi
import com.dev.bayan.ibrahim.core.database.entities.table.LanguageEntity
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.WORD
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.LANGUAGE
import kotlinx.coroutines.flow.Flow

@Dao
interface LanguageDao {
    @JaArDbInternalApi
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreAllLanguages(languages: List<LanguageEntity>)

    @JaArDbPublicApi
    @Query("SELECT COUNT(*) > 0 FROM ${LANGUAGE.table}")
    suspend fun isDbContainsAnyLanguage(): Boolean

    @JaArDbInternalApi
    @Query(
        "UPDATE ${LANGUAGE.table} " +
                "SET " +
                "${LANGUAGE.version} = :version, " +
                "${LANGUAGE.valid_characters} = :valid, " +
                "${LANGUAGE.search_ignorable_characters} = :ignorables, " +
                "${LANGUAGE.interchangable_characters} = :interchangeables, " +
                "${LANGUAGE.package_id} = :packageId, " +
                "${LANGUAGE.package_name} = :packageName, " +
                "${LANGUAGE.package_version} = :packageVersion, " +
                "${LANGUAGE.package_key} = :packageKey " +
                "WHERE ${LANGUAGE.code} = :code AND ${LANGUAGE.version} < :version"
    )
    suspend fun updateLanguagesIfNewerVersion(
        code: String,
        version: Int,
        valid: String,
        ignorables: String,
        interchangeables: List<List<Char>>,
        packageId: Long,
        packageName: String,
        packageVersion: Int,
        packageKey: String,
    )

    @JaArDbPublicApi
    @Transaction
    suspend fun insertOrUpdateIfNewerVersionAllLanguages(languages: List<LanguageEntity>) {
        insertOrIgnoreAllLanguages(languages)
        languages.forEach {
            updateLanguagesIfNewerVersion(
                code = it.language_code,
                version = it.language_version,
                valid = it.valid_characters,
                ignorables = it.search_ignorable_characters,
                interchangeables = it.interchangable_characters_groups,
                packageId = it.package_id,
                packageName = it.package_name,
                packageVersion = it.language_version,
                packageKey = it.package_key,
            )
        }
    }

    @Query("DELETE FROM ${WORD.table} WHERE ${WORD.language_code} = :languageCode")
    suspend fun deleteContentOfLanguage(languageCode: String): Int

    @JaArDbPublicApi
    @Query(
        "SELECT * FROM ${LANGUAGE.table} " +
                "WHERE ${LANGUAGE.code} IN (" +
                "SELECT ${WORD.language_code} " +
                "FROM ${WORD.table}" +
                ")"
    )
    fun getAllNonEmptyLanguages(): Flow<List<LanguageEntity>>

    @JaArDbPublicApi
    @Query("SELECT * FROM ${LANGUAGE.table}")
    fun getAllLanguages(): Flow<List<LanguageEntity>>

    @JaArDbPublicApi
    @Query("SELECT ${LANGUAGE.code} FROM ${LANGUAGE.table}")
    fun getAllLanguagesCodes(): Flow<List<String>>
}
