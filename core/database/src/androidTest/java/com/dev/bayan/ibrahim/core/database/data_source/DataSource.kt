package com.dev.bayan.ibrahim.core.database.data_source

import com.dev.bayan.ibrahim.core.database.entities.relation.filter.FilterCrossCategoryTypeRelation
import com.dev.bayan.ibrahim.core.database.entities.table.CategoryEntity
import com.dev.bayan.ibrahim.core.database.entities.table.FilterCrossCategoryEntity
import com.dev.bayan.ibrahim.core.database.entities.table.FilterCrossTypeEntity
import com.dev.bayan.ibrahim.core.database.entities.table.FilterEntity
import com.dev.bayan.ibrahim.core.database.entities.table.FilterGroupEntity
import com.dev.bayan.ibrahim.core.database.entities.table.GroupCrossFilterEntity
import com.dev.bayan.ibrahim.core.database.entities.table.LanguageEntity
import com.dev.bayan.ibrahim.core.database.entities.table.MeaningEntity
import com.dev.bayan.ibrahim.core.database.entities.table.TypeEntity
import com.dev.bayan.ibrahim.core.database.entities.table.WordCrossCategoryEntity
import com.dev.bayan.ibrahim.core.database.entities.table.WordEntity

val languagesDataSource = listOf<LanguageEntity>(
    LanguageEntity(
        language_code = "en",
        valid_characters = "abcdefghijklmnopqrstuvwxyz",
        search_ignorable_characters = "",
        interchangable_characters_groups = listOf(listOf('a', 'â')),
        language_version = 1,
        package_version = 1,
        package_name = "adsf",
        package_key = "asdf",
        package_id = 1,
    ),
    LanguageEntity(
        language_code = "ar",
        valid_characters = "ابتثجحخدذرزسشصضطظعغفقكلمنهويأآإ",
        search_ignorable_characters = "ًٌٍَُِ~ْ",
        interchangable_characters_groups = listOf(listOf('آ', 'ا')),
        language_version = 1,
        package_version = 1,
        package_name = "adsf",
        package_key = "asdf",
        package_id = 1,
    ),
)

val categoriesDataSource = listOf(
    CategoryEntity(id = 1, name = "tech", description = "technical words", 1000, 2000),
    CategoryEntity(id = 2, name = "food", description = "food words", 500, 3000),
)

val meaningsDataSource = listOf(
    MeaningEntity(id = 1),
    MeaningEntity(id = 2),
)

val typesDataSource = listOf<TypeEntity>(
    TypeEntity(id = 1, name = "verb", "verb description", language_code = "en", type_version = 1),
    TypeEntity(id = 2, name = "noun", "noun description", language_code = "en", type_version = 1),
    TypeEntity(id = 3, name = "فعل", "وصف الفعل", language_code = "ar", type_version = 1),
    TypeEntity(id = 4, name = "اسم", "وصف الاسم", language_code = "ar", type_version = 1),
    TypeEntity(id = 5, name = "جملة", "وصف الجملة", language_code = "ar", type_version = 1),
)
val wordsDataSource = listOf(
    WordEntity(
        id = 1,
        name = "water", description = "necessary drink", language_code = "en",
        addDate = 4000, lastEditDate = 4000,
        meaningId = 1, typeId = 2,
    ),
    WordEntity(
        id = 2,
        name = "act", description = null, language_code = "en",
        addDate = 5000, lastEditDate = 8000,
        meaningId = 2, typeId = 1,
    ),
    WordEntity(
        id = 3,
        name = null, description = "السعي إلى شيء ما", language_code = "ar",
        addDate = 9000, lastEditDate = 9000,
        meaningId = 2, typeId = 5,
    ),
)
val wordCrossCategoriesDataSource = listOf(
    WordCrossCategoryEntity(1, 1),
    WordCrossCategoryEntity(1, 2),
    WordCrossCategoryEntity(2, 2),
    WordCrossCategoryEntity(3, 1),
    WordCrossCategoryEntity(3, 2),
)


val filtersTemplatesDataSource = listOf(
    FilterEntity(
        id = 1,
        name = "filter 1",
        type = 0,// category type
        primary_value = null,
        secondary_value = null,
        tertiary_value = null,
        template = true
    ),
    FilterEntity(
        id = 2,
        name = "filter 2",
        type = 0, // word type type
        primary_value = null,
        secondary_value = null,
        tertiary_value = null,
        template = true
    ),
    FilterEntity(
        id = 3,
        name = "filter 3",
        type = 2, // word type type
        primary_value = 1f,
        secondary_value = null,
        tertiary_value = null,
        template = false,
    ),
)
val filtersCrossCategoriesDataSource = listOf(
    FilterCrossCategoryEntity(1, 1),
    FilterCrossCategoryEntity(2, 1),
)
val filterCrossTypesDataSource = listOf(
    FilterCrossTypeEntity(1, 2),
    FilterCrossTypeEntity(2, 2),
)

val filterCategoryTypeRelationDataSource =
    filtersTemplatesDataSource.map { filterEntity ->
        FilterCrossCategoryTypeRelation(
            filterEntity,
            filtersCrossCategoriesDataSource.mapNotNull { cross ->
                if (cross.filter_id == filterEntity.id) {
                    categoriesDataSource.first { it.id == cross.category_id }
                } else {
                    null
                }
            },
            filterCrossTypesDataSource.mapNotNull { cross ->
                if (cross.filter_id == filterEntity.id) {
                    typesDataSource.first { it.id == cross.type_id }
                } else {
                    null
                }
            }
        )
    }
val filterGroupsDataSource = listOf(
    FilterGroupEntity(
        id = 1,
        name = "group 1",
        template = true,
    ),
    FilterGroupEntity(
        id = 2,
        name = "group 2",
        template = false,
    ),
)
val filterGroupsCrossFiltersDataSource = listOf(
    GroupCrossFilterEntity(1, 1),
    GroupCrossFilterEntity(1, 2),
    GroupCrossFilterEntity(1, 3),
    GroupCrossFilterEntity(2, 2),
)
