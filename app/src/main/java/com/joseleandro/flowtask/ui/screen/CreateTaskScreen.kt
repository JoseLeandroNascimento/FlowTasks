package com.joseleandro.flowtask.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joseleandro.flowtask.R
import com.joseleandro.flowtask.ui.components.FlowTaskTextField
import com.joseleandro.flowtask.ui.viewmodel.NavigationViewModel
import com.joseleandro.flowtask.ui.theme.FlowTaskTheme
import org.koin.compose.viewmodel.koinViewModel


data class Tag(
    val id: Int,
    val name: String
)

val TAGS: List<Tag> = listOf(
    Tag(1, "Trabalho"),
    Tag(2, "Escola"),
    Tag(3, "Academia"),
    Tag(4, "Projeto"),
    Tag(5, "Design"),
    Tag(6, "Arte"),
)

val COLORS = listOf(
    Color(0xFFFED9FA),
    Color(0xFFFCE1BC),
    Color(0xFFFEF17E),
    Color(0xFFDDF7B6),
    Color(0xFFD3E7FF),
)

@Composable
fun CreateTaskScreen() {

    val navigationViewModel: NavigationViewModel = koinViewModel()

    CreateTaskScreen(
        onBack = navigationViewModel::pop
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskScreen(
    onBack: () -> Unit
) {

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.nova_tarefa),
                        style = MaterialTheme.typography.titleLarge
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
        }
    ) { innerPadding ->
        CreateTaskContent(
            modifier = Modifier.padding(innerPadding),
            onBack = onBack
        )
    }

}

@Composable
fun CreateTaskContent(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {

    val colorDefault = MaterialTheme.colorScheme.primary
    var tagSelected by remember { mutableStateOf<Tag?>(null) }
    var colorSelected by remember { mutableStateOf<Color?>(null) }


    val colorSelectedAnimation by animateColorAsState(
        targetValue = colorSelected ?: colorDefault,
        animationSpec = tween(
            durationMillis = 280,
            easing = FastOutSlowInEasing
        ),
        label = "color"
    )

    val backgroundColor by animateColorAsState(
        targetValue = colorSelectedAnimation.copy(alpha = 0.2f),
        animationSpec = tween(
            durationMillis = 350,
            delayMillis = 40,
            easing = LinearOutSlowInEasing
        ),
        label = "backgroundColor"
    )

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    state = rememberScrollState()
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(
                    horizontal = 16.dp
                )
            ) {

                items(items = TAGS) { tag ->
                    FilterChip(
                        onClick = {
                            tagSelected = tag
                        },
                        label = {
                            Text(
                                text = tag.name,
                                style = MaterialTheme.typography.labelLarge
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = MaterialTheme.shapes.large,
                        selected = tagSelected?.let { tagSelected ->
                            tag.id == tagSelected.id
                        } ?: false
                    )
                }
            }

            Column(
                modifier = Modifier.padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Box(
                    modifier = Modifier
                        .background(
                            color = backgroundColor,
                            shape = MaterialTheme.shapes.extraLarge
                        )
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Image(
                        modifier = Modifier.size(34.dp),
                        painter = painterResource(
                            id = R.drawable.cart_rounded_icon_task
                        ),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = colorSelectedAnimation)
                    )

                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.escolher_um_cone),
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Text(
                        text = stringResource(R.string.toque_para_alterar),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = .5f)
                        )
                    )
                }
            }


            FlowTaskTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                state = rememberTextFieldState(),
                label = stringResource(R.string.nome_da_tarefa),
                placeholder = stringResource(R.string.digite_aqui_o_nome_da_tarefa)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    text = stringResource(R.string.cor_de_fundo),
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = .5f)
                    )
                )

                BoxWithConstraints(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val itemCount = COLORS.size + 1
                    val horizontalPadding = 16.dp * 2
                    val spacing = 8.dp

                    val availableWidth = maxWidth - horizontalPadding - (spacing * (itemCount - 1))
                    val itemSize = availableWidth / itemCount

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(spacing),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(COLORS) { color ->
                            ColorSelect(
                                size = itemSize.coerceAtMost(56.dp),
                                color = color,
                                selected = color == colorSelected,
                                onSelected = { colorSelected = color }
                            )
                        }

                        item {

                            FilledIconButton(
                                modifier = Modifier.size(itemSize.coerceAtMost(56.dp)),
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = Color(0xFFF2F2F2)
                                ),
                                onClick = {}
                            ) {
                                Image(
                                    painter = painterResource(
                                        id = R.drawable.color_lens
                                    ),
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }

            }

            Surface(
                modifier = Modifier.padding(horizontal = 16.dp),
                shape = MaterialTheme.shapes.large,
                color = Color(0xFF151F2C)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.oi_task),
                            contentDescription = stringResource(R.string.imagem_icon_sub_tarefa),
                            colorFilter = ColorFilter.tint(
                                color = MaterialTheme.colorScheme.primary
                            )
                        )

                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "Subtarefas",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    lineHeight = 16.sp
                                )
                            )
                            Text(
                                text = "Divida a tarefas em passos menores",
                                style = MaterialTheme.typography.labelSmall,
                                lineHeight = 11.sp
                            )
                        }
                    }

                    FilledIconButton(
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                        ),
                        shape = MaterialTheme.shapes.medium,
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.adicionar_subtarefa)
                        )
                    }
                }
            }


            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = stringResource(R.string.detalhes),
                style = MaterialTheme.typography.titleSmall.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = .5f)
                )
            )

            Surface(
                modifier = Modifier.padding(horizontal = 16.dp),
                shape = MaterialTheme.shapes.medium,
                color = Color(0xFF151F2C)
            ) {

                Column(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth()
                ) {

                    ListItem(
                        colors = ListItemDefaults.colors(
                            containerColor = Color(0xFF151F2C)
                        ),
                        leadingContent = {
                            Image(
                                painter = painterResource(
                                    id = R.drawable.calendar
                                ),
                                contentDescription = stringResource(R.string.icon_calendario),
                                colorFilter = ColorFilter.tint(
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                        },
                        headlineContent = {
                            Text(
                                text = stringResource(R.string.planejamento),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp
                                )
                            )
                        },
                        trailingContent = {

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.n_o_repete),
                                    style = MaterialTheme.typography.labelMedium
                                )
                                Icon(
                                    modifier = Modifier.size(16.dp),
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                                    contentDescription = null
                                )
                            }
                        }
                    )


                    ListItem(
                        colors = ListItemDefaults.colors(
                            containerColor = Color(0xFF151F2C)
                        ),
                        leadingContent = {
                            Image(
                                painter = painterResource(
                                    id = R.drawable.target_fill
                                ),
                                contentDescription = stringResource(R.string.icon_calendario),
                                colorFilter = ColorFilter.tint(
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                        },
                        headlineContent = {
                            Text(
                                text = stringResource(R.string.data_de_vencimento),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp
                                )
                            )
                        },
                        trailingContent = {

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.sem_prazo),
                                    style = MaterialTheme.typography.labelMedium
                                )
                                Icon(
                                    modifier = Modifier.size(16.dp),
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                                    contentDescription = null
                                )
                            }
                        }
                    )


                    ListItem(
                        colors = ListItemDefaults.colors(
                            containerColor = Color(0xFF151F2C)
                        ),
                        leadingContent = {
                            Image(
                                painter = painterResource(
                                    id = R.drawable.alarm_sharp
                                ),
                                contentDescription = stringResource(R.string.icon_calendario),
                                colorFilter = ColorFilter.tint(
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                        },
                        headlineContent = {
                            Text(
                                text = stringResource(R.string.alerta),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp
                                )
                            )
                        },
                        trailingContent = {

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.sem_alarme),
                                    style = MaterialTheme.typography.labelMedium
                                )
                                Icon(
                                    modifier = Modifier.size(16.dp),
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                                    contentDescription = null
                                )
                            }
                        }
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(
                    150.dp
                )
            )
        }

        Surface(
            modifier = Modifier.align(Alignment.BottomCenter),
            color = Color(0xFF151F2C)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = .1f)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)
                ) {

                    Button(
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = .2f),
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        onClick = onBack,
                        contentPadding = PaddingValues(
                            16.dp
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.cancelar),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    Button(
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White
                        ),
                        onClick = {},
                        contentPadding = PaddingValues(
                            16.dp
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.criar_tarefa),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun ColorSelect(
    modifier: Modifier = Modifier,
    size: Dp = 50.dp,
    color: Color = MaterialTheme.colorScheme.primary,
    selected: Boolean = false,
    onSelected: () -> Unit
) {

    val sizeIntern by animateDpAsState(
        targetValue = if (selected) size * .8f else size,
        animationSpec = tween(
            durationMillis = 250,
            easing = FastOutSlowInEasing
        ),
        label = "scale"
    )

    Box(
        modifier = modifier
            .clip(
                shape = CircleShape
            )
            .clickable {
                onSelected()
            }
            .size(size)
            .border(
                width = 2.dp,
                color = color,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .background(
                    color = color,
                    shape = CircleShape
                )
                .size(sizeIntern),
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(
                visible = selected,
                enter = scaleIn(
                    animationSpec = tween(500)
                ) + fadeIn(),
                exit = scaleOut(
                    animationSpec = tween(150)
                ) + fadeOut()
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.background
                )
            }
        }
    }

}

@Preview
@Composable
private fun ColorSelectDarkPreview() {
    FlowTaskTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        ColorSelect() {

        }
    }
}

@Preview
@Composable
private fun CreateTaskScreenDarkPreview() {

    FlowTaskTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        CreateTaskScreen()
    }
}