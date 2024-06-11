package com.dev.bayan.ibrahim.core.database.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.dev.bayan.ibrahim.core.data.utils.deserializePackageFromStream
import com.dev.bayan.ibrahim.core.database.dao.*
import com.dev.bayan.ibrahim.core.database.dao.SafeWordDao
import com.dev.bayan.ibrahim.core.database.db.JaArDatabase
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES
import com.dev.bayan.ibrahim.core.database.utils.toLanguageTypeEntities
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private lateinit var jaArDatabase: JaArDatabase

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): JaArDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = JaArDatabase::class.java,
            name = DB_NAMES.db,
        ).build()
    }

    @Provides
    @Singleton
    fun provideCategoryDao(db: JaArDatabase): CategoryDao {
        return db.getCategoryDao()
    }

    @Provides
    @Singleton
    fun provideFilterDao(db: JaArDatabase): FilterDao {
        return db.getFilterDao()
    }

    @Provides
    @Singleton
    fun provideFilterGroupDao(db: JaArDatabase): FilterGroupDao {
        return db.getFilterGroupDao()
    }

    @Provides
    @Singleton
    fun provideLanguageDao(db: JaArDatabase): LanguageDao {
        return db.getLanguageDao()
    }

    @Provides
    @Singleton
    fun provideMeaningDao(db: JaArDatabase): MeaningDao {
        return db.getMeaningDao()
    }

    @Provides
    @Singleton
    fun provideQuizDao(db: JaArDatabase): QuizDao {
        return db.getQuizDao()
    }

    @Provides
    @Singleton
    fun provideResultDao(db: JaArDatabase): ResultDao {
        return db.getResultDao()
    }

    @Provides
    @Singleton
    fun provideTypeDao(db: JaArDatabase): TypeDao {
        return db.getTypeDao()
    }

    @Provides
    @Singleton
    fun provideWordDao(db: JaArDatabase): WordDao {
        return db.getWordDao()
    }

    @Provides
    @Singleton
    fun provideWordResultDao(db: JaArDatabase): WordResultDao {
        return db.getWordResultDao()
    }

    @Provides
    @Singleton
    fun provideSafeWordDao(db: JaArDatabase): SafeWordDao {
        return db.getSafeWordDao()
    }
}