package com.dev.bayan.ibrahim.core.database.converter

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class InterchangableCharactersConverter {
    @TypeConverter
    fun groupToString(groups: List<List<Char>>): String {
        return Json.encodeToString(groups)
    }

    @TypeConverter
    fun stringToGroups(jsonString: String): List<List<Char>> {
        return Json.decodeFromString(jsonString)
    }
}