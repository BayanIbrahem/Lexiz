package com.dev.bayan.ibrahim.feature.library.ui.screens.word.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import com.dev.bayan.ibrahim.core.ui.components.container.JaArContainerFabOption
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicVector
import com.dev.bayan.ibrahim.core.ui.res.actionOnModelResource
import com.dev.bayan.ibrahim.core.ui.R as UiRes

enum class WordsContainerFabOptions(
    override val tooltip: JaArDynamicString,
    override val icon: JaArDynamicVector,
) : JaArContainerFabOption {
    ADD(
        tooltip = JaArDynamicString.StrRes(UiRes.string.add),
        icon = JaArDynamicVector.Vector(Icons.Filled.Add),
    ),
    WORD(
        tooltip = JaArDynamicString.Builder {
            actionOnModelResource(
                action = UiRes.string.add,
                model = UiRes.plurals.word,
                count = 1
            )
        },
        icon = JaArDynamicVector.Res(UiRes.drawable.ic_word),
    ),
    CATEGORY(
        tooltip = JaArDynamicString.Builder {
            actionOnModelResource(
                action = UiRes.string.add,
                model = UiRes.plurals.category,
                count = 1
            )
        },
        icon = JaArDynamicVector.Res(UiRes.drawable.ic_category),
    ),
}
