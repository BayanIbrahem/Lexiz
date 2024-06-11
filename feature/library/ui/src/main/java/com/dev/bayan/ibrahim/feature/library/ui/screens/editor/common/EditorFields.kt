package com.dev.bayan.ibrahim.feature.library.ui.screens.editor.common

import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.feature.ui.R
import com.dev.bayan.ibrahim.core.ui.R as UiRes

internal enum class EditorFields(
    val title: JaArDynamicString?,
    val label: JaArDynamicString,
    val subtitle: JaArDynamicString,
) {
    WORD_NAME(
        title = JaArDynamicString.PluralRes(UiRes.plurals.word, 1),
        label = JaArDynamicString.StrRes(R.string.word_input_name_label),
        subtitle = JaArDynamicString.StrRes(R.string.word_input_name_subtitle),
    ),
    WORD_DESCRIPTION(
        title = null,
        label = JaArDynamicString.StrRes(R.string.word_input_description_label),
        subtitle = JaArDynamicString.StrRes(R.string.word_input_description_subtitle),
    ),
    CATEGORY_NAME(
        title = JaArDynamicString.PluralRes(UiRes.plurals.category, 1),
        label = JaArDynamicString.StrRes(R.string.category_input_name_label),
        subtitle = JaArDynamicString.StrRes(R.string.category_input_name_subtitle),
    ),
    CATEGORY_DESCRIPTION(
        title = null,
        label = JaArDynamicString.StrRes(R.string.category_input_description_label),
        subtitle = JaArDynamicString.StrRes(R.string.category_input_description_subtitle),
    ),
    LANGUAGE(
        title = JaArDynamicString.PluralRes(UiRes.plurals.language, 1),
        label = JaArDynamicString.PluralRes(UiRes.plurals.language, 1),
        subtitle = JaArDynamicString.PluralRes(UiRes.plurals.language, 1),
    ),
    TYPE(
        title = JaArDynamicString.PluralRes(UiRes.plurals.type, 1),
        label = JaArDynamicString.StrRes(R.string.word_input_type_label),
        subtitle = JaArDynamicString.StrRes(R.string.word_input_type_subtitle),
    ),
    WORD_CATEGORY(
        title = JaArDynamicString.PluralRes(UiRes.plurals.category, 10),
        label = JaArDynamicString.StrRes(R.string.word_input_category_label),
        subtitle = JaArDynamicString.StrRes(R.string.word_input_category_subtitle),
    ),
    WORD_MEANING(
        title = JaArDynamicString.PluralRes(UiRes.plurals.meaning, 1),
        label = JaArDynamicString.StrRes(R.string.word_input_meaning_label),
        subtitle = JaArDynamicString.StrRes(R.string.word_input_meaning_subtitle),
    ),
    CATEGORY_WORD(
        title = JaArDynamicString.PluralRes(UiRes.plurals.word, 10),
        label = JaArDynamicString.StrRes(R.string.category_input_words_label),
        subtitle = JaArDynamicString.StrRes(R.string.category_input_words_subtitle),
    ),
}