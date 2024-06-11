package com.dev.bayan.ibrahim.core.database.entities.relation.word_category_type

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.dev.bayan.ibrahim.core.database.entities.relation.word_type.WordWithType
import com.dev.bayan.ibrahim.core.database.entities.sub_tables.CategoryNameId
import com.dev.bayan.ibrahim.core.database.entities.table.WordCrossCategoryEntity
import com.dev.bayan.ibrahim.core.database.entities.table.WordEntity
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.WORD
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.CATEGORY
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES.WORD_CROSS_CATEGORY


data class CategoryWithWordsTypeRelation(
    @Embedded val category: CategoryNameId,
    @Relation(
        entity = WordEntity::class,
        parentColumn = CATEGORY.id,
        entityColumn = WORD.id,
        associateBy = Junction(
            value = WordCrossCategoryEntity::class,
            parentColumn = WORD_CROSS_CATEGORY.category_id,
            entityColumn = WORD_CROSS_CATEGORY.word_id,
        ),
    )
    val words: List<WordWithCategoriesTypeRelation>,
)

fun CategoryWithWordsTypeRelation.simpleString(linePrefix: String = ""): String {
    return StringBuilder().apply {
        append("\n${linePrefix}categories with words types relation\n")
        append("${linePrefix}\tcategory(${category.id}): ${category.name}\n")
        words.forEach { word ->
            append(word.simpleString("${linePrefix}\t"))
        }
    }.toString()
}