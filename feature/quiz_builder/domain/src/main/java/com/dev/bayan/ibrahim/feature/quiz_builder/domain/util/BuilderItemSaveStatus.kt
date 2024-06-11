package com.dev.bayan.ibrahim.feature.quiz_builder.domain.util

import androidx.annotation.StringRes;

import com.dev.bayan.ibrahim.feature.quiz_builder.domain.R;
import com.dev.bayan.ibrahim.core.ui.R as UiRes

enum class BuilderItemSaveStatus(
    @StringRes val resName: Int
) {
    SAVED(R.string.saved),
    EDITED(R.string.edited),
    NEW(UiRes.string.new_);
}
