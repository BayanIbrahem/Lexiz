package com.dev.bayan.ibrahim.ja_ar

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dev.bayan.ibrahim.core.common.JaArScreenSize
import com.dev.bayan.ibrahim.core.ui.navigation.JaArNavActions
import com.dev.bayan.ibrahim.core.ui.navigation.JaArNavRoute
import com.dev.bayan.ibrahim.core.ui.navigation.JaArNavigationType
import com.dev.bayan.ibrahim.core.ui.utils.animation.fadeHorizontalExpandEnterTransition
import com.dev.bayan.ibrahim.core.ui.utils.animation.fadeHorizontalShrinkExitTransition
import com.dev.bayan.ibrahim.core.ui.utils.animation.fadeVerticalExpandEnterTransition
import com.dev.bayan.ibrahim.core.ui.utils.animation.fadeVerticalShrinkExitTransition
import com.dev.bayan.ibrahim.ja_ar.navigation.bar.JaArNavigationBar
import com.dev.bayan.ibrahim.ja_ar.navigation.graph.JaArNavGraph
import com.dev.bayan.ibrahim.ja_ar.window_size_class.JaArWindowHeightSizeClass
import com.dev.bayan.ibrahim.ja_ar.window_size_class.JaArWindowWidthSizeClass

@Composable
fun JaArApp(
    modifier: Modifier = Modifier,
    widthSizeClass: JaArWindowWidthSizeClass = JaArWindowWidthSizeClass.Compact,
    heightSizeClass: JaArWindowHeightSizeClass = JaArWindowHeightSizeClass.Medium,
    appViewModel: JaArViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        appViewModel.initDbIfEmpty(context) { success ->
            Log.d("init_db", "inserted data $success")
        }
    }
    val screenSize = getScreenSize(widthSizeClass, heightSizeClass)
    val navigationType = if (screenSize.isCompat()) {
        JaArNavigationType.BOTTOM_BAR
    } else {
        JaArNavigationType.NAVIGATION_RAIL
    }

    JaArAppContent(
        modifier = modifier,
        screenSize = screenSize,
        navigationType = navigationType,
    )
}

@Composable
private fun JaArAppContent(
    modifier: Modifier = Modifier,
    screenSize: JaArScreenSize,
    navigationType: JaArNavigationType,
) {
    val navController = rememberNavController()
    val navActions = remember(navController) { JaArNavActions(navController) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination by remember(navBackStackEntry?.destination?.route) {
        derivedStateOf {
            JaArNavRoute.TopLevel.entries.firstOrNull {
                it.route == navBackStackEntry?.destination?.route
            }
        }
    }
    val isTopLevelDestination by remember(selectedDestination) {
        derivedStateOf { selectedDestination != null }
    }

    Scaffold(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
               .padding(it)
               .padding(dimensionResource(id = com.dev.bayan.ibrahim.core.ui.R.dimen.main_screen_padding)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = com.dev.bayan.ibrahim.core.ui.R.dimen.main_screen_content_spacedBy))
        ) {
            AnimatedVisibility(
                visible = navigationType == JaArNavigationType.NAVIGATION_RAIL && isTopLevelDestination,
                enter = fadeHorizontalExpandEnterTransition(Alignment.Start),
                exit = fadeHorizontalShrinkExitTransition(Alignment.Start),
            ) {
                JaArNavigationBar(
                    bottomNavigation = false,
                    selectedDestination = selectedDestination,
                    onClickDestination = {
                        if (it != selectedDestination) {
                            navActions.jaArNavigateTo(it.getDestination())
                        }
                    }
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = com.dev.bayan.ibrahim.core.ui.R.dimen.main_screen_content_spacedBy))
            ) {
                JaArNavGraph(
                    modifier = Modifier
                        .weight(1f),
                    navController = navController,
                    navActions = navActions,
                    screenSize = screenSize
                )
                AnimatedVisibility(
                    visible = navigationType == JaArNavigationType.BOTTOM_BAR && isTopLevelDestination,
                    enter = fadeVerticalExpandEnterTransition(Alignment.Bottom),
                    exit = fadeVerticalShrinkExitTransition(Alignment.Bottom),
                ) {
                    JaArNavigationBar(
                        bottomNavigation = true,
                        selectedDestination = selectedDestination,
                        onClickDestination = {
                            if (it != selectedDestination) {
                                navActions.jaArNavigateTo(it.getDestination())
                            }
                        }
                    )
                }
            }
        }
    }
}

private fun getScreenSize(
    widthSizeClass: JaArWindowWidthSizeClass,
    heightSizeClass: JaArWindowHeightSizeClass,
) = when (widthSizeClass) {
    JaArWindowWidthSizeClass.Compact -> {
        if (heightSizeClass == JaArWindowHeightSizeClass.Compact) {
            JaArScreenSize.COMPAT_SHORT
        } else if (heightSizeClass == JaArWindowHeightSizeClass.Expanded) {
            JaArScreenSize.COMPAT_HIGH
        } else {
            JaArScreenSize.COMPAT
        }
    }

    JaArWindowWidthSizeClass.Medium -> {
        if (heightSizeClass == JaArWindowHeightSizeClass.Compact) {
            JaArScreenSize.MEDIUM_SHORT
        } else if (heightSizeClass == JaArWindowHeightSizeClass.Expanded) {
            JaArScreenSize.MEDIUM_HIGH
        } else {
            JaArScreenSize.MEDIUM
        }
    }

    JaArWindowWidthSizeClass.Expanded -> {
        if (heightSizeClass == JaArWindowHeightSizeClass.Compact) {
            JaArScreenSize.EXPANDED_SHORT
        } else if (heightSizeClass == JaArWindowHeightSizeClass.Expanded) {
            JaArScreenSize.EXPANDED_HIGH
        } else {
            JaArScreenSize.EXPANDED
        }
    }

    else -> {
        JaArScreenSize.COMPAT
    }
}