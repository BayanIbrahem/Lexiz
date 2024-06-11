package com.dev.bayan.ibrahim.core.ui.navigation

import androidx.navigation.NavHostController

class JaArNavActions(private val navHostController: NavHostController) {
    fun jaArNavigateTo(route: String, isTopLevelDestination: Boolean = false) {
        if (isTopLevelDestination) {
            navHostController.clearBackStack(navHostController.graph.startDestinationId)
        }
        navHostController.navigate(route) {
            this.launchSingleTop = true
        }
    }

    fun navigateUp() {
        navHostController.popBackStack()
    }
}
