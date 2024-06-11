package com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.new_group.page.template_filter

import com.dev.bayan.ibrahim.core.common.JaArUiEvent

internal sealed interface TemplateFilterEvent {
    data class OnClickFilter(val id: Long) : TemplateFilterEvent
    data object OnConfirm : TemplateFilterEvent
    data object OnCancel : TemplateFilterEvent
    data object OnRemove : TemplateFilterEvent
}
