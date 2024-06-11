package com.dev.bayan.ibrahim.core.ui.components.dynamic

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource

@Stable
sealed interface JaArDynamicVector {
    val contentDescription: Int?

    data class Vector(
        val value: ImageVector,
        override val contentDescription: Int? = null
    ) : JaArDynamicVector

    data class Res(
        @DrawableRes val resId: Int,
        override val contentDescription: Int? = null
    ) : JaArDynamicVector

    val vector: ImageVector
        @Composable
        get() = when (this) {
            is Res -> ImageVector.vectorResource(id = resId)
            is Vector -> this.value
        }
}

fun ImageVector.asDynamicVector(
    contentDescription: Int? = null
): JaArDynamicVector = JaArDynamicVector.Vector(
    value = this,
    contentDescription = contentDescription
)
