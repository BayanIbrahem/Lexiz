package com.dev.bayan.ibrahim.core.data.model

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LibraryLanguagesPackage(
    val name: String,
    val id: Long,
    val version: Int,
    val key: String,
    val languages: List<LibraryPackageLanguage>,
)