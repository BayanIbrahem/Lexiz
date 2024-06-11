package com.dev.bayan.ibrahim.core.ui.res

import androidx.annotation.PluralsRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import com.dev.bayan.ibrahim.core.common.format.StringCapitalizer
import com.dev.bayan.ibrahim.core.common.format.capitalize
import com.dev.bayan.ibrahim.core.ui.R

/**
 * returns a string of "there is $item in $group" pass ids that have a number to show the number of
 * items or number of groups:
 * - item with number group without number: "there is 4 words in language"
 * - item without number group with number: "there is words in 4 languages"
 * and you can pass number for both or none
 * @param item res of the item (content)
 * @param itemCount count of the item
 * @param group res of the group (container)
 * @param groupCount count of the groups
 * @param capitalizer optional capitalizer see [StringCapitalizer]
 */
@Composable
fun thereModel(
    @PluralsRes item: Int, itemCount: Int,
    capitalizer: StringCapitalizer? = null,
): String = capitalizer.capitalize(
    string = stringResource(
        id = R.string.there_x,
        pluralStringResource(id = item, count = itemCount, itemCount),
    )
)

@Composable
fun thereModelInModelResource(
    @PluralsRes item: Int, itemCount: Int,
    @PluralsRes group: Int, groupCount: Int,
    capitalizer: StringCapitalizer? = null,
): String = capitalizer.capitalize(
    string = stringResource(
        id = R.string.there_x_in_y,
        pluralStringResource(id = item, count = itemCount, itemCount),
        pluralStringResource(id = group, count = groupCount, groupCount),
    )
)
