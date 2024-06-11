package com.dev.bayan.ibrahim.core.ui.components.action.selectable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicVector

@Composable
internal fun JaArSelectableHeader(
    modifier: Modifier = Modifier,
    fillMaxWidth: Boolean = true,
    title: JaArDynamicString? = null,
    leadingIcon: JaArDynamicVector? = null,
    label: JaArDynamicString,
    trailingIcon: JaArDynamicVector? = null,
    supportingText: JaArDynamicString? = null,
    onClick: (() -> Unit)?,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        title?.value?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
            )
        }
        Row(
            modifier = Modifier
                .height(48.dp)
                .clip(RoundedCornerShape(24.dp))
                .clickable(enabled = onClick != null) { onClick?.let { it() } }
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            leadingIcon?.let {
                Icon(imageVector = it.vector, contentDescription = null)
            }
            Text(
                modifier = if (fillMaxWidth) Modifier.weight(1f) else Modifier,
                text = label.value,
                style = MaterialTheme.typography.labelLarge,
                maxLines = 1,
            )
            trailingIcon?.let {
                Icon(imageVector = it.vector, contentDescription = null)
            }
        }
        supportingText?.value?.let {
            Text(
                modifier = Modifier.alpha(0.5f),
                text = it,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
            )
        }
    }
}