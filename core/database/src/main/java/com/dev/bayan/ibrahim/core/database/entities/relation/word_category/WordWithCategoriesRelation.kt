package com.dev.bayan.ibrahim.core.database.entities.relation.word_category

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.dev.bayan.ibrahim.core.database.entities.table.WordEntity
import com.dev.bayan.ibrahim.core.database.entities.table.CategoryEntity
import com.dev.bayan.ibrahim.core.database.entities.table.WordCrossCategoryEntity
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.WORD
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.CATEGORY
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.WORD_CROSS_CATEGORY


data class WordWithCategoriesRelation(
    @Embedded val word: WordEntity,
    @Relation(
        parentColumn = WORD.id,
        entityColumn = CATEGORY.id,
        associateBy = Junction(
            value = WordCrossCategoryEntity::class,
            parentColumn = WORD_CROSS_CATEGORY.word_id,
            entityColumn = WORD_CROSS_CATEGORY.category_id
        ),
    )
    val categories: List<CategoryEntity>
)