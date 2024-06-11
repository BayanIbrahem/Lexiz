package com.dev.bayan.ibrahim.core.ui.res

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import com.dev.bayan.ibrahim.core.common.format.StringCapitalizer
import com.dev.bayan.ibrahim.core.common.format.capitalize


/**
 * returns the model name followed by a number like
 * Word 1 or Quiz 2
 */
@Composable
fun modelDefaultName(
    model: Int,
    number: Int,
    capitalizer: StringCapitalizer = StringCapitalizer.FIRST_CHAR
): String = "${pluralStringResource(id = model, count = 1)} $number".capitalize(
    capitalizer
)