package com.joseleandro.flowtask.ui.components.layout

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joseleandro.flowtask.R
import com.joseleandro.flowtask.ui.theme.FlowTaskTheme

@Composable
fun DrawerItem(
    label: String,
    @DrawableRes icon: Int,
    selected: Boolean = false,
    onClick: () -> Unit
) {

    val contentColorUnselected = Color(0xFF9CA3AF)
    val contentColorSelected = MaterialTheme.colorScheme.primary
    val containerColorUnselected = Color.Transparent
    val containerColorSelected = MaterialTheme.colorScheme.primary.copy(alpha = .1f)

    val colorIcon = remember(selected) {
        if (selected) contentColorSelected else contentColorUnselected
    }

    NavigationDrawerItem(
        colors = NavigationDrawerItemDefaults.colors(
            unselectedTextColor = contentColorUnselected,
            selectedTextColor = contentColorSelected,
            unselectedContainerColor = containerColorUnselected,
            selectedContainerColor = containerColorSelected
        ),
        icon = {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = icon),
                contentDescription = stringResource(R.string.icon_navigation_drawer_item),
                colorFilter = ColorFilter.tint(
                    color = colorIcon
                )
            )
        },
        shape = RoundedCornerShape(16.dp),
        onClick = onClick,
        selected = selected,
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium
            )
        }
    )

}

@Preview
@Composable
private fun DrawerItemDarkUnselectedPreview() {

    FlowTaskTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        DrawerItem(
            icon = R.drawable.grid,
            label = "Painel Geral",
            onClick = {},
            selected = false
        )
    }
}

@Preview
@Composable
private fun DrawerItemDarkSelectedPreview() {

    FlowTaskTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        DrawerItem(
            icon = R.drawable.grid,
            label = "Painel Geral",
            onClick = {},
            selected = true
        )
    }
}