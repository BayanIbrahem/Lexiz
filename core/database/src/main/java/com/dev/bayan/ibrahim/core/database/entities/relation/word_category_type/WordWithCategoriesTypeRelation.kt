package com.dev.bayan.ibrahim.core.database.entities.relation.word_category_type

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.dev.bayan.ibrahim.core.common.model.INVALID_ID
import com.dev.bayan.ibrahim.core.database.entities.relation.word_type.WordWithType
import com.dev.bayan.ibrahim.core.database.entities.sub_tables.CategoryNameId
import com.dev.bayan.ibrahim.core.database.entities.table.CategoryEntity
import com.dev.bayan.ibrahim.core.database.entities.table.WordCrossCategoryEntity
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.WORD
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.CATEGORY
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.WORD_CROSS_CATEGORY


data class WordWithCategoriesTypeRelation(
    @Embedded val wordWithType: WordWithType,
    @Relation(
        entity = CategoryEntity::class,
        parentColumn = WORD.id,
        entityColumn = CATEGORY.id,
        associateBy = Junction(
            value = WordCrossCategoryEntity::class,
            parentColumn = WORD_CROSS_CATEGORY.word_id,
            entityColumn = WORD_CROSS_CATEGORY.category_id
        ),
    )
    val categories: List<CategoryNameId>,
)

fun WordWithCategoriesTypeRelation.simpleString(linePrefix: String = ""): String {
    return StringBuilder().apply {
        append("\n${linePrefix}word(${wordWithType.word.id}): ${wordWithType.word.name}\n")
        append("${linePrefix}categories:\n")
        categories.forEach { category ->
            append("${linePrefix}\tcategory(${category.id}): ${category.name}\n")
        }
    }.toString()
}
