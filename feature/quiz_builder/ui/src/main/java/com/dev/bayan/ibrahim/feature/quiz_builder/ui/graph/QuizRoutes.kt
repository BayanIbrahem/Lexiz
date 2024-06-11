package com.dev.bayan.ibrahim.feature.quiz_builder.ui.graph

import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.core.ui.navigation.NavRoute
import com.dev.bayan.ibrahim.core.ui.navigation.NavRouteType
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.R

internal enum class QuizRoutes(
    override val routeName: JaArDynamicString,
) : NavRoute, NavRouteType.Inner {
    QuizSetup(routeName = JaArDynamicString.StrRes(R.string.create_quiz)),
    TemplateGroups(routeName = JaArDynamicString.StrRes(R.string.template_filter_groups)),
    NewGroup(routeName = JaArDynamicString.StrRes(R.string.new_filter_group)),
    TemplateQuizes(routeName = JaArDynamicString.StrRes(R.string.template_quizes));

    override val graph: String get() = "quiz"
    override val args: Set<String> get() = emptySet()

    fun getDestination() = generateDestinationRoute()
}