package com.joseleandro.flowtask.ui.components.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joseleandro.flowtask.R
import com.joseleandro.flowtask.ui.screen.MainTopBarDrawer
import com.joseleandro.flowtask.ui.theme.FlowTaskTheme

@Composable
fun MainDrawerContent() {

    ModalDrawerSheet(
        modifier = Modifier.fillMaxWidth(.8f),
        drawerContainerColor = Color(0xFF0F1820)
    ) {

        MainTopBarDrawer()

        Column(
            Modifier
                .padding(top = 16.dp)
                .fillMaxSize(),
        ) {

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                DrawerItem(
                    icon = R.drawable.grid,
                    label = "Painel Geral",
                    onClick = {},
                    selected = true
                )
                DrawerItem(
                    icon = R.drawable.check_fill,
                    label = "Gerenciar Tarefas",
                    onClick = {},
                    selected = false
                )
                DrawerItem(
                    icon = R.drawable.star,
                    label = "Favoritos",
                    onClick = {},
                    selected = false
                )
                DrawerItem(
                    icon = R.drawable.pie_chart,
                    label = "Insights de Produtividade",
                    onClick = {},
                    selected = false
                )
                DrawerItem(
                    icon = R.drawable.tag,
                    label = "Gerenciar Tags",
                    onClick = {},
                    selected = false
                )
                HorizontalDivider(
                    color = Color(0xFF1F252D)
                )

                DrawerItem(
                    icon = R.drawable.archive,
                    label = "Arquivadas",
                    onClick = {},
                    selected = false
                )

                DrawerItem(
                    icon = R.drawable.delete,
                    label = "Lixeira",
                    onClick = {},
                    selected = false
                )
            }

            Surface(
                color = Color(0xFF0F161E)
            ) {

                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {

                    DrawerItem(
                        icon = R.drawable.settings,
                        label = "Configurações",
                        onClick = {},
                        selected = false
                    )

                    DrawerItem(
                        icon = R.drawable.help,
                        label = "Ajuda",
                        onClick = {},
                        selected = false
                    )
                }
            }

        }
    }
}

@Preview
@Composable
private fun MainDrawerContentDarkPreview() {

    FlowTaskTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        MainDrawerContent()
    }
}