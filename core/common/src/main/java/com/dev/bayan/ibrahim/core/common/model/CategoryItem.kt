package com.dev.bayan.ibrahim.core.common.model

private interface Category : ModelItem {
    /**
     * return true is the two categories counted the same
     * it keeps only letters and digits from each name, then compare them ignoring case
     */
    fun same(other: Category): Boolean = same(other.value)

    /**
     * return true is the two categories counted the same
     * it keeps only letters and digits from each name, then compare them ignoring case
     */
    fun same(other: String): Boolean {
        val name = value.filter { it.isLetterOrDigit() }.lowercase()
        val otherName = other.filter { it.isLetterOrDigit() }.lowercase()
        return name == otherName
    }
}

data class CategoryItem(
    override val id: Long,
    val name: String,
    val description: String?,
) : Category {
    override val value = name
    override val subtitle = description
}

data class SavableCategoryItem(
    override val id: Long,
    val name: String,
    val description: String?,
    val initWords: Set<Long>,
) : Category {
    override val value = name
    override val subtitle = description
}

data class LoadableCategoryItem(
    override val id: Long,
    val name: String,
    val description: String?,
    val initWords: List<WordItem>,
) : Category {
    override val value = name
    override val subtitle = description
}
