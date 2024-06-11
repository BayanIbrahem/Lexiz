package com.dev.bayan.ibrahim.core.data.model

import kotlinx.serialization.Serializable


@Serializable
data class LibraryPackageType(
    val name: String,
    val id: Long,
    val version: Int,
    val language_code: String,
    val description: String,
)