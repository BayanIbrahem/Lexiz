package com.dev.bayan.ibrahim.feature.quiz_builder.ui.screen.core.filter_item

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.FilterAppearanceStatus
import com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter.Filter
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.R
import com.dev.bayan.ibrahim.feature.quiz_builder.ui.utils.filter.icon

@Composable
internal fun FilterTypeGridItem(
    modifier: Modifier = Modifier,
    type: Filter,
    appearanceStatus: FilterAppearanceStatus,
    onClick: (() -> Unit)?,
) {
    FilterGridItem(
        modifier = modifier,
        appearanceStatus = appearanceStatus,
        width = dimensionResource(R.dimen.quizBuilder_filterItem_templateWidth),
        iconRes = type.icon,
        name = stringResource(id = type.resName),
        label = null,
        onClick = onClick
    )
}

