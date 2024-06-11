package com.dev.bayan.ibrahim.feature.quiz_builder.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.bayan.ibrahim.core.common.jaar_exception.filter.IllegalFilterKeyException
import com.dev.bayan.ibrahim.core.common.model.LanguageItem
import com.dev.bayan.ibrahim.core.common.null_coerce.coercedInOrNull
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.core.ui.res.modelDefaultName
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.FilterGroup
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.Quiz
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.FILTER_CATEGORY
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.FILTER_CATEGORY_KEY
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.FILTER_DIFFICULTY_RANGE
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.FILTER_DIFFICULTY_RANGE_KEY
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.FILTER_FAILURE_PERCENT
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.FILTER_FAILURE_PERCENT_KEY
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.FILTER_FAILURE_RANGE
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.FILTER_FAILURE_RANGE_KEY
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.FILTER_KEYS
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.FILTER_LENGTH
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.FILTER_LENGTH_KEY
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.FILTER_SUCCESS_PERCENT
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.FILTER_SUCCESS_PERCENT_KEY
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.FILTER_SUCCESS_RANGE
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.FILTER_SUCCESS_RANGE_KEY
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.FILTER_TYPE
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.FILTER_TYPE_KEY
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.Filter
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.name_index.QuizBuilderNewFilterNameIndexUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.name_index.QuizBuilderNewGroupNameIndexUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.name_index.QuizBuilderNewQuizNameIndexUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.raw.QuizBuilderAllCategoriesUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.raw.QuizBuilderAllLanguagesUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.raw.QuizBuilderAllTypesUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.raw.QuizBuilderFailurePeakUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.raw.QuizBuilderLengthPeakUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.raw.QuizBuilderSelectedLanguageTypesUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.raw.QuizBuilderSuccessPeakUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.template.delete.QuizBuilderDeleteFilterUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.template.delete.QuizBuilderDeleteGroupUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.template.get.QuizBuilderTemplateFilterUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.template.get.QuizBuilderTemplateGroupUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.template.get.QuizBuilderTemplateQuizUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.template.upsert.QuizBuilderUpsertFilterUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.use_case.template.upsert.QuizBuilderUpsertGroupUseCase
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.util.BuilderItemSaveStatus
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.new_group.page.new_filter.NewFilterEvent
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.new_group.page.new_filter.NewFilterUiState
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.new_group.page.new_filter_group.NewFilterGroupEvent
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.new_group.page.template_filter.TemplateFilterEvent
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.new_group.page.template_filter.TemplateFiltersUiState
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.quiz_setup.screen.QuizSetupEvent
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.quiz_setup.screen.QuizSetupUiState
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.quiz_setup.screen.asRuleBasedQuiz
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.template_group.TemplateFilterGroupEvent
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.template_group.TemplateFilterGroupUiState
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.utils.fab_optoins.QuizFabOptions
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.utils.top_app_bar.QuizTopAppBarDropDownMenu
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.utils.top_app_bar.QuizTopAppBarDropDownMenu.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.dev.bayan.ibrahim.core.ui.R as UiRes

