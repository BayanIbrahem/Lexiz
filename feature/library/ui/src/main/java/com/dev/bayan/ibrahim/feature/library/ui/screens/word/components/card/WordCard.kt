package com.dev.bayan.ibrahim.feature.library.ui.screens.word.components.card


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.common.model.CategoryItem
import com.dev.bayan.ibrahim.core.common.model.INVALID_ID
import com.dev.bayan.ibrahim.core.common.model.LanguageItem
import com.dev.bayan.ibrahim.core.common.model.TypeItem
import com.dev.bayan.ibrahim.core.ui.components.container.JaArContainer
import com.dev.bayan.ibrahim.core.ui.components.container.JaArContainerType
import com.dev.bayan.ibrahim.core.ui.res.thereNoModel
import com.dev.bayan.ibrahim.feature.library.domain.model.words.Word
import kotlinx.datetime.Clock
import com.dev.bayan.ibrahim.core.ui.R as UiRes

@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
internal fun WordCard(
    modifier: Modifier = Modifier,
    word: Word,
    isOtherSelectedAndThisNot: Boolean,
    focusedCard: Long?,
    onClick: (() -> Unit)?,
    onLongClick: (() -> Unit)?,
    onDoubleClick: (() -> Unit)?,
) {
    JaArContainer(
        modifier = modifier,
        type = JaArContainerType.SECONDARY,
        enabled = !isOtherSelectedAndThisNot,
        onClick = onClick,
        onLongClick = onLongClick,
        onDoubleClick = onDoubleClick,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer { this.alpha = alpha },
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = word.name ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        modifier = modifier.alpha(0.75f),
                        text = word.language.selfDisplayName,
                        style = MaterialTheme.typography.labelLarge,
                    )
                    Text(
                        modifier = modifier.alpha(0.75f),
                        text = word.type.name,
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
            }
            // even if the description is short then it will take the same size of long one
            word.description?.let {
//            DividerWithSubtitle(
//               subtitle = it,
//               subtitleAlignment = Alignment.TopStart,
//               color = MaterialTheme.colorScheme.onSecondaryContainer,
//               textColor = MaterialTheme.colorScheme.onSecondaryContainer,
//               horizontalPadding = 0.dp,
//               textStyle = MaterialTheme.typography.bodySmall,
//            )
                val descriptionModifier by remember(focusedCard) {
                    derivedStateOf {
                        if (word.wordId == focusedCard) {
                            Modifier.basicMarquee()
                        } else {
                            Modifier
                        }
                    }
                }
                Text(
                    modifier = descriptionModifier,
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            if (word.categories.isNotEmpty()) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(0.dp),
                ) {
                    word.categories.forEach {
                        Text(
                            text = it.name,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1,
                            overflow = TextOverflow.Clip,
                        )
                    }
                }
            } else {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = thereNoModel(item = UiRes.plurals.category, there = false),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun WordCardPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center,
    ) {
        val word1 = Word(
            groupId = INVALID_ID,
            groupName = null,
            wordId = INVALID_ID,
            name = "word name",
            description = "very very very loooong description for an existed word",
            language = LanguageItem("en", "language", "language"),
            type = TypeItem(INVALID_ID, "en", "type", ""),
            categories = listOf(
                CategoryItem(INVALID_ID, "category 1", null),
                CategoryItem(INVALID_ID, "category 2", null),
                CategoryItem(INVALID_ID, "category 3", null),
                CategoryItem(INVALID_ID, "category 4", null),
            ),
            meaningId = INVALID_ID,
            addDate = Clock.System.now(),
            lastEditDate = Clock.System.now(),
        )
        val word2 = word1.copy(
            name = "very very long word name",
            categories = listOf()
        )
        Column(
            modifier = Modifier.widthIn(max = 200.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            WordCard(
                word = word1,
                isOtherSelectedAndThisNot = false,
                focusedCard = 1,
                onClick = {},
                onLongClick = {},
                onDoubleClick = {}
            )
            WordCard(
                word = word2,
                focusedCard = INVALID_ID,
                isOtherSelectedAndThisNot = false,
                onClick = {},
                onLongClick = {},
                onDoubleClick = {}
            )
        }
    }
}
