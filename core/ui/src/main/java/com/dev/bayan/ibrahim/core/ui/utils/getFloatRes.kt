package com.dev.bayan.ibrahim.core.ui.utils

import androidx.annotation.DimenRes
import androidx.annotation.IntegerRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource

@Composable
fun getFloatRes(
    @IntegerRes id: Int,
    factor: Float = 100f,
): Float {
    return integerResource(id = id) / factor
}