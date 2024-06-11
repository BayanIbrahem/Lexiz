package com.dev.bayan.ibrahim.ja_ar.navigation.bar


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dev.bayan.ibrahim.core.ui.navigation.JaArNavRoute
import com.dev.bayan.ibrahim.ja_ar.navigation.bar.bottom_bar.JaArBottomNavigationBar
import com.dev.bayan.ibrahim.ja_ar.navigation.bar.rail.JaArNavigationRail

@Composable
fun JaArNavigationBar(
    modifier: Modifier = Modifier,
    bottomNavigation: Boolean,
    selectedDestination: JaArNavRoute.TopLevel?,
    onClickDestination: (JaArNavRoute.TopLevel) -> Unit,
) {
    if (bottomNavigation) {
        JaArBottomNavigationBar(
            modifier = modifier,
            selectedDestination = selectedDestination,
            onClickDestination = onClickDestination
        )
    } else {
        JaArNavigationRail(
            modifier = modifier,
            selectedDestination = selectedDestination,
            onClickDestination = onClickDestination
        )
    }
}

