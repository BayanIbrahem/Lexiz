package com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.core.filter_item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.FilterType
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.util.BuilderItemSaveStatus
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.utils.color

@Composable
internal fun FilterValueListItem(
    modifier: Modifier = Modifier,
    value: FilterType,
    onEdit: () -> Unit,
    onRemove: () -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                if (value.saveStatus != BuilderItemSaveStatus.SAVED) {
                    Text(
                        text = stringResource(id = value.saveStatus.resName),
                        style = MaterialTheme.typography.labelSmall,
                        color = value.saveStatus.color
                    )
                }
                Text(
                    text = value.name.value,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            IconButton(onClick = onEdit) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = null
                )
            }
            IconButton(onClick = onRemove) {
                Icon(
                    imageVector = Icons.Filled.Remove,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        HorizontalDivider()
    }
}
