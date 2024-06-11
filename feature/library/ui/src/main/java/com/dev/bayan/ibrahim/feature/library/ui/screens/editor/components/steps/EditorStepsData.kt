package com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.steps

import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicVector
import com.dev.bayan.ibrahim.core.ui.components.labled_icon.LabeledIconData
import com.dev.bayan.ibrahim.feature.library.ui.screens.editor.common.EditorFields
import com.dev.bayan.ibrahim.feature.ui.R
import kotlinx.collections.immutable.toPersistentMap
import com.dev.bayan.ibrahim.core.ui.R as UiRes

internal interface EditorStepsData : LabeledIconData {
    val fields: List<EditorFields>
}

internal enum class WordEditorSteps(
    override val icon: JaArDynamicVector,
    override val label: JaArDynamicString,
    override val fields: List<EditorFields>,
) : EditorStepsData {
    LANGUAGE(
        icon = JaArDynamicVector.Res(UiRes.drawable.ic_language),
        label = JaArDynamicString.PluralRes(UiRes.plurals.language, 1),
        listOf(EditorFields.LANGUAGE),
    ),

    NAME_DESCRIPTION(
        icon = JaArDynamicVector.Res(UiRes.drawable.ic_name_descripition),
        label = JaArDynamicString.StrRes(R.string.name_description),
        listOf(
            EditorFields.WORD_NAME,
            EditorFields.WORD_DESCRIPTION,
        )
    ),

    TYPE(
        icon = JaArDynamicVector.Res(UiRes.drawable.ic_type),
        label = JaArDynamicString.PluralRes(UiRes.plurals.type, 1),
        listOf(
            EditorFields.TYPE,
        )
    ),

    CATEGORY(
        icon = JaArDynamicVector.Res(UiRes.drawable.ic_category),
        label = JaArDynamicString.PluralRes(UiRes.plurals.category, 1),
        listOf(
            EditorFields.WORD_CATEGORY,
        )
    ),

    MEANING(
        icon = JaArDynamicVector.Res(UiRes.drawable.ic_meaning),
        label = JaArDynamicString.PluralRes(UiRes.plurals.meaning, 1),
        listOf(
            EditorFields.WORD_MEANING,
        )
    );

    companion object {
        val validationMap = entries.associateWith {
            when (it) {
                LANGUAGE -> false
                NAME_DESCRIPTION -> false
                TYPE -> false
                CATEGORY -> true
                MEANING -> true
            }
        }.toPersistentMap()
    }
}

internal enum class CategoryEditorSteps(
    override val icon: JaArDynamicVector,
    override val label: JaArDynamicString,
    override val fields: List<EditorFields>,
) : EditorStepsData {
    NAME_DESCRIPTION(
        icon = JaArDynamicVector.Res(UiRes.drawable.ic_name_descripition),
        label = JaArDynamicString.StrRes(R.string.category_input_description_subtitle),
        listOf(
            EditorFields.CATEGORY_NAME,
            EditorFields.CATEGORY_DESCRIPTION,
        ),
    ),
    WORD(
        icon = JaArDynamicVector.Res(UiRes.drawable.ic_word),
        label = JaArDynamicString.StrRes(R.string.category_input_words_subtitle),
        listOf(
            EditorFields.CATEGORY_WORD,
        )
    );

    companion object {
        val validationMap = entries.associateWith {
            when (it) {
                NAME_DESCRIPTION -> false
                WORD -> true
            }
        }.toPersistentMap()
    }
}