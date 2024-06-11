package com.dev.bayan.ibrahim.ja_ar.navigation.graph


import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dev.bayan.ibrahim.core.common.JaArScreenSize
import com.dev.bayan.ibrahim.core.ui.navigation.JaArNavActions
import com.dev.bayan.ibrahim.core.ui.navigation.JaArNavRoute
import com.dev.bayan.ibrahim.feature.library.ui.graph.library
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.graph.quiz

@Composable
fun JaArNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    navActions: JaArNavActions,
    screenSize: JaArScreenSize,
) {
    LaunchedEffect(key1 = Unit) {
        Log.d("nav_controller", navController.graph.map { it.route }.toString())
    }
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = JaArNavRoute.TopLevel.HOME.route,
    ) {
        composable(
            route = JaArNavRoute.TopLevel.HOME.route
        ) {
            // TODO add home content
            Box(Modifier.fillMaxSize(), Alignment.Center) {
                Text(
                    modifier = Modifier
                        .semantics {
                            this.testTag = "test_home_screen"
                        },
                    text = "Home",
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }
        library(
            screenSize = screenSize,
            onNavigateUp = navActions::navigateUp,
            onEditWord = {
                navActions.jaArNavigateTo(JaArNavRoute.LibraryEditorWord.getDestination(it))
            },
            onEditCategory = {
                navActions.jaArNavigateTo(JaArNavRoute.LibraryEditorCategory.getDestination(it))
            },
        )
        quiz(
            screenSize = screenSize,
            onNavigateUp = navActions::navigateUp,
        )
    }
}
