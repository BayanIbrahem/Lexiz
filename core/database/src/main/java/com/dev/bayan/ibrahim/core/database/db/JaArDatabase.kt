package com.dev.bayan.ibrahim.core.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dev.bayan.ibrahim.core.database.converter.InterchangableCharactersConverter
import com.dev.bayan.ibrahim.core.database.dao.CategoryDao
import com.dev.bayan.ibrahim.core.database.dao.FilterDao
import com.dev.bayan.ibrahim.core.database.dao.FilterGroupDao
import com.dev.bayan.ibrahim.core.database.dao.LanguageDao
import com.dev.bayan.ibrahim.core.database.dao.MeaningDao
import com.dev.bayan.ibrahim.core.database.dao.QuizDao
import com.dev.bayan.ibrahim.core.database.dao.ResultDao
import com.dev.bayan.ibrahim.core.database.dao.TypeDao
import com.dev.bayan.ibrahim.core.database.dao.WordDao
import com.dev.bayan.ibrahim.core.database.dao.WordResultDao
import com.dev.bayan.ibrahim.core.database.dao.SafeWordDao
import com.dev.bayan.ibrahim.core.database.entities.table.FilterCrossCategoryEntity
import com.dev.bayan.ibrahim.core.database.entities.table.FilterGroupEntity
import com.dev.bayan.ibrahim.core.database.entities.table.LanguageEntity
import com.dev.bayan.ibrahim.core.database.entities.table.MeaningEntity
import com.dev.bayan.ibrahim.core.database.entities.table.QuizCrossFilterEntity
import com.dev.bayan.ibrahim.core.database.entities.table.QuizCrossWordEntity
import com.dev.bayan.ibrahim.core.database.entities.table.QuizEntity
import com.dev.bayan.ibrahim.core.database.entities.table.ResultEntity
import com.dev.bayan.ibrahim.core.database.entities.table.FilterCrossTypeEntity
import com.dev.bayan.ibrahim.core.database.entities.table.TypeEntity
import com.dev.bayan.ibrahim.core.database.entities.table.WordEntity
import com.dev.bayan.ibrahim.core.database.entities.table.WordResultEntity
import com.dev.bayan.ibrahim.core.database.entities.table.CategoryEntity
import com.dev.bayan.ibrahim.core.database.entities.table.FilterEntity
import com.dev.bayan.ibrahim.core.database.entities.table.GroupCrossFilterEntity
import com.dev.bayan.ibrahim.core.database.entities.table.WordCrossCategoryEntity
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES

@Database(
    entities = [
        CategoryEntity::class,
        FilterEntity::class, FilterCrossCategoryEntity::class, FilterCrossTypeEntity::class,
        FilterGroupEntity::class, GroupCrossFilterEntity::class,
        LanguageEntity::class,
        MeaningEntity::class,
        QuizEntity::class, QuizCrossFilterEntity::class, QuizCrossWordEntity::class,
        ResultEntity::class,
        TypeEntity::class,
        WordEntity::class,
        WordResultEntity::class, WordCrossCategoryEntity::class
    ],
    version = DB_NAMES.version,
    exportSchema = false,
)
@TypeConverters(InterchangableCharactersConverter::class)
abstract class JaArDatabase : RoomDatabase() {
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getFilterDao(): FilterDao
    abstract fun getFilterGroupDao(): FilterGroupDao
    abstract fun getLanguageDao(): LanguageDao
    abstract fun getMeaningDao(): MeaningDao
    abstract fun getQuizDao(): QuizDao
    abstract fun getResultDao(): ResultDao
    abstract fun getTypeDao(): TypeDao
    abstract fun getWordDao(): WordDao
    abstract fun getWordResultDao(): WordResultDao
    abstract fun getSafeWordDao(): SafeWordDao
}