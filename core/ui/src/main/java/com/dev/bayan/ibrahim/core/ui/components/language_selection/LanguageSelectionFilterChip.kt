package com.dev.bayan.ibrahim.core.ui.components.language_selection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.common.model.LanguageItem
import com.dev.bayan.ibrahim.core.ui.R
import com.dev.bayan.ibrahim.core.ui.components.container.JaArContainerType
import com.dev.bayan.ibrahim.core.ui.components.divider.DividerWithSubtitle
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.core.ui.theme.JaArTheme
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toPersistentList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSelectionFilterChip(
    modifier: Modifier = Modifier,
    languages: PersistentList<LanguageItem>,
    selectedLanguage: LanguageItem? = null,
    unAllowedLanguages: PersistentSet<LanguageItem> = persistentSetOf(),
    onSelectItem: (LanguageItem?) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        FilterChip(
            modifier = Modifier.width(75.dp),
            selected = false,
            onClick = { expanded = true },
            label = {
                Text(
                    text = selectedLanguage?.languageCode
                        ?: "-",
                )
            },
            trailingIcon = { Icon(Icons.Filled.ArrowDropDown, null) },
            colors = FilterChipDefaults.filterChipColors(
                containerColor = JaArContainerType.SECONDARY.containerColor,
                labelColor = JaArContainerType.SECONDARY.onContainerColor,
                iconColor = JaArContainerType.SECONDARY.onContainerColor,
            )
        )
        DropdownMenu(
            modifier = Modifier.heightIn(max = 52.dp * 5),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            DropdownMenuItem(
                leadingIcon = null,
                trailingIcon = null,
                text = {
                    Text(
                        text = stringResource(id = R.string.clear_selection),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                onClick = {
                    onSelectItem(null)
                    expanded = false
                },
            )
            languages.filterNot { it in unAllowedLanguages }.forEach { lang ->
                DropdownMenuItem(
                    leadingIcon = {
                        Text(
                            text = lang.languageCode.uppercase(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    trailingIcon = if (lang.languageCode == selectedLanguage?.languageCode) {
                        { Icon(Icons.Filled.Check, null) }
                    } else {
                        null
                    },
                    text = {
                        Column {
                            Text(
                                text = lang.selfDisplayName,
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                modifier = Modifier.alpha(0.75f),
                                text = lang.localDisplayName,
                                style = MaterialTheme.typography.labelSmall,
                            )
                        }
                    },
                    onClick = {
                        onSelectItem(lang)
                        expanded = false
                    },
                )
            }
        }

    }
}

@Preview(showBackground = false)
@Composable
private fun LanguageSelectionFilterChipPreview() {
    var selectedItem by remember { mutableStateOf<LanguageItem?>(null) }
//   JaArTheme {
    Surface(modifier = Modifier) {
        LanguageSelectionFilterChip(
            languages = List(10) {
                LanguageItem(
                    languageCode = "en",
                    selfDisplayName = "English",
                    localDisplayName = "english",
                )
            }.toPersistentList(),
            onSelectItem = { selectedItem = it },
        )
    }
//   }
}