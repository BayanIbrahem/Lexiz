package com.dev.bayan.ibrahim.core.common.model

data class TypeItem(
    override val id: Long,
    override val languageCode: String,
    val name: String,
    val description: String,
) : ModelItem {
    override val value = name
    override val subtitle: String = description
}
