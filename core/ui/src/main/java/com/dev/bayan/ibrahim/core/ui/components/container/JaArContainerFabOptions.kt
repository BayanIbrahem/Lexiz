package com.dev.bayan.ibrahim.core.ui.components.container

import androidx.annotation.Size
import androidx.compose.ui.graphics.Color
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicVector
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import kotlin.enums.EnumEntries


interface JaArContainerFabOption {
    val icon: JaArDynamicVector
    val tooltip: JaArDynamicString
}

enum class DummyJaArContainerFabOptions : JaArContainerFabOption