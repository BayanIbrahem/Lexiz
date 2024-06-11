@file:Suppress("EnumEntryName")

package com.dev.bayan.ibrahim.core.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import com.dev.bayan.ibrahim.core.ui.R
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicVector
import com.dev.bayan.ibrahim.core.ui.res.actionOnModelResource

sealed interface JaArNavRoute : NavRoute, NavRouteType {
    override val graph: String get() = "jaar"

    enum class TopLevel(
        override val routeName: JaArDynamicString,
        override val selectedIcon: JaArDynamicVector,
        override val unselectedIcon: JaArDynamicVector,
    ) : JaArNavRoute, NavRouteType.TopLevel {
        QUIZ(
            routeName = JaArDynamicString.PluralRes(R.plurals.quiz, 1),
            selectedIcon = JaArDynamicVector.Res(R.drawable.ic_quiz_fill),
            unselectedIcon = JaArDynamicVector.Res(R.drawable.ic_quiz_outline)
        ),
        HOME(
            routeName = JaArDynamicString.StrRes(R.string.home),
            selectedIcon = JaArDynamicVector.Vector(Icons.Filled.Home),
            unselectedIcon = JaArDynamicVector.Vector(Icons.Outlined.Home)
        ),
        LIBRARY(
            routeName = JaArDynamicString.StrRes(R.string.library),
            selectedIcon = JaArDynamicVector.Res(R.drawable.ic_library_fill),
            unselectedIcon = JaArDynamicVector.Res(R.drawable.ic_library_outline)
        );

        override val args = setOf<String>()

        fun getDestination() = generateDestinationRoute()
    }

    data object LibraryEditorWord : JaArNavRoute {
        enum class Args { word_id }

        override val args: Set<String> get() = Args.entries.asNavLabels()
        override val routeName = JaArDynamicString.Builder {
            actionOnModelResource(action = R.string.add, model = R.plurals.word, count = 1)
        }
        override val editRouteName = JaArDynamicString.Builder {
            actionOnModelResource(action = R.string.edit, model = R.plurals.word, count = 1)
        }

        fun getDestination(
            wordId: Long? = null,
        ) = generateDestinationRoute(
            JaArNavArg(
                label = Args.word_id.name,
                value = wordId?.toString(),
            )
        )
    }

    data object LibraryEditorCategory : JaArNavRoute {
        enum class Args { category_id }

        override val args: Set<String> get() = Args.entries.asNavLabels()
        override val routeName = JaArDynamicString.Builder {
            actionOnModelResource(action = R.string.add, model = R.plurals.category, count = 1)
        }
        override val editRouteName = JaArDynamicString.Builder {
            actionOnModelResource(action = R.string.edit, model = R.plurals.category, count = 1)
        }

        fun getDestination(
            categoryId: Long? = null,
        ) = generateDestinationRoute(
            JaArNavArg(
                label = Args.category_id.name,
                value = categoryId?.toString(),
            )
        )
    }

}