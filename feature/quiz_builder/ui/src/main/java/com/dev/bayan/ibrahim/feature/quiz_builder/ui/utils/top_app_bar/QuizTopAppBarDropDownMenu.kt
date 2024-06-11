package com.dev.bayan.ibrahim.feature.quiz_builder.ui.utils.top_app_bar

import com.dev.bayan.ibrahim.core.ui.components.action.selectable.JaArSelectable
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.R
import com.dev.bayan.ibrahim.core.ui.R as UiRes

enum class QuizTopAppBarDropDownMenu(
    override val label: JaArDynamicString,
    override val supportingText: JaArDynamicString,
    override val disabledSupportingText: JaArDynamicString,
    val validOnNew: Boolean,
) : JaArSelectable {

    SAVE_RULE(
        JaArDynamicString.StrRes(R.string.save_rule),
        JaArDynamicString.StrRes(R.string.save_rule_description),
        JaArDynamicString.StrRes(R.string.save_rule_description),
        true,
    ),
    SAVE_WORD(
        JaArDynamicString.StrRes(R.string.save_words),
        JaArDynamicString.StrRes(R.string.save_words_description),
        JaArDynamicString.StrRes(R.string.save_words_description),
        true,
    ),
    SAVE_COPY_RULE(
        JaArDynamicString.StrRes(R.string.save_copy_rule),
        JaArDynamicString.StrRes(R.string.save_copy_rule_description),
        JaArDynamicString.StrRes(R.string.save_copy_rule_disabled_description),
        false,
    ),
    SAVE_COPY_WORD(
        JaArDynamicString.StrRes(R.string.save_copy_words),
        JaArDynamicString.StrRes(R.string.save_copy_words_description),
        JaArDynamicString.StrRes(R.string.save_words_disabled_description),
        false,
    ),
    RESET(
        JaArDynamicString.StrRes(UiRes.string.reset_changes),
        JaArDynamicString.StrRes(R.string.reset_changes_description),
        JaArDynamicString.StrRes(R.string.reset_changes_disapled_description),
        false,
    ),
    CONSUME(
        label = JaArDynamicString.StrRes(UiRes.string.consume),
        supportingText = JaArDynamicString.StrRes(R.string.consume_description),
        disabledSupportingText = JaArDynamicString.StrRes(R.string.consume_disapled_description),
        validOnNew = false,
    );

    override val id: Long = this.ordinal.toLong()
}