@HiltViewModel
internal class QuizViewModel @Inject constructor(
    allLanguagesUseCase: QuizBuilderAllLanguagesUseCase,
    allTypesUseCase: QuizBuilderAllTypesUseCase,
    allCategoriesUseCase: QuizBuilderAllCategoriesUseCase,
    private val selectedLanguageTypesUseCase: QuizBuilderSelectedLanguageTypesUseCase,
    private val selectedLanguageCategoriesUseCase: QuizBuilderSelectedLanguageTypesUseCase,
    private val wordLengthPeakUseCase: QuizBuilderLengthPeakUseCase,
    private val wordFailurePeakUseCase: QuizBuilderFailurePeakUseCase,
    private val wordSuccessPeakUseCase: QuizBuilderSuccessPeakUseCase,

    templateFilterUseCase: QuizBuilderTemplateFilterUseCase,
    templateGroupUseCase: QuizBuilderTemplateGroupUseCase,
    private val templateQuizUseCase: QuizBuilderTemplateQuizUseCase,

    private val upsertFilterUseCase: QuizBuilderUpsertFilterUseCase,
    private val upsertGroupUseCase: QuizBuilderUpsertGroupUseCase,

    private val deleteFilterUseCase: QuizBuilderDeleteFilterUseCase,
    private val deleteGroupUseCase: QuizBuilderDeleteGroupUseCase,

    newQuizNameIndexUseCase: QuizBuilderNewQuizNameIndexUseCase,
    newGroupNameIndexUseCase: QuizBuilderNewGroupNameIndexUseCase,
    newFilterNameIndexUseCase: QuizBuilderNewFilterNameIndexUseCase,
) : ViewModel() {
    private val immediateDispatcher = Dispatchers.Main.immediate
    private suspend fun inImmediateDispatcher(job: suspend () -> Unit) =
        withContext(immediateDispatcher) { job() }

    private val newQuizNameIndex = newQuizNameIndexUseCase()
    private val newGroupNameIndex = newGroupNameIndexUseCase()
    private val newFilterNameIndex = newFilterNameIndexUseCase()
    private val templateFiltersFlow = templateFilterUseCase()

    private var groupIndexInQuiz: Int? = null
    private var filterIndexInGroup: Int? = null

    // mutable states:
    private val _newFiltersUiState = MutableStateFlow(NewFilterUiState())
    private val _templateFiltersUiState = MutableStateFlow<Long?>(null)
    private val _newGroupUiState = MutableStateFlow(FilterGroup())
    private val _quizSetupUiState = MutableStateFlow(QuizSetupUiState())
    private val newGroupFiltersKeys = _newGroupUiState.map { group ->
        group.filters.map { it.key }.toPersistentSet()
    }

    // immutable states:
    val newFiltersUiState: StateFlow<NewFilterUiState> =
        _newFiltersUiState.combine(newGroupFiltersKeys) { uiState, keys ->
            uiState.copy(
                unAllowedFilterKeys = keys
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = NewFilterUiState()
        )
    val templateFiltersUiState = newGroupFiltersKeys.combine(_templateFiltersUiState) { keys, id ->
        keys to id
    }.combine(
        templateFiltersFlow
    ) { (unAllowedKeys, id), templates ->
        TemplateFiltersUiState(
            selectedFilterId = templates.firstOrNull { it.id == id }?.id,
            filters = templates.toPersistentList(),
            unAllowedFilterKeys = unAllowedKeys,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TemplateFiltersUiState()
    )
    val newGroupUiState: StateFlow<FilterGroup> = _newGroupUiState
    val templateGroupUiState: StateFlow<TemplateFilterGroupUiState> =
        templateGroupUseCase().map { groups ->
            TemplateFilterGroupUiState(groups.toPersistentList())
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TemplateFilterGroupUiState()
        )
    val quizSetupSelectedGroupsIds: StateFlow<PersistentSet<Long>> =
        _quizSetupUiState.map { uiState ->
            uiState.groups.mapNotNull { it.id }.toPersistentSet()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = persistentSetOf()
        )
    val quizSetupUiState: StateFlow<QuizSetupUiState> = _quizSetupUiState

    // db flows:
    val allLanguages = allLanguagesUseCase().map {
        it.toPersistentList()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = persistentListOf()
    )
    val allTypes = allTypesUseCase().map {
        it.toPersistentList()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = persistentListOf()
    )
    val allCategories = allCategoriesUseCase().map {
        it.toPersistentList()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = persistentListOf()
    )


    fun onQuizSetupEvent(
        event: QuizSetupEvent,
        navigateToTemplateGroup: () -> Unit,
        navigateToNewGroup: () -> Unit,
        navigateUp: () -> Unit,
    ) {
        viewModelScope.launch {
            inImmediateDispatcher {
                _quizSetupUiState.update { event.onApplyEvent(quizSetupUiState.value) }
            }
            when (event) {
                QuizSetupEvent.InitScreen -> quizSetupInit()
                QuizSetupEvent.OnAddTemplateGroup -> inImmediateDispatcher {
                    navigateToTemplateGroup()
                }

                QuizSetupEvent.OnAddNewGroup -> inImmediateDispatcher {
                    _newGroupUiState.invalidate()
                    navigateToNewGroup()
                }

                is QuizSetupEvent.OnEditGroup -> {
                    inImmediateDispatcher {
                        _newGroupUiState.value = quizSetupUiState.value.groups[event.index]
                        groupIndexInQuiz = event.index
                        navigateToNewGroup()
                    }
                }

                is QuizSetupEvent.OnClickTopAppBarMenuItem -> onQuizSetupMenuItemClick(event.item)
                is QuizSetupEvent.OnFabOption -> onQuizSetupFabOptionClick(event.option)
                QuizSetupEvent.OnNavigateUp -> inImmediateDispatcher(navigateUp)
                else -> { /* already handled*/
                }
            }
        }
    }

    fun onTemplateGroupEvent(
        event: TemplateFilterGroupEvent,
        navigateToNewGroup: () -> Unit,
        navigateUp: () -> Unit,
    ) {
        viewModelScope.launch {
            when (event) {
                TemplateFilterGroupEvent.OnCancel -> inImmediateDispatcher { navigateUp() }
                is TemplateFilterGroupEvent.OnClickGroup -> {
                    inImmediateDispatcher {
                        _quizSetupUiState.update {
                            it.copy(
                                groups = it.groups.add(
                                    element = templateGroupUiState.value.groups[event.index]
                                )
                            )
                        }
                        navigateUp()
                    }
                }

                is TemplateFilterGroupEvent.OnEditGroup -> {
                    inImmediateDispatcher {
                        _newGroupUiState.value = templateGroupUiState.value.groups[event.index]
                        navigateToNewGroup()
                    }
                }

                is TemplateFilterGroupEvent.OnRemoveGroup -> {
                    templateGroupUiState.value.groups.getOrNull(event.index)?.id?.let {
                        deleteGroup(it)
                    }
                }
            }
        }
    }

    fun onNewGroupEvent(
        event: NewFilterGroupEvent,
        navigateUp: () -> Unit,
    ) {
        viewModelScope.launch {
            inImmediateDispatcher {
                _newGroupUiState.update { event.onApplyEvent(newGroupUiState.value) }
            }
            when (event) {
                NewFilterGroupEvent.InitScreen -> newGroupInit()
                NewFilterGroupEvent.OnApplyWithoutSave -> {
                    inImmediateDispatcher {
                        val value = newGroupUiState.value.copy(
                            id = null,
                            saveStatus = BuilderItemSaveStatus.NEW,
                        )
                        _quizSetupUiState.update {
                            it.copy(
                                groups = it.groups.run {
                                    groupIndexInQuiz?.let { i ->
                                        set(i, value)
                                    } ?: add(value)
                                }
                            )
                        }
                        navigateUp()
                        groupIndexInQuiz = null
                    }
                }

                NewFilterGroupEvent.OnCancel -> {
                    inImmediateDispatcher { navigateUp() }
                    groupIndexInQuiz = null
                }

                NewFilterGroupEvent.OnConfirm -> {
                    saveGroup(newGroupUiState.value).await()?.let { id ->
                        val value = newGroupUiState.value.run {
                            copy(id = id, saveStatus = BuilderItemSaveStatus.SAVED)
                        }
                        inImmediateDispatcher {
                            _quizSetupUiState.update {
                                it.copy(
                                    groups = it.groups.run {
                                        groupIndexInQuiz?.let { i ->
                                            set(i, value)
                                        } ?: add(value)
                                    }
                                )
                            }
                            navigateUp()
                            groupIndexInQuiz = null
                        }
                    }
                }

                is NewFilterGroupEvent.OnEditFilter -> {
                    inImmediateDispatcher {
                        newGroupUiState.value.filters[event.index].run {
                            _newFiltersUiState.value = NewFilterUiState(
                                selectedFilter = this,
                                unAllowedFilterKeys = FILTER_KEYS.remove(this.key)
                            )
                        }
                    }
                    filterIndexInGroup = event.index
                }

                else -> { /* already handled */
                }
            }
        }
    }

    fun onTemplateFilterEvent(
        event: TemplateFilterEvent,
    ) {
        viewModelScope.launch {
            when (event) {
                is TemplateFilterEvent.OnClickFilter -> {
                    inImmediateDispatcher {
                        _templateFiltersUiState.value = event.id
                    }
                }

                TemplateFilterEvent.OnConfirm -> {
                    inImmediateDispatcher {
                        templateFiltersUiState.value.run {
                            filters.firstOrNull {
                                it.id == selectedFilterId
                            }
                        }?.let { filter ->
                            _newGroupUiState.update {
                                it.copy(
                                    filters = it.filters.add(filter)
                                )
                            }
                        }
                        _templateFiltersUiState.value = null
                    }
                }

                TemplateFilterEvent.OnRemove -> {
                    templateFiltersUiState.value.selectedFilterId?.let {
                        deleteFilter(it)
                    }
                    _templateFiltersUiState.value = null
                }

                else -> { /* already handled */
                }
            }
        }
    }

    fun onNewFilterEvent(
        event: NewFilterEvent,
    ) {
        viewModelScope.launch {
            val indexes = newFilterNameIndex.first()
            val mostFailed = wordFailurePeakUseCase()
            val longest = wordLengthPeakUseCase()
            val mostSucceeded = wordSuccessPeakUseCase()
            _newFiltersUiState.update { uiState ->
                event.onApplyEvent(uiState) { key ->
                    getDefaultFilterValue(
                        key = key,
                        index = indexes[key] ?: 1,
                        mostFailed = mostFailed,
                        longest = longest,
                        mostSucceeded = mostSucceeded,
                    )
                }
            }
            when (event) {
                NewFilterEvent.InitScreen -> newFilterInit()
                NewFilterEvent.OnApplyWithoutSave -> {
                    inImmediateDispatcher {
                        newFiltersUiState.value.selectedFilter?.let { value ->
                            val filter = value.copyContent(
                                id = null,
                                saveStatus = BuilderItemSaveStatus.NEW
                            )
                            _newGroupUiState.update {
                                it.copy(
                                    filters = it.filters.run {
                                        filterIndexInGroup?.let { i ->
                                            set(i, filter)
                                        } ?: add(filter)
                                    }
                                )
                            }
                            filterIndexInGroup = null
                        }
                    }
                    _newFiltersUiState.invalidate()
                }

                NewFilterEvent.OnConfirm -> {
                    newFiltersUiState.value.selectedFilter?.let { filter ->
                        saveFilter(filter).await()?.let { id ->
                            val value = filter.copyContent(
                                id = id,
                                saveStatus = BuilderItemSaveStatus.SAVED
                            )
                            inImmediateDispatcher {
                                _newGroupUiState.update {
                                    it.copy(
                                        filters = it.filters.run {
                                            filterIndexInGroup?.let { i ->
                                                set(i, value)
                                            } ?: add(value)
                                        }
                                    )
                                }
                                filterIndexInGroup = null
                            }
                        }
                    }
                    _newFiltersUiState.invalidate()
                }

                NewFilterEvent.OnCancel -> {
                    filterIndexInGroup = null
                }

                else -> { /* already handled */
                }
            }
        }
    }

    private fun quizSetupInit() {
        viewModelScope.launch {
            val index = newQuizNameIndex.first()
            _quizSetupUiState.update {
                it.copy(name = JaArDynamicString.Builder {
                    modelDefaultName(model = UiRes.plurals.quiz, number = index)
                })
            }
        }
    }

    private fun newGroupInit() {
        viewModelScope.launch {
            if (newGroupUiState.value.name.valueOrNull.isNullOrBlank()) {
                val index = newGroupNameIndex.first()
                _newGroupUiState.update {
                    it.copy(
                        name = JaArDynamicString.Builder {
                            modelDefaultName(model = UiRes.plurals.group, number = index)
                        }
                    )
                }
            }
        }
    }

    private fun newFilterInit() {
        viewModelScope.launch {
            quizSetupUiState.value.firstLanguage.let {
                val types = it?.getLanguageTypes()
                val categories = it?.getLanguageCategories()
                inImmediateDispatcher {
                    _newFiltersUiState.update {
                        it.copy(
                            firstLanguageTypes = types?.await(),
                            firstLanguageCategories = categories?.await(),
                        )
                    }
                }
            }
        }
    }

    private suspend fun onQuizSetupMenuItemClick(item: QuizTopAppBarDropDownMenu) {
        when (item) {
            SAVE_RULE -> {
                saveRuleBasedQuiz(quizSetupUiState.value.asRuleBasedQuiz(copy = false))
            }

            SAVE_WORD -> {
                // todo save quiz word based
                saveWordBasedQuiz(quizSetupUiState.value.asRuleBasedQuiz(copy = false))
            }

            SAVE_COPY_RULE -> {
                saveRuleBasedQuiz(quizSetupUiState.value.asRuleBasedQuiz(copy = false))
            }

            SAVE_COPY_WORD -> {
                // todo save quiz word based
                saveWordBasedQuiz(quizSetupUiState.value.asRuleBasedQuiz(copy = false))
            }

            RESET -> _quizSetupUiState.invalidate()
            CONSUME -> { /*already handled*/
            }
        }
    }

    private suspend fun onQuizSetupFabOptionClick(option: QuizFabOptions) {
        when (option) {
            QuizFabOptions.START -> {
                // todo, start quiz runner
            }
        }
    }

    @JvmName("MutableStateFlowQuizSetupUiStateInvalidate")
    private fun MutableStateFlow<QuizSetupUiState>.invalidate() {
        value = QuizSetupUiState()
    }

    @JvmName("MutableStateFlowTemplateFilterGroupUiStateInvalidate")
    private fun MutableStateFlow<TemplateFilterGroupUiState>.invalidate() {
        value = TemplateFilterGroupUiState()
    }

    @JvmName("MutableStateFlowFilterGroupInvalidate")
    private fun MutableStateFlow<FilterGroup>.invalidate() {
        value = FilterGroup()
    }

    @JvmName("MutableStateFlowTemplateFiltersUiStateInvalidate")
    private fun MutableStateFlow<TemplateFiltersUiState>.invalidate() {
        value = TemplateFiltersUiState()
    }

    @JvmName("MutableStateFlowNewFilterUiStateInvalidate")
    private fun MutableStateFlow<NewFilterUiState>.invalidate() {
        value = NewFilterUiState()
        newFilterInit()
    }


    private fun saveRuleBasedQuiz(quiz: Quiz) = viewModelScope.async<Long?> {
        // todo
        null
    }

    private fun saveWordBasedQuiz(quiz: Quiz) = viewModelScope.async<Long?> {
        // todo
        null
    }

    private fun saveGroup(group: FilterGroup) = viewModelScope.async {
        upsertGroupUseCase(group)?.also { groupId ->
            updateSelectedGroupStatus(
                newStatus = BuilderItemSaveStatus.SAVED,
                id = groupId,
                index = groupIndexInQuiz
            )
        }
    }


    private fun saveFilter(filter: Filter) = viewModelScope.async {
        upsertFilterUseCase(filter)?.also { filterId ->
            updateSelectedFilterStatus(
                newStatus = BuilderItemSaveStatus.SAVED,
                id = filterId,
                index = filterIndexInGroup
            )
        }
    }

    private fun deleteFilter(id: Long) {
        viewModelScope.launch() {
            deleteFilterUseCase(id)
            updateSelectedFilterStatus(newStatus = BuilderItemSaveStatus.NEW, id = id)
        }
    }

    private fun deleteGroup(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            if (deleteGroupUseCase(id)) {
                updateSelectedGroupStatus(newStatus = BuilderItemSaveStatus.NEW, id = id)
            }
        }
    }

    private fun updateSelectedGroupStatus(
        newStatus: BuilderItemSaveStatus,
        index: Int? = null,
        id: Long? = null,
    ) {
        if (id != null || index != null) {
            viewModelScope.launch {
                quizSetupUiState.value.groups.run {
                    val groupIndex = index ?: indexOfFirst {
                        it.id == id
                    }.coercedInOrNull(this)

                    groupIndex?.let {
                        this.set(
                            index = it,
                            element = this[it].run {
                                if (id != null) {
                                    copy(id = id, saveStatus = newStatus)
                                } else {
                                    copy(saveStatus = newStatus)
                                }
                            }
                        )
                    }
                }?.let { newGroups ->
                    inImmediateDispatcher {
                        _quizSetupUiState.update {
                            it.copy(groups = newGroups)
                        }
                    }
                }
            }
        }
    }

    private fun updateSelectedFilterStatus(
        newStatus: BuilderItemSaveStatus,
        id: Long? = null,
        index: Int? = null,
    ) {
        if (id != null || index != null) {
            viewModelScope.launch {
                newGroupUiState.value.filters.run {
                    val filterIndex = index ?: indexOfFirst {
                        it.id == id
                    }.coercedInOrNull(this)

                    filterIndex?.let {
                        this.set(
                            index = it,
                            element = this[it].run {
                                if (id != null) {
                                    copyContent(id = id, saveStatus = newStatus)
                                } else {
                                    copyContent(saveStatus = newStatus)
                                }
                            }
                        )
                    }
                }?.let { newFilters ->
                    inImmediateDispatcher {
                        _newGroupUiState.update {
                            it.copy(filters = newFilters)
                        }
                    }
                }
            }
        }
    }

    private suspend fun LanguageItem.getLanguageTypes() = viewModelScope.async {
        selectedLanguageTypesUseCase(languageCode).toPersistentSet()
    }

    private suspend fun LanguageItem.getLanguageCategories() = viewModelScope.async {
        selectedLanguageCategoriesUseCase(languageCode).toPersistentSet()
    }

    private fun getDefaultFilterValue(
        key: Int,
        index: Int,
        mostFailed: Int?,
        longest: Int?,
        mostSucceeded: Int?,
    ): Filter {
        return when (key) {
            FILTER_TYPE_KEY -> FILTER_TYPE.run { copy(name = getDefaultName(index)) }
            FILTER_CATEGORY_KEY -> FILTER_CATEGORY.run { copy(name = getDefaultName(index)) }
            FILTER_LENGTH_KEY -> longest?.run {
                FILTER_LENGTH.run {
                    copy(
                        name = getDefaultName(index), validRange = 1..index.coerceAtLeast(5)
                    )
                }
            } ?: FILTER_LENGTH.run { copy(name = getDefaultName(index)) }

            FILTER_DIFFICULTY_RANGE_KEY -> FILTER_DIFFICULTY_RANGE.run {
                copy(
                    name = getDefaultName(
                        index
                    )
                )
            }

            FILTER_FAILURE_RANGE_KEY -> mostFailed?.run {
                FILTER_FAILURE_RANGE.run {
                    copy(
                        name = getDefaultName(index), validRange = 1..index.coerceAtLeast(5)
                    )
                }
            } ?: FILTER_FAILURE_RANGE.run { copy(name = getDefaultName(index)) }

            FILTER_SUCCESS_RANGE_KEY -> mostSucceeded?.run {
                FILTER_SUCCESS_RANGE.run {
                    copy(
                        name = getDefaultName(index), validRange = 1..index.coerceAtLeast(5)
                    )
                }
            } ?: FILTER_SUCCESS_RANGE.run { copy(name = getDefaultName(index)) }

            FILTER_FAILURE_PERCENT_KEY -> FILTER_FAILURE_PERCENT.run {
                copy(
                    name = getDefaultName(
                        index
                    )
                )
            }

            FILTER_SUCCESS_PERCENT_KEY -> FILTER_SUCCESS_PERCENT.run {
                copy(
                    name = getDefaultName(
                        index
                    )
                )
            }

            else -> throw IllegalFilterKeyException(key)
        }
    }
}
