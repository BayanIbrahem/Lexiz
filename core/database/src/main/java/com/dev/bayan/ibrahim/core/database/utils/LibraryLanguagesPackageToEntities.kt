package com.dev.bayan.ibrahim.core.database.utils

import com.dev.bayan.ibrahim.core.data.model.LibraryLanguagesPackage
import com.dev.bayan.ibrahim.core.database.entities.table.LanguageEntity
import com.dev.bayan.ibrahim.core.database.entities.table.TypeEntity


fun LibraryLanguagesPackage.toLanguageTypeEntities(
): Pair<List<LanguageEntity>, List<TypeEntity>> {
    val languagesEntities = mutableListOf<LanguageEntity>()
    val typesEntities = mutableListOf<TypeEntity>()
    this.languages.forEach { langPkg ->
        languagesEntities.add(
            LanguageEntity(
                language_code = langPkg.language_code,
                language_version = langPkg.version,
                valid_characters = langPkg.valid_chars.joinToString(""),
                search_ignorable_characters = langPkg.ignorables.joinToString(""),
                interchangable_characters_groups = langPkg.interchangeables,
                package_id = this.id,
                package_key = this.key,
                package_name = this.name,
                package_version = this.version,
            )
        )
        langPkg.types.forEach { typePkg ->
            typesEntities.add(
                TypeEntity(
                    id = typePkg.id,
                    name = typePkg.name,
                    description = typePkg.description,
                    language_code = typePkg.language_code,
                    type_version = typePkg.version,
                )
            )
        }
    }
    return languagesEntities to typesEntities
}