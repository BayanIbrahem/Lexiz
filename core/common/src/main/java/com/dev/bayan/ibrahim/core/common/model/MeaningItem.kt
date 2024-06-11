package com.dev.bayan.ibrahim.core.common.model

data class MeaningItem(
    override val id: Long,
    val wordName: String,
    override val languageCode: String,
) : ModelItem {
    override val value: String = wordName
}
