package com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.quiz_setup.core.language_selector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.CompareArrows
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.common.model.LanguageItem
import com.dev.bayan.ibrahim.core.ui.components.container.JaArContainerType
import com.dev.bayan.ibrahim.core.ui.components.language_selection.LanguageSelectionFilterChip
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.R
import kotlinx.collections.immutable.PersistentList
import com.dev.bayan.ibrahim.core.ui.R as UiRes

@Composable
internal fun QuizLanguageSelector(
    modifier: Modifier = Modifier,
    languages: PersistentList<LanguageItem>,
    firstLanguage: LanguageItem?,
    secondLanguage: LanguageItem?,
    onFirstLanguageSelect: (LanguageItem?) -> Unit,
    onSecondLanguageSelect: (LanguageItem?) -> Unit,
    onInvert: () -> Unit,
) {
    Column {
        Text(
            text = pluralStringResource(id = UiRes.plurals.the_language, count = 10),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            modifier = Modifier.alpha(0.75f),
            text = stringResource(id = R.string.select_language_body),
            style = MaterialTheme.typography.labelMedium
        )
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            val firstLangList by remember(languages, secondLanguage) {
                derivedStateOf { languages.removeAll { it.languageCode == secondLanguage?.languageCode } }
            }
            val secondLangList by remember(languages, firstLanguage) {
                derivedStateOf { languages.removeAll { it.languageCode == firstLanguage?.languageCode } }
            }
            LanguageSelectionFilterChip(
                languages = firstLangList,
                onSelectItem = onFirstLanguageSelect,
                selectedLanguage = firstLanguage,
            )
            IconButton(
                onClick = onInvert,
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = JaArContainerType.SECONDARY.containerColor,
                    contentColor = JaArContainerType.SECONDARY.onContainerColor,
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.CompareArrows,
                    contentDescription = null
                )
            }
            LanguageSelectionFilterChip(
                languages = secondLangList,
                onSelectItem = onSecondLanguageSelect,
                selectedLanguage = secondLanguage,
            )
        }
    }
}