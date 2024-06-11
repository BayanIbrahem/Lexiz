package com.dev.bayan.ibrahim.core.data.utils

import com.dev.bayan.ibrahim.core.data.model.LibraryLanguagesPackage
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.InputStream


@OptIn(ExperimentalSerializationApi::class)
fun deserializePackageFromStream(json: InputStream): LibraryLanguagesPackage? {
    return try {
        Json.decodeFromStream<LibraryLanguagesPackage>(json)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}