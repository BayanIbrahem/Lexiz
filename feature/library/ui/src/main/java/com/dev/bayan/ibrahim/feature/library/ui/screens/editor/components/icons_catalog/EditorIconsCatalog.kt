package com.dev.bayan.ibrahim.feature.library.ui.screens.editor.components.icons_catalog


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.ui.components.labled_icon.JaArLabeledIcon
import com.dev.bayan.ibrahim.feature.ui.R

@Composable
internal fun EditorIconsCatalog(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = stringResource(id = R.string.icons_meanings),
            style = MaterialTheme.typography.labelMedium,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            IconsCatalog.entries.forEach {
                JaArLabeledIcon(data = it)
            }
        }
    }
}
