package com.dev.bayan.ibrahim.feature.library.data.repository.db

import com.dev.bayan.ibrahim.feature.library.data.repository.db.components.editor.init.*
import com.dev.bayan.ibrahim.feature.library.data.repository.db.components.editor.save.save_new.*
import com.dev.bayan.ibrahim.feature.library.data.repository.db.components.editor.save.update.*
import com.dev.bayan.ibrahim.feature.library.data.repository.db.components.editor.suggestion.*


/**
 * This is a component of [LibraryDbRepo]. This sub-repository provides data for the editor screen.
 * 1) Init info when editing using [LibraryDbEditorInitWordRepo],
 * [LibraryDbEditorInitCategoryRepo]
 * 2) suggestions info using [LibraryDbEditorSuggestionRepo]
 * 3) save new or existed words using [LibraryDbEditorSaveNewWordRepo]
 * 4) save new or existed categories using [LibraryDbEditorSaveNewCategoryRepo]
 * **/
interface LibraryDbEditorRepo :
    LibraryDbEditorInitWordRepo,
    LibraryDbEditorInitCategoryRepo,

    LibraryDbEditorSuggestionRepo,

    LibraryDbEditorSaveNewWordRepo,
    LibraryDbEditorSaveNewCategoryRepo,

    LibraryDbEditorSaveUpdateWordRepo,
    LibraryDbEditorSaveUpdateCategoryRepo {


}