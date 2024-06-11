package com.dev.bayan.ibrahim.feature.library.data.util

/**
 * name and description with id,
 */
data class IdWithNameDescription<T>(
    val id: T,
    private val nameAndDescription: Pair<String, String>?
) {
    val name: String?
        get() = nameAndDescription?.first

    val description: String?
        get() = nameAndDescription?.second

    val type = if (id == null && nameAndDescription != null) {
        IdWithNameDescriptionType.NEW_ITEM
    } else if (id != null) {
        IdWithNameDescriptionType.EXISTED_ITEM
    } else {
        IdWithNameDescriptionType.INVALID
    }
}