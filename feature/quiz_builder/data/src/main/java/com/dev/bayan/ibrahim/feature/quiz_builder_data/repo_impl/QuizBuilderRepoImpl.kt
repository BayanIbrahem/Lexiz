package com.dev.bayan.ibrahim.feature.quiz_builder_data.repo_impl

import com.dev.bayan.ibrahim.core.database.dao.CategoryDao
import com.dev.bayan.ibrahim.core.database.dao.FilterDao
import com.dev.bayan.ibrahim.core.database.dao.FilterGroupDao
import com.dev.bayan.ibrahim.core.database.dao.LanguageDao
import com.dev.bayan.ibrahim.core.database.dao.QuizDao
import com.dev.bayan.ibrahim.core.database.dao.TypeDao
import com.dev.bayan.ibrahim.core.database.dao.WordDao
import com.dev.bayan.ibrahim.core.database.dao.WordResultDao
import com.dev.bayan.ibrahim.core.database.entities.relation.filter.FilterCrossCategoryTypeRelation
import com.dev.bayan.ibrahim.core.database.entities.relation.filter.GroupCrossFilterRelation
import com.dev.bayan.ibrahim.core.database.entities.relation.quiz.QuizCrossFilterWordsRelation
import com.dev.bayan.ibrahim.core.database.entities.table.CategoryEntity
import com.dev.bayan.ibrahim.core.database.entities.table.FilterEntity
import com.dev.bayan.ibrahim.core.database.entities.table.FilterGroupEntity
import com.dev.bayan.ibrahim.core.database.entities.table.LanguageEntity
import com.dev.bayan.ibrahim.core.database.entities.table.TypeEntity
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.QuizBuilderRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class QuizBuilderRepoImpl @Inject constructor(
    private val languageDao: LanguageDao,
    private val typeDao: TypeDao,
    private val categoryDao: CategoryDao,

    private val wordDao: WordDao,
    private val wordResultDao: WordResultDao,
    private val filterDao: FilterDao,
    private val filterGroupDao: FilterGroupDao,
    private val quizDao: QuizDao,
) : QuizBuilderRepo {
    override fun setupAllLanguages(): Flow<List<LanguageEntity>> =
        languageDao.getAllLanguages()

    override fun setupAllTypes(): Flow<List<TypeEntity>> =
        typeDao.getAllTypes()

    override fun setupAllCategories(): Flow<List<CategoryEntity>> =
        categoryDao.getAllCategories()

    override fun setupSelectedLanguageTypes(code: String): Flow<List<Long>> =
        typeDao.getAllTypesOfLanguage(code).map { it.map { it.id } }

    override fun setupSelectedLanguageCategories(code: String): Flow<List<Long>> =
        categoryDao.getCategoriesIdsOfLanguage(code)

    override fun setupWordLengthPeak(): Flow<Int?> =
        wordDao.getLongestWordLength()

    override fun setupWordFailurePeak(): Flow<Int?> =
        wordResultDao.getMostFailWordCount()

    override fun setupWordSuccessPeak(): Flow<Int?> =
        wordResultDao.getMostSuccessWordCount()

    override fun setupTemplateFilters(): Flow<List<FilterCrossCategoryTypeRelation>> =
        filterDao.getAllTemplateFilters()

    override fun setupTemplateGroups(): Flow<List<GroupCrossFilterRelation>> =
        filterGroupDao.getAllTemplateGroups()

    override fun setupTemplateQuizzes(): Flow<List<QuizCrossFilterWordsRelation>> =
        quizDao.getAllTemplateQuizzes()

    override suspend fun setupInsertFilter(
        entity: FilterEntity,
        types: List<Long>,
        categories: List<Long>,
    ) =
        filterDao.insertFilterWithTypeCategory(entity, types, categories)

    override suspend fun setupUpdateFilter(
        entity: FilterEntity,
        newTypes: List<Long>,
        newCategories: List<Long>,
    ) = filterDao.updateFilterWithTypeCategory(
        entity = entity,
        newTypes = newTypes,
        newCategories = newCategories
    )


    override suspend fun setupInsertGroup(
        entity: FilterGroupEntity,
        data: List<Triple<FilterEntity, List<Long>, List<Long>>>,
    ) = filterGroupDao.insertGroupWithFilters(entity, data)

    override suspend fun setupUpdateGroup(
        entity: FilterGroupEntity,
        data: List<Triple<FilterEntity, List<Long>, List<Long>>>,
    ) = filterGroupDao.updateGroupsWithFilters(entity, data)

    override suspend fun setupDeleteFilters(id: Long): Int {
        return filterDao.deleteFilterOrMakeNonTemplate(id)
    }

    override suspend fun setupDeleteGroups(id: Long) = filterGroupDao.deleteFilterGroupById(id)
    override fun setupNewQuizNameIndex() = quizDao.getQuizzesCount().map { it.inc() }

    override fun setupNewGroupNameIndex() = filterGroupDao.getTemplateGroupsCount().map { it.inc() }

    override fun setupNewFilterNameIndex() = filterDao
        .getTemplateFiltersCount().map {
            it.mapValues { it.value.inc() }
        }
}