package com.dev.bayan.ibrahim.core.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dev.bayan.ibrahim.core.data.data_source.deserializedTestPackage
import com.dev.bayan.ibrahim.core.data.model.LibraryLanguagesPackage
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.InputStream

@RunWith(AndroidJUnit4::class)
class DeserializationTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private lateinit var jsonFile: InputStream

    @Before
    fun setup() {
        jsonFile = context.assets.open("test_package.json")

    }

    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun deserializeJson_deserializedCorrectly() {
        val myPackage: LibraryLanguagesPackage = Json.decodeFromStream(jsonFile)
        assertEquals(deserializedTestPackage, myPackage)
    }
}