package com.dev.bayan.ibrahim.feature.quiz_builder.ui.utils.fab_optoins

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import com.dev.bayan.ibrahim.core.ui.components.container.JaArContainerFabOption
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicVector
import com.dev.bayan.ibrahim.core.ui.R as UiRes

internal enum class QuizFabOptions(
    override val icon: JaArDynamicVector,
    override val tooltip: JaArDynamicString
) : JaArContainerFabOption {
    START(
        icon = JaArDynamicVector.Vector(
            value = Icons.AutoMirrored.Filled.ArrowForward,  // choose better icon
        ),
        tooltip = JaArDynamicString.StrRes(
            UiRes.string.start_now
        )
    );
}