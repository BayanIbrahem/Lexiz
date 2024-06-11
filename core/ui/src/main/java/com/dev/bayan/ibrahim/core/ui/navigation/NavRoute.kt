package com.dev.bayan.ibrahim.core.ui.navigation

import android.net.Uri
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicVector

/**
 * @property label the local name of the destination, like 'profile', 'home', 'library', no need to override it just if some problems happened
 * @property graph the graph prefix, like the 'myApp' for the main navigation routes
 * @property args set of arguments for navigation to this destination
 * @property route the route used in nav graph builder,
 * @property generateDestinationRoute returns the route with arguments values if not null
 */
interface NavRoute : NavRouteType {
    val label: String get() = if (this is Enum<*>) this.name else this.javaClass.simpleName

    private fun routeBuilder(
        scheme: String,
        args: Set<JaArNavArg>,
    ): String {
        val uriBuilder = Uri.Builder()
        uriBuilder.apply {
            scheme(scheme)
            args.forEach {
                appendQueryParameter(it.label, it.value)
            }
        }
        return uriBuilder.build().toString()
    }

    val graph: String
    val args: Set<String>

    val route: String
        get() = routeBuilder(
            scheme = "${graph}_${label}",
            args = args.asNavArg()
        )


    /**
     * returns the route used by navigation controller with arguments, any argument that has
     * a null value or it is not in [args] will be ignored
     */
    fun generateDestinationRoute(vararg args: JaArNavArg): String {
        return args.filter {
            it.label in this.args && it.value != null
        }.distinctBy {
            it.label
        }.toSet().run {
            routeBuilder(
                scheme = "${graph}_${label}",
                this,
            )
        }
    }
}

interface NavRouteType {
    val routeName: JaArDynamicString
    val editRouteName: JaArDynamicString? get() = null

    interface TopLevel : NavRouteType {
        val selectedIcon: JaArDynamicVector
        val unselectedIcon: JaArDynamicVector
    }

    interface Inner : NavRouteType
}

