package com.joseleandro.flowtask.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joseleandro.flowtask.R
import com.joseleandro.flowtask.ui.components.GlowingFab
import com.joseleandro.flowtask.ui.components.TagsBottomSheet
import com.joseleandro.flowtask.ui.theme.FlowTaskTheme
import com.joseleandro.flowtask.ui.viewmodel.NavigationViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TagsScreen() {

    val navigationViewModel: NavigationViewModel = koinViewModel()

    TagsScreen(
        onBack = {
            navigationViewModel.pop()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagsScreen(
    onBack: () -> Unit
) {

    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Gerenciar Tags",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            GlowingFab {
                showBottomSheet = !showBottomSheet
            }
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) { innerPadding ->
        Content(
            modifier = Modifier.padding(innerPadding)
        )

        if (showBottomSheet) {

            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 1.dp
            ) {
                TagsBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    }
                )
            }
        }
    }
}


@Composable
fun Content(modifier: Modifier = Modifier) {

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {

        item {
            TagCard(
                title = "Trabalho",
                color = Color(0xFF5CBB1D)
            )
        }

        item {
            TagCard(
                title = "Supermercado",
                color = Color(0xFFD7691E)
            )
        }

        item {
            TagCard(
                title = "Escola",
                color = Color(0xFFE81111)
            )
        }


        item {
            TagCard(
                title = "Design",
                color = Color(0xFF3FC1D9)
            )
        }


        item {
            TagCard(
                title = "Projetos",
                color = Color(0xFF791DBB)
            )
        }


        item {
            TagCard(
                title = "Academia",
                color = Color(0xFFCE68E3)
            )
        }


        item {
            TagCard(
                title = "Lazer",
                color = Color(0xFF664CA9)
            )
        }
    }
}

@Composable
fun TagCard(
    modifier: Modifier = Modifier,
    title: String,
    color: Color
) {
    ListItem(
        modifier = modifier,
        headlineContent = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        },
        supportingContent = {
            Text(
                text = "12 tarefas",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = .5f)
                )
            )
        },
        leadingContent = {
            Image(
                modifier = Modifier.size(20.dp),
                painter = painterResource(
                    id = R.drawable.park_solid_tag
                ),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color)
            )
        },
        trailingContent = {
            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null
                )
            }
        }
    )
}


@Preview
@Composable
private fun TagsScreenDarkPreview() {

    FlowTaskTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        TagsScreen(
            onBack = {}
        )
    }
}