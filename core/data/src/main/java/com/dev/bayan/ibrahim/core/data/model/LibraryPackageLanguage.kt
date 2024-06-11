package com.dev.bayan.ibrahim.core.data.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Serializable
data class LibraryPackageLanguage(
    val version: Int,
    val language_code: String,
    val interchangeables: List<List<Char>>,
    val ignorables: List<Char>,
    val valid_chars: List<Char>,
    val types: List<LibraryPackageType>,
)