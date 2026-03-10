package com.joseleandro.flowtask.ui.screen.task.components.solid

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.joseleandro.flowtask.R

val solidIcons = listOf(
    SolidIcon(R.drawable.ph_seal_check_fill),
    SolidIcon(R.drawable.ri_plane_icon_task),
    SolidIcon(R.drawable.work_icon_task),
    SolidIcon(R.drawable.cart_rounded_icon_task),
    SolidIcon(R.drawable.weights_icon_task),
    SolidIcon(R.drawable.bi_tools),
    SolidIcon(R.drawable.boxicons_broom_filled),
    SolidIcon(R.drawable.boxicons_medal_star_filled),
    SolidIcon(R.drawable.bxs_book_bookmark),
    SolidIcon(R.drawable.bxs_bus_school),
    SolidIcon(R.drawable.dashicons_admin_tools),
    SolidIcon(R.drawable.fa6_solid_briefcase_medical),
    SolidIcon(R.drawable.fa_pencil),
    SolidIcon(R.drawable.fa_solid_chart_line),
    SolidIcon(R.drawable.fa_solid_graduation_cap),
    SolidIcon(R.drawable.fa_solid_running),
    SolidIcon(R.drawable.fluent_clipboard_text_edit_32_filled),
    SolidIcon(R.drawable.fluent_note_24_filled),
    SolidIcon(R.drawable.ic_outline_menu_book),
    SolidIcon(R.drawable.ion_book_sharp),
    SolidIcon(R.drawable.ion_rocket_sharp),
    SolidIcon(R.drawable.fluent_weather_snow_shower_night_24_filled),
    SolidIcon(R.drawable.ion_water_sharp),
    SolidIcon(R.drawable.jam_task_list_f),
    SolidIcon(R.drawable.mage_checklist_note_fill),
    SolidIcon(R.drawable.material_symbols_light_music_note_2),
    SolidIcon(R.drawable.material_symbols_note_rounded),
    SolidIcon(R.drawable.material_symbols_star),
    SolidIcon(R.drawable.material_symbols_stylus_note_rounded),
    SolidIcon(R.drawable.material_symbols_tools_power_drill),
    SolidIcon(R.drawable.mdi_alarm_note),
    SolidIcon(R.drawable.mdi_gift),
    SolidIcon(R.drawable.mdi_heart),
    SolidIcon(R.drawable.mdi_music_note),
    SolidIcon(R.drawable.mingcute_car_fill),
    SolidIcon(R.drawable.mingcute_desk_lamp_fill),
    SolidIcon(R.drawable.mingcute_fire_fill),
    SolidIcon(R.drawable.mingcute_target_fill),
    SolidIcon(R.drawable.park_solid_tag),
    SolidIcon(R.drawable.ph_chart_bar_fill),
    SolidIcon(R.drawable.ph_coffee_fill),
    SolidIcon(R.drawable.ph_note_fill),
    SolidIcon(R.drawable.ph_star_four_fill),
    SolidIcon(R.drawable.ri_shopping_bag_fill),
    SolidIcon(R.drawable.solar_bill_list_bold),
    SolidIcon(R.drawable.solar_camera_bold),
    SolidIcon(R.drawable.solar_clipboard_bold),
    SolidIcon(R.drawable.solar_cup_star_bold),
    SolidIcon(R.drawable.solar_gamepad_bold),
    SolidIcon(R.drawable.solar_sofa_bold),
    SolidIcon(R.drawable.streamline_business_idea_money_solid),
    SolidIcon(R.drawable.streamline_fireworks_rocket_solid),
    SolidIcon(R.drawable.streamline_sharp_star_badge_solid),
    SolidIcon(R.drawable.streamline_ultimate_cash_briefcase_bold),
    SolidIcon(R.drawable.tabler_apple_filled),
    SolidIcon(R.drawable.tabler_briefcase_filled),
    SolidIcon(R.drawable.tdesign_tools_filled),
    SolidIcon(R.drawable.teenyicons_desklamp_solid),
    SolidIcon(R.drawable.vector),
)

@Composable
fun SolidIconsPicker(
    onSelect: (Int) -> Unit
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        items(solidIcons) { icon ->

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .clickable {
                        onSelect(icon.resId)
                    },
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(icon.resId),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}