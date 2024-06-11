package com.dev.bayan.ibrahim.core.database.entities.relation.word_type

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.dev.bayan.ibrahim.core.database.entities.table.TypeEntity
import com.dev.bayan.ibrahim.core.database.entities.table.WordEntity
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES

data class WordWithType(
    @Embedded val word: WordEntity,
    @Relation(
        entity = TypeEntity::class,
        parentColumn = DB_NAMES.WORD.type_id,
        entityColumn = DB_NAMES.TYPE.id,
        projection = [DB_NAMES.TYPE.name],
    )
    val typeName: String,
)