package com.dev.bayan.ibrahim.core.common.model

data class WordItem(
    override val id: Long,
    override val languageCode: String,
    val name: String,
    val description: String?,
) : ModelItem {
    override val value = name
    override val subtitle = description
}

data class SavableWordItem(
    override val id: Long,
    override val languageCode: String,
    val name: String,
    val description: String?,
    val meaningId: Long?,
    val typeId: Long,
    val categories: Set<Long>,
) : ModelItem {
    override val value = name
    override val subtitle = description
}
