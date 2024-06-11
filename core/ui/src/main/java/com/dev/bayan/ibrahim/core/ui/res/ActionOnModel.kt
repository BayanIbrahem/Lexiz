package com.dev.bayan.ibrahim.core.ui.res

import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import com.dev.bayan.ibrahim.core.common.format.StringCapitalizer
import com.dev.bayan.ibrahim.core.common.format.capitalize

/**
 * returns a string using an action and a model
 * pass a model that has a parameter to show the count
 * it formats it like this
 * $action $model
 * like:
 * "delete one language"
 */
@Composable
fun actionOnModelResource(
    @StringRes action: Int,
    @PluralsRes model: Int,
    count: Int,
    capitalizer: StringCapitalizer? = null,
): String = capitalizer.capitalize(
    string = "${
        stringResource(id = action)
    } ${
        pluralStringResource(
            id = model,
            count = count,
            count
        )
    }"
)