package com.dev.bayan.ibrahim.core.database.entities.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dev.bayan.ibrahim.core.common.model.INVALID_ID
import com.dev.bayan.ibrahim.core.common.model.WordItem
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.LANGUAGE
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.MEANING
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.TYPE
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.WORD

/**
 * jaar database
 * this entity presents the word table (largest table in database in rows and columns) which has
 * all words data
 * @param id primary key of word
 *
 * @param name the word itself it may be null but in this case [description] must not be null
 * @param description the word description it may be null but in this case [name] must not be null
 * @param language_code this word used language
 *
 * @param addDate date of adding this word
 * @param lastEditDate date of last edit of this word
 *
 * @param meaningId (fk) id of this word meaning (which can we reach word's categories using it
 * @param typeId (fk) type of this word
 *
 * @see MeaningEntity
 * @see TypeEntity
 */
@Entity(
    tableName = WORD.table,
    indices = [
        Index(WORD.language_code, unique = false),
        Index(WORD.type_id, unique = false),
        Index(WORD.meaning_id, unique = false),
    ],
    foreignKeys = [
        ForeignKey(
            entity = LanguageEntity::class,
            parentColumns = [LANGUAGE.code],
            childColumns = [WORD.language_code],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = MeaningEntity::class,
            parentColumns = [MEANING.id],
            childColumns = [WORD.meaning_id],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = TypeEntity::class,
            parentColumns = [TYPE.id],
            childColumns = [WORD.type_id],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
    ]
)
data class WordEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = WORD.id)
    val id: Long? = null,

    @ColumnInfo(name = WORD.name)
    val name: String,
    @ColumnInfo(name = WORD.description)
    val description: String?,
    @ColumnInfo(name = WORD.language_code)
    val language_code: String,

    @ColumnInfo(name = WORD.add_date)
    val addDate: Long,
    @ColumnInfo(name = WORD.last_edit_date)
    val lastEditDate: Long = System.currentTimeMillis(),

    @ColumnInfo(name = WORD.meaning_id)
    val meaningId: Long,
    @ColumnInfo(name = WORD.type_id)
    val typeId: Long,
)


fun WordEntity.asWordItem(): WordItem = WordItem(
    id = id ?: INVALID_ID,
    languageCode = language_code,
    name = name,
    description = description,
)