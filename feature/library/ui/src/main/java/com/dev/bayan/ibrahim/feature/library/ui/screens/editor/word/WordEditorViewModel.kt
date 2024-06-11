package com.dev.bayan.ibrahim.feature.library.ui.screens.editor.word

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.bayan.ibrahim.core.common.model.EditLanguageItem
import com.dev.bayan.ibrahim.core.common.model.INVALID_ID
import com.dev.bayan.ibrahim.core.common.model.LanguageItem
import com.dev.bayan.ibrahim.core.common.model.ModelItem
import com.dev.bayan.ibrahim.core.common.model.SavableWordItem
import com.dev.bayan.ibrahim.core.common.model.WordItem
import com.dev.bayan.ibrahim.core.common.regex_validator.RegexValidator
import com.dev.bayan.ibrahim.core.common.regex_validator.checkIfValid
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.core.ui.utils.inputDynamicString
import com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.init.LibraryDbEditorInitWordUseCase
import com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.save.save_new.LibraryDbEditorSaveNewWordUseCase
import com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.save.update.LibraryDbEditorSaveUpdateWordUseCase
import com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.suggest.LibraryDbEditorSuggestionCategoryUseCase
import com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.suggest.LibraryDbEditorSuggestionLanguageUseCase
import com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.suggest.LibraryDbEditorSuggestionMeaningUseCase
import com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.suggest.LibraryDbEditorSuggestionTypeUseCase
import com.dev.bayan.ibrahim.feature.library.domain.use_case.editor.suggest.LibraryDbEditorSuggestionWordUseCase
import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.common.EditorFields
import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.steps.WordEditorSteps
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
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
internal class WordEditorViewModel @Inject constructor(
    private val initWordUseCase: LibraryDbEditorInitWordUseCase,

    private val suggestionWordUseCase: LibraryDbEditorSuggestionWordUseCase,
    private val suggestionMeaningUseCase: LibraryDbEditorSuggestionMeaningUseCase,
    private val suggestionCategoryUseCase: LibraryDbEditorSuggestionCategoryUseCase,
    private val suggestionTypeUseCase: LibraryDbEditorSuggestionTypeUseCase,
    private val suggestionLanguageUseCase: LibraryDbEditorSuggestionLanguageUseCase,

    private val saveNewWordUseCase: LibraryDbEditorSaveNewWordUseCase,

    private val saveUpdateWordUseCase: LibraryDbEditorSaveUpdateWordUseCase,
) : ViewModel() {
    fun getUiState(): WordEditorUiState = uiState

    private val immediateDispatcher = Dispatchers.Main.immediate
    private suspend fun onImmediate(job: suspend CoroutineScope.() -> Unit) =
        withContext(immediateDispatcher, job)

    private fun modelImmediate(job: suspend CoroutineScope.() -> Unit) = viewModelScope.launch {
        withContext(immediateDispatcher, job)
    }

    private var selectedLanguage: EditLanguageItem? = null

    private var uiState = object : MutableWordEditorUiState {
        override var id: Long? by mutableStateOf(null)
        override var currentStep: WordEditorSteps by mutableStateOf(WordEditorSteps.LANGUAGE)
        override var steps: PersistentMap<WordEditorSteps, Boolean> by mutableStateOf(
            WordEditorSteps.validationMap
        )
        override val nameField = generateMutableField(EditorFields.WORD_NAME)
        override val descriptionField = generateMutableField(EditorFields.WORD_DESCRIPTION)
        override var languageField by mutableStateOf(object : MutableEditorFieldUiState {
            var languages: PersistentList<EditLanguageItem>? by mutableStateOf(null)
            override var id: Long? by mutableStateOf(null)
            override val input: EditorFields = EditorFields.LANGUAGE
            override var value: String by mutableStateOf("")
            override var error: JaArDynamicString? by mutableStateOf(null)
            override var suggestions: PersistentList<ModelItem>? = languages
            override var selectedValues: PersistentList<ModelItem>? by mutableStateOf(null)
        }
        )
        override val typeField = generateMutableField(EditorFields.TYPE)
        override val categoryField = generateMutableField(EditorFields.WORD_CATEGORY)
        override val meaningField = generateMutableField(EditorFields.WORD_MEANING)
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

    fun initWord(id: Long?) {
        viewModelScope.launch(Dispatchers.IO) {
            id?.run {
                initWordUseCase(id)?.run {
                    onImmediate {
                        uiState.id = id
                        uiState.currentStep = WordEditorSteps.LANGUAGE
                        uiState.steps =
                            WordEditorSteps.entries.associateWith { true }.toPersistentMap()
                        uiState.nameField.value = name ?: ""
                        uiState.descriptionField.value = description ?: ""
                        uiState.languageField.selectedValues = persistentListOf(language)
                        uiState.typeField.selectedValues = persistentListOf(type)
                        uiState.categoryField.selectedValues = categories.toPersistentList()
                        uiState.meaningField.selectedValues = persistentListOf(meaning)
                    }
                    selectedLanguage =
                        suggestionLanguageUseCase(language.languageCode).firstOrNull {
                            it.languageCode == language.languageCode
                        }
                    uiState.typeField.suggestions = suggestionTypeUseCase(
                        typeName = "",
                        languageCode = language.languageCode,
                        ignorableChars = setOf()
                    ).toPersistentList()
                }
            } ?: onImmediate {
                uiState.apply {
                    this.id = null
                    currentStep = WordEditorSteps.LANGUAGE
                    steps = WordEditorSteps.validationMap
                    languageField.apply {
                        this.id = null
                        value = ""
                        error = null
                        languages = null
                        selectedValues = null
                        suggestions = null
                    }
                    nameField.apply {
                        this.id = null
                        value = ""
                        error = null
                        suggestions = null
                    }
                    uiState.descriptionField.value = ""
                    typeField.apply {
                        this.id = null
                        value = ""
                        error = null
                        selectedValues = null
                        suggestions = null
                    }
                    categoryField.apply {
                        this.id = null
                        value = ""
                        error = null
                        selectedValues = null
                        suggestions = null
                    }
                    meaningField.apply {
                        this.id = null
                        value = ""
                        error = null
                        selectedValues = null
                        suggestions = null
                    }
                }
            }
            updateLanguage("")
            updateCategory("")
            updateMeaning("")
        }
    }


    // language
    private fun updateLanguage(
        input: String,
    ) {
        uiState.languageField.value = input
        viewModelScope.launch(Dispatchers.IO) {
            suggestionLanguageUseCase(input).let { suggestions ->
                onImmediate {
                    suggestions.toPersistentList().let {
                        uiState.languageField.apply {
                            languages = it
                            this.suggestions = it
                        }
                    }
                }
            }
        }
    }

    private fun selectLanguage(
        language: EditLanguageItem,
    ) {
        viewModelScope.launch {
            onImmediate {
                uiState.languageField.apply {
                    error = null
                    selectedValues = persistentListOf(language)
                }
                uiState.steps = uiState.steps.put(WordEditorSteps.LANGUAGE, true)
            }
            updateLanguage("")
            if (language != selectedLanguage) {
                selectedLanguage = language
                uiState.steps = uiState.steps.put(WordEditorSteps.NAME_DESCRIPTION, false)
                updateName(name = uiState.nameField.value)
                removeType()
            }
        }
    }

    private fun removeLanguage() {
        viewModelScope.launch {
            onImmediate {
                uiState.languageField.apply {
                    error = JaArDynamicString.StrRes(R.string.language_invalid_body)
                    selectedValues = null
                }
                uiState.steps = uiState.steps.put(WordEditorSteps.LANGUAGE, false)
            }
            updateLanguage("")
            selectedLanguage = null
        }
    }

    private fun updateType(
        input: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            onImmediate {
                uiState.typeField.value = input
            }
            selectedLanguage?.let {
                suggestionTypeUseCase(
                    typeName = input,
                    languageCode = it.languageCode,
                    ignorableChars = it.ignorables
                ).let { suggestions ->
                    onImmediate {
                        uiState.typeField.suggestions = suggestions.toPersistentList()
                    }
                }
            }
        }
    }

    private fun selectType(
        type: ModelItem,
    ) {
        viewModelScope.launch {
            onImmediate {
                uiState.typeField.apply {
                    error = null
                    selectedValues = persistentListOf(type)
                }
                uiState.steps = uiState.steps.put(WordEditorSteps.TYPE, true)
            }
            updateType("")
        }
    }

    private fun removeType() {
        viewModelScope.launch {
            onImmediate {
                uiState.typeField.apply {
                    error = JaArDynamicString.StrRes(R.string.type_invalid_body)
                    selectedValues = null
                }
                uiState.steps = uiState.steps.put(WordEditorSteps.TYPE, false)
            }
            updateType("")
        }
    }

    // word name
    private fun updateName(
        name: String,
    ) {
        uiState.nameField.apply {
            value = name
            error = selectedLanguage?.validChars?.let {
                name.checkIfValid(
                    validator = RegexValidator.OfValidChars(
                        validChars = it,
                        ignoreCase = true,
                        minLength = 1,
                    )
                )
            }?.run {
                if (first.matched) {
                    null// not suggestion but valid word
                } else if (name.isBlank() && uiState.descriptionField.value.isNotBlank()) {
                    null// blank but description is valid
                } else {
                    first.inputDynamicString(
                        input = name,
                        invalidChar = name.getOrNull(second ?: -1)?.toString()
                    )

                }
            }
            uiState.steps = uiState.steps.put(WordEditorSteps.NAME_DESCRIPTION, error == null)
        }
        viewModelScope.launch(Dispatchers.IO) {
            uiState.nameField.suggestions = selectedLanguage?.let {
                this@WordEditorViewModel.suggestionWordUseCase(
                    wordName = name,
                    ignorableChars = it.ignorables,
                    interchangables = it.interchangeables
                ).toPersistentList()
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
                        key = WordEditorSteps.NAME_DESCRIPTION,
                        value = nameField.error == null && (nameField.value.isNotBlank() || descriptionField.value.isNotBlank())
                    )
                }
            }
        }
    }

    private fun updateCategory(
        input: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            onImmediate {
                uiState.categoryField.value = input
            }
            suggestionCategoryUseCase(
                categoryName = input,
                uiState.categoryField.selectedValues?.map { it.id }?.toSet() ?: setOf()
            ).let { suggestions ->
                onImmediate {
                    uiState.categoryField.suggestions = suggestions.toPersistentList()
                }
            }
        }
    }

    private fun selectCategory(
        category: ModelItem,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            onImmediate {
                uiState.categoryField.apply {
                    value = ""
                    selectedValues = selectedValues?.add(category) ?: persistentListOf(category)
                }
            }
            suggestionCategoryUseCase(
                categoryName = "",
                existedIds = uiState.categoryField.selectedValues?.map { it.id }?.toSet() ?: setOf()
            ).let { suggestions ->
                onImmediate {
                    uiState.categoryField.suggestions = suggestions.toPersistentList()
                }
            }
        }
    }

    private fun removeCategory(
        category: ModelItem,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            onImmediate {
                uiState.categoryField.apply {
                    selectedValues = selectedValues?.remove(category)
                }
            }
            suggestionCategoryUseCase(
                categoryName = uiState.categoryField.value,
                existedIds = uiState.categoryField.selectedValues?.map { it.id }?.toSet() ?: setOf()
            ).let { suggestions ->
                onImmediate {
                    uiState.categoryField.suggestions = suggestions.toPersistentList()
                }
            }
        }
    }

    private fun updateMeaning(
        input: String,
    ) {
        viewModelScope.launch {
            onImmediate {
                uiState.meaningField.value = input
            }
            suggestionMeaningUseCase(input).let {
                onImmediate {
                    uiState.meaningField.suggestions = it.toPersistentList()
                }
            }
        }
    }

    private fun selectMeaning(
        meaning: ModelItem,
    ) {
        uiState.meaningField.apply {
            value = ""
            selectedValues = persistentListOf(meaning)
        }
        viewModelScope.launch {
            uiState.meaningField.suggestions =
                suggestionMeaningUseCase(meaning.value).toPersistentList()
        }
    }

    private fun removeMeaning() {
        viewModelScope.launch {
            onImmediate {
                uiState.meaningField.apply {
                    value = ""
                    selectedValues = null
                }
            }
            uiState.meaningField.suggestions = suggestionMeaningUseCase("").toPersistentList()
        }
    }

    private fun confirm(navigateUp: () -> Unit) {
        viewModelScope.launch {
            uiState.apply {
                if (steps.all { it.value }) {
                    val word = SavableWordItem(
                        id = id ?: INVALID_ID,
                        name = nameField.value,
                        description = descriptionField.value,
                        languageCode = selectedLanguage!!.languageCode,
                        categories = categoryField.selectedValues?.map { it.id }?.toSet()
                            ?: setOf(),
                        meaningId = meaningField.selectedValues?.firstOrNull()?.id,
                        typeId = typeField.selectedValues?.first()?.id!!
                    )
                    if (id == null) {
                        saveNewWordUseCase(word)
                        initWord(null)
                    } else {
                        saveUpdateWordUseCase(word)
                        navigateUp()
                    }
                }
            }
        }
    }


    fun getUiActions(navigateUp: () -> Unit) = object : WordEditorUiActions {
        override val languageFieldActions = object : EditorFieldUiAction {
            override fun onValueChange(newValue: String) = updateLanguage(
                input = newValue,
            )

            override fun onSelectSuggestion(suggestion: ModelItem) {
                uiState.languageField.languages?.firstOrNull { it.languageCode == suggestion.languageCode }
                    ?.let {
                        selectLanguage(it)
                    }
            }

            override fun onRemoveFilterChip(model: ModelItem) { /* no selected values */
                removeLanguage()
            }
        }
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
        override val typeFieldActions = object : EditorFieldUiAction {
            override fun onValueChange(newValue: String) = updateType(
                input = newValue,
            )

            override fun onSelectSuggestion(suggestion: ModelItem) = selectType((suggestion))

            override fun onRemoveFilterChip(model: ModelItem) = removeType()
        }
        override val categoryFieldActions = object : EditorFieldUiAction {
            override fun onValueChange(newValue: String) = updateCategory(newValue)

            override fun onSelectSuggestion(suggestion: ModelItem) = selectCategory(suggestion)

            override fun onRemoveFilterChip(model: ModelItem) = removeCategory(model)
        }
        override val meaningFieldActions = object : EditorFieldUiAction {
            override fun onValueChange(newValue: String) = updateMeaning(newValue)

            override fun onSelectSuggestion(suggestion: ModelItem) = selectMeaning(suggestion)

            override fun onRemoveFilterChip(model: ModelItem) = removeMeaning()
        }

        override fun onPreviousStep() {
            modelImmediate {
                uiState.apply {
                    currentStep = WordEditorSteps.entries[
                        currentStep.ordinal.dec().coerceAtLeast(0)
                    ]
                }
            }
        }

        override fun onNextStep() {
            modelImmediate {
                uiState.apply {
                    currentStep = WordEditorSteps.entries[
                        currentStep.ordinal.inc()
                            .coerceAtMost(WordEditorSteps.entries.count().dec())
                    ]
                }
            }
        }

        override fun onCancel() = navigateUp()

        override fun onConfirm() = confirm(navigateUp)
    }
}


@JvmName("LanguageItemContains")
private fun LanguageItem.contains(input: String): Boolean {
    return input.lowercase().trim().let {
        it == languageCode.lowercase()
                || it == selfDisplayName.lowercase()
                || it == localDisplayName.lowercase()
    }
}

@JvmName("EditLanguageItemContains")
private fun EditLanguageItem.contains(input: String): Boolean {
    return input.lowercase().trim().let {
        it == languageCode.lowercase()
                || it == selfDisplayName.lowercase()
                || it == localDisplayName.lowercase()
    }
}