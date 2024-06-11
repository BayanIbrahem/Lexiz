package com.dev.bayan.ibrahim.feature.library.ui.screens.editor.category

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.bayan.ibrahim.core.common.model.INVALID_ID
import com.dev.bayan.ibrahim.core.common.model.ModelItem
import com.dev.bayan.ibrahim.core.common.model.SavableCategoryItem
import com.dev.bayan.ibrahim.core.common.regex_validator.RegexValidator
import com.dev.bayan.ibrahim.core.common.regex_validator.checkIfValid
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.core.ui.utils.inputDynamicString
import com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.init.LibraryDbEditorInitCategoryUseCase
import com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.save.save_new.LibraryDbEditorSaveNewCategoryUseCase
import com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.save.update.LibraryDbEditorSaveUpdateCategoryUseCase
import com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.suggest.LibraryDbEditorSuggestionCategoryUseCase
import com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.suggest.LibraryDbEditorSuggestionWordUseCase
import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.common.EditorFields
import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.steps.CategoryEditorSteps
import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.text_field.EditorFieldUiAction
import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.text_field.MutableEditorFieldUiState
import com.dev.bayan.ibrahim.feature.ui.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.dev.bayan.ibrahim.core.ui.R as UiRes

@HiltViewModel
internal class CategoryEditorViewModel @Inject constructor(
    private val initCategoryUseCase: LibraryDbEditorInitCategoryUseCase,

    private val suggestionWordUseCase: LibraryDbEditorSuggestionWordUseCase,
    private val suggestionCategoryUseCase: LibraryDbEditorSuggestionCategoryUseCase,

    private val saveNewCategoryUseCase: LibraryDbEditorSaveNewCategoryUseCase,

    private val saveUpdateCategoryUseCase: LibraryDbEditorSaveUpdateCategoryUseCase,
) : ViewModel() {
    fun getUiState(): CategoryEditorUiState = uiState

    private val immediateDispatcher = Dispatchers.Main.immediate
    private suspend fun onImmediate(job: suspend CoroutineScope.() -> Unit) =
        withContext(immediateDispatcher, job)

    private fun modelImmediate(job: suspend CoroutineScope.() -> Unit) = viewModelScope.launch {
        withContext(immediateDispatcher, job)
    }

    private var uiState = object : MutableCategoryEditorUiState {
        override var id: Long? by mutableStateOf(null)
        override var currentStep: CategoryEditorSteps by mutableStateOf(CategoryEditorSteps.NAME_DESCRIPTION)
        override var steps: PersistentMap<CategoryEditorSteps, Boolean> by mutableStateOf(
            CategoryEditorSteps.validationMap
        )
        override val nameField = generateMutableField(EditorFields.CATEGORY_NAME)
        override val descriptionField = generateMutableField(EditorFields.CATEGORY_DESCRIPTION)
        override val wordField = generateMutableField(EditorFields.CATEGORY_WORD)
    }

    private fun generateMutableField(input: EditorFields) = mutableStateOf(
        value = object : MutableEditorFieldUiState {
            override var id: Long? by mutableStateOf(null)
            override val input: EditorFields = input
            override var value: String by mutableStateOf("")
            override var error: JaArDynamicString? by mutableStateOf(null)
            override var suggestions: PersistentList<ModelItem>? by mutableStateOf(null)
            override var selectedValues: PersistentList<ModelItem>? by mutableStateOf(null)
        }
    ).value

    fun initCategory(id: Long?) {
        viewModelScope.launch(Dispatchers.IO) {
            id?.run {
                initCategoryUseCase(id)?.run {
                    onImmediate {
                        uiState.id = id
                        uiState.currentStep = CategoryEditorSteps.NAME_DESCRIPTION
                        uiState.steps =
                            CategoryEditorSteps.entries.associateWith { true }.toPersistentMap()
                        uiState.nameField.value = name
                        uiState.descriptionField.value = description ?: ""
                        uiState.wordField.selectedValues = initWords.toPersistentList()
                    }
                }
            } ?: onImmediate {
                uiState.apply {
                    this.id = null
                    currentStep = CategoryEditorSteps.NAME_DESCRIPTION
                    steps = CategoryEditorSteps.validationMap
                    nameField.apply {
                        this.id = null
                        value = ""
                        error = null
                        suggestions = null
                    }
                    uiState.descriptionField.value = ""
                    wordField.apply {
                        this.id = null
                        value = ""
                        error = null
                        selectedValues = null
                        suggestions = null
                    }

                }
            }
        }
    }


    private fun updateName(
        name: String,
    ) {
        uiState.nameField.apply {
            value = name
        }
        viewModelScope.launch(Dispatchers.IO) {
            suggestionCategoryUseCase(name, setOf()).let {
                uiState.nameField.suggestions = it.toPersistentList()
                val sameCategory = it.firstOrNull { it.same(name) }
                if (sameCategory != null) {
                    uiState.apply {
                        nameField.error = JaArDynamicString.StrRes(
                            R.string.category_existed_name,
                            sameCategory.name
                        )
                        steps = steps.put(CategoryEditorSteps.NAME_DESCRIPTION, false)
                    }
                } else if (name.isBlank()) {
                    uiState.apply {
                        nameField.error = JaArDynamicString.StrRes(UiRes.string.name_empty_body)
                        steps = steps.put(CategoryEditorSteps.NAME_DESCRIPTION, false)
                    }
                } else {
                    uiState.apply {
                        nameField.error = null
                        steps = steps.put(CategoryEditorSteps.NAME_DESCRIPTION, true)
                    }
                }
            }
        }
    }

    private fun updateDescription(
        description: String,
    ) {
        viewModelScope.launch {
            onImmediate {
                uiState.apply {
                    descriptionField.value = description
                    steps = uiState.steps.put(
                        key = CategoryEditorSteps.NAME_DESCRIPTION,
                        value = nameField.error == null && (nameField.value.isNotBlank() || descriptionField.value.isNotBlank())
                    )
                }
            }
        }
    }

    private fun updateWord(
        input: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            onImmediate {
                uiState.wordField.value = input
            }
            suggestionWordUseCase(
                wordName = input,
                existedIds = uiState.wordField.selectedValues?.map { it.id }?.toSet() ?: setOf(),
                ignorableChars = setOf(),
            ).let { suggestions ->
                onImmediate {
                    uiState.wordField.suggestions = suggestions.toPersistentList()
                }
            }
        }
    }

    private fun selectWord(
        category: ModelItem,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            onImmediate {
                uiState.wordField.apply {
                    value = ""
                    selectedValues = selectedValues?.add(category) ?: persistentListOf(category)
                }
            }
            suggestionCategoryUseCase(
                categoryName = "",
                existedIds = uiState.wordField.selectedValues?.map { it.id }?.toSet() ?: setOf()
            ).let { suggestions ->
                onImmediate {
                    uiState.wordField.suggestions = suggestions.toPersistentList()
                }
            }
        }
    }

    private fun removeWord(
        word: ModelItem,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            onImmediate {
                uiState.wordField.apply {
                    selectedValues = selectedValues?.remove(word)
                }
            }
            suggestionCategoryUseCase(
                categoryName = uiState.wordField.value,
                existedIds = uiState.wordField.selectedValues?.map { it.id }?.toSet() ?: setOf()
            ).let { suggestions ->
                onImmediate {
                    uiState.wordField.suggestions = suggestions.toPersistentList()
                }
            }
        }
    }

    private fun confirm(navigateUp: () -> Unit) {
        viewModelScope.launch {
            uiState.apply {
                if (steps.all { it.value }) {
                    val word = SavableCategoryItem(
                        id = id ?: INVALID_ID,
                        name = nameField.value,
                        description = descriptionField.value,
                        initWords = wordField.selectedValues?.map { it.id }?.toSet() ?: setOf()
                    )
                    if (id == null) {
                        saveNewCategoryUseCase(word)
                        initCategory(null)
                    } else {
                        saveUpdateCategoryUseCase(word)
                        navigateUp()
                    }
                }
            }
        }
    }


    fun getUiActions(navigateUp: () -> Unit) = object : CategoryEditorUiActions {
        override val nameFieldActions = object : EditorFieldUiAction {
            override fun onValueChange(newValue: String): Unit = updateName(
                name = newValue,
            )

            override fun onSelectSuggestion(suggestion: ModelItem) = updateName(
                name = suggestion.value,
            )

            override fun onRemoveFilterChip(model: ModelItem) { /* no selected values */
            }

        }
        override val descriptionFieldActions = object : EditorFieldUiAction {
            override fun onValueChange(newValue: String) = updateDescription(newValue)
            override fun onSelectSuggestion(suggestion: ModelItem) { /* no suggestions */
            }

            override fun onRemoveFilterChip(model: ModelItem) { /* no selected values */
            }
        }
        override val wordFieldActions = object : EditorFieldUiAction {
            override fun onValueChange(newValue: String) = updateWord(newValue)

            override fun onSelectSuggestion(suggestion: ModelItem) = selectWord(suggestion)

            override fun onRemoveFilterChip(model: ModelItem) = removeWord(model)
        }

        override fun onPreviousStep() {
            modelImmediate {
                uiState.apply {
                    currentStep = CategoryEditorSteps.entries[
                        currentStep.ordinal.dec().coerceAtLeast(0)
                    ]
                }
            }
        }

        override fun onNextStep() {
            modelImmediate {
                uiState.apply {
                    currentStep = CategoryEditorSteps.entries[
                        currentStep.ordinal.inc()
                            .coerceAtMost(CategoryEditorSteps.entries.count().dec())
                    ]
                }
            }
        }

        override fun onCancel() = navigateUp()

        override fun onConfirm() = confirm(navigateUp)
    }
}
