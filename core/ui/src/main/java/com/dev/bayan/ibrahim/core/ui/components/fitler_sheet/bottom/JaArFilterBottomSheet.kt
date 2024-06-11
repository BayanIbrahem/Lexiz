package com.dev.bayan.ibrahim.core.ui.components.fitler_sheet.bottom


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JaArFilterBottomSheet(
    modifier: Modifier = Modifier,
    state: SheetState,
    contentHeight: Dp,
    title: JaArDynamicString?,
    onDismissRequest: () -> Unit,
    primaryAction: @Composable() (RowScope.() -> Unit)?,
    secondaryAction: @Composable() (RowScope.() -> Unit)?,
    tertiaryAction: @Composable() (() -> Unit)?,
    content: @Composable () -> Unit,
) {

    ModalBottomSheet(
        modifier = modifier,
        windowInsets = WindowInsets.systemBars,
        sheetState = state,
//      dragHandle = null,
        content = {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                title?.let {
                    Text(
                        text = it.value,
                        style = MaterialTheme.typography.titleSmall,
                    )
                }
                Box(
                    modifier = Modifier
                        .heightIn(max = contentHeight)
                        .fillMaxWidth()
                ) {
                    content()
                }
                if (primaryAction != null || secondaryAction != null || tertiaryAction != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(
                            12.dp,
                            Alignment.CenterVertically
                        ),
                    ) {
                        if (primaryAction != null || secondaryAction != null) {
                            Row(
                                modifier = Modifier
                                    .height(40.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                secondaryAction?.let { it() } ?: Box(Modifier)
                                primaryAction?.let { it() } ?: Box(Modifier)
                            }
                        }
                        tertiaryAction?.let { it() }
                    }
                }
            }
        },
        onDismissRequest = onDismissRequest,
    )
}