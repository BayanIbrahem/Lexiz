package com.dev.bayan.ibrahim.ja_ar.navigation.bar.rail


import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dev.bayan.ibrahim.core.ui.navigation.JaArNavRoute
import com.dev.bayan.ibrahim.ja_ar.R
import com.dev.bayan.ibrahim.ja_ar.navigation.bar.JaArNavDestination

@Composable
fun JaArNavigationRail(
    modifier: Modifier = Modifier,
    selectedDestination: JaArNavRoute.TopLevel?,
    onClickDestination: (JaArNavRoute.TopLevel) -> Unit,
) {
    val containerColor = MaterialTheme.colorScheme.primaryContainer
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxHeight()
            .drawBehind {
                drawRoundRect(
                    color = containerColor,
                    cornerRadius = CornerRadius(16.dp.toPx()),
                )
            }
            .padding(8.dp),
    ) {
        RailLogoImage(logo = R.drawable.jaar_logo)
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
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

@Composable
private fun RailLogoImage(
    modifier: Modifier = Modifier,
    @DrawableRes logo: Int,
) {
    Image(
        modifier = modifier
            .size(64.dp)
            .clip(CircleShape),
        painter = painterResource(id = logo),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )

}
