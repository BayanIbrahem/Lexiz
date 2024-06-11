package com.dev.bayan.ibrahim.core.database.dao


import androidx.room.*
import com.dev.bayan.ibrahim.core.common.jaar_annotation.JaArDbInternalApi
import com.dev.bayan.ibrahim.core.common.jaar_annotation.JaArDbPublicApi
import com.dev.bayan.ibrahim.core.database.entities.sub_tables.TypeNameLanguageId
import com.dev.bayan.ibrahim.core.database.entities.table.TypeEntity
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.WORD
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.TYPE
import kotlinx.coroutines.flow.Flow

@Dao
interface TypeDao {
    @JaArDbInternalApi
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreAllTypes(types: List<TypeEntity>)

    @JaArDbInternalApi
    @Query(
        "UPDATE ${TYPE.table} " +
                "SET " +
                "${TYPE.name} = :name, " +
                "${TYPE.description} = :description, " +
                "${TYPE.type_version} = :version " +
                "WHERE ${TYPE.id} = :id AND ${TYPE.type_version} < :version"
    )
    suspend fun updateTypesIfNewerVersion(
        id: Long,
        name: String,
        description: String,
        version: Int,
    )

    @JaArDbPublicApi
    @Transaction
    suspend fun insertOrUpdateIfNewerVersionAllTypes(types: List<TypeEntity>) {
        insertOrIgnoreAllTypes(types)
        types.forEach {
            updateTypesIfNewerVersion(
                id = it.id,
                name = it.name,
                description = it.description,
                version = it.type_version
            )
        }
    }

    @JaArDbPublicApi
    @Query(
        "SELECT * FROM ${TYPE.table} " +
                "WHERE ${TYPE.id} = (" +
                "SELECT ${WORD.type_id} FROM ${WORD.table} WHERE ${WORD.id} = :wordId" +
                ")"
    )
    fun getTypeOfWord(wordId: Long): Flow<TypeEntity>

    @JaArDbPublicApi
    @Query("SELECT * FROM ${TYPE.table} WHERE ${TYPE.language} = :languageCode")
    fun getAllTypesOfLanguage(languageCode: String): Flow<List<TypeEntity>>


    @JaArDbPublicApi
    @Query("SELECT ${TYPE.id}, ${TYPE.name}, ${TYPE.language} FROM ${TYPE.table}")
    fun getAllTypesNames(): Flow<List<TypeNameLanguageId>>

    @JaArDbPublicApi
    @Query("SELECT * FROM ${TYPE.table}")
    fun getAllTypes(): Flow<List<TypeEntity>>

    @JaArDbPublicApi
    @Query(
        """
            SELECT
                ${TYPE.id},
                ${TYPE.name},
                ${TYPE.language}
            FROM
                ${TYPE.table}
            WHERE
                ${TYPE.id} IN (
                    SELECT
                        ${WORD.type_id}
                    FROM
                        ${WORD.table}
                )
        """
    )
    fun getAllUsedTypesNames(): Flow<List<TypeNameLanguageId>>
}
