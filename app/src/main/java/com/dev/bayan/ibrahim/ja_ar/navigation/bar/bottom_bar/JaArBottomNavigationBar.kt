package com.dev.bayan.ibrahim.ja_ar.navigation.bar.bottom_bar


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.ui.components.container.JaArContainer
import com.dev.bayan.ibrahim.core.ui.components.container.JaArContainerType
import com.dev.bayan.ibrahim.core.ui.navigation.JaArNavRoute
import com.dev.bayan.ibrahim.ja_ar.navigation.bar.JaArNavDestination

@Composable
fun JaArBottomNavigationBar(
    modifier: Modifier = Modifier,
    selectedDestination: JaArNavRoute.TopLevel?,
    onClickDestination: (JaArNavRoute.TopLevel) -> Unit,
) {
    JaArContainer(
        type = JaArContainerType.PRIMARY
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = modifier
                .fillMaxWidth()
        ) {
            JaArNavRoute.TopLevel.entries.forEach {
                JaArNavDestination(
                    destination = it,
                    selected = it == selectedDestination,
                    onClick = { onClickDestination(it) }
                )
            }
        }
    }
}

