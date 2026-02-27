package com.joseleandro.flowtask.ui.theme

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.joseleandro.flowtask.R

object ColorPickerPallet {

    enum class CategoryColorPallet(
        @StringRes val title: Int
    ) {
        CLARAS(R.string.color_category_light),
        VIBRANTES(R.string.color_category_vibrant),
        ESCURAS(R.string.color_category_dark),
        EXTRAS(R.string.color_category_extra)
    }

    data class ColorCategory(
        val category: CategoryColorPallet,
        val colors: List<Color>
    )

    val colorDefault = primaryDrk

    private val vibrantColors = listOf(
        colorDefault,
        Color(0xFFFF1744), Color(0xFFF50057), Color(0xFFD500F9),
        Color(0xFF651FFF), Color(0xFF3D5AFE), Color(0xFF2979FF),
        Color(0xFF00B0FF), Color(0xFF00E5FF), Color(0xFF1DE9B6),
        Color(0xFF00E676), Color(0xFF76FF03), Color(0xFFC6FF00),
        Color(0xFFFFEA00), Color(0xFFFFC400), Color(0xFFFF9100),
        Color(0xFFFF3D00), Color(0xFF00C853), Color(0xFFAA00FF),
        Color(0xFF0091EA), Color(0xFFFF6D00),

        Color(0xFFFF5252), Color(0xFFFF4081), Color(0xFFE040FB),
        Color(0xFF7C4DFF), Color(0xFF536DFE), Color(0xFF448AFF),
        Color(0xFF40C4FF), Color(0xFF18FFFF), Color(0xFF64FFDA),
        Color(0xFF69F0AE), Color(0xFFB2FF59), Color(0xFFEEFF41),
        Color(0xFFFFFF00), Color(0xFFFFD740), Color(0xFFFFAB40),
        Color(0xFFFF6E40), Color(0xFFE91E63), Color(0xFF3F51B5),
        Color(0xFF6200EA), Color(0xFF2962FF)
    )

    private val darkColors = listOf(
        Color(0xFFB71C1C), Color(0xFF880E4F), Color(0xFF4A148C),
        Color(0xFF311B92), Color(0xFF1A237E), Color(0xFF0D47A1),
        Color(0xFF01579B), Color(0xFF006064), Color(0xFF004D40),
        Color(0xFF1B5E20), Color(0xFF33691E), Color(0xFF827717),
        Color(0xFFF57F17), Color(0xFFFF6F00), Color(0xFFE65100),
        Color(0xFFBF360C), Color(0xFF3E2723), Color(0xFF263238),
        Color(0xFF1C1C1C), Color(0xFF212121),

        Color(0xFF4E342E), Color(0xFF37474F), Color(0xFF455A64),
        Color(0xFF1F618D), Color(0xFF154360), Color(0xFF0B5345),
        Color(0xFF145A32), Color(0xFF186A3B), Color(0xFF512E5F),
        Color(0xFF4A235A), Color(0xFF6C3483), Color(0xFF7D6608),
        Color(0xFF784212), Color(0xFF6E2C00), Color(0xFF922B21),
        Color(0xFF641E16), Color(0xFF2C3E50), Color(0xFF1B2631),
        Color(0xFF17202A), Color(0xFF0E6251)
    )

    private val extraColors = listOf(
        Color(0xFFEF5350), Color(0xFFEC407A), Color(0xFFAB47BC),
        Color(0xFF7E57C2), Color(0xFF5C6BC0), Color(0xFF42A5F5),
        Color(0xFF26C6DA), Color(0xFF26A69A), Color(0xFF66BB6A),
        Color(0xFF9CCC65), Color(0xFFD4E157), Color(0xFFFFEE58),
        Color(0xFFFFCA28), Color(0xFFFFA726), Color(0xFFFF7043),
        Color(0xFF8D6E63), Color(0xFF78909C), Color(0xFF9575CD),
        Color(0xFF4DD0E1), Color(0xFFF06292),

        Color(0xFF00ACC1), Color(0xFF00897B), Color(0xFF43A047),
        Color(0xFF7CB342), Color(0xFFC0CA33), Color(0xFFFDD835),
        Color(0xFFFFB300), Color(0xFFFB8C00), Color(0xFFF4511E),
        Color(0xFFE53935), Color(0xFFD81B60), Color(0xFF8E24AA),
        Color(0xFF5E35B1), Color(0xFF3949AB), Color(0xFF1E88E5),
        Color(0xFF039BE5), Color(0xFF9C27B0), Color(0xFF2E7D32),
        Color(0xFF558B2F), Color(0xFF6D4C41)
    )

    private val lightColors = listOf(

        Color(0xFFFFCDD2), Color(0xFFF8BBD0), Color(0xFFE1BEE7),
        Color(0xFFD1C4E9), Color(0xFFC5CAE9), Color(0xFFBBDEFB),
        Color(0xFFB3E5FC), Color(0xFFB2EBF2), Color(0xFFB2DFDB),
        Color(0xFFC8E6C9), Color(0xFFDCEDC8), Color(0xFFF0F4C3),
        Color(0xFFFFF9C4), Color(0xFFFFECB3), Color(0xFFFFE0B2),
        Color(0xFFFFCCBC), Color(0xFFFFD6E7), Color(0xFFE6EEFF),
        Color(0xFFE0F7FA), Color(0xFFE8F5E9),

        Color(0xFFFFE4E1), Color(0xFFFFE4F2), Color(0xFFF3E8FF),
        Color(0xFFEDE7FF), Color(0xFFE8ECFF), Color(0xFFE3F2FF),
        Color(0xFFE1F5FE), Color(0xFFE6FFFA), Color(0xFFE8F8F5),
        Color(0xFFE9F7EF), Color(0xFFFDF2E9), Color(0xFFFFF4E6),
        Color(0xFFFFF0D6), Color(0xFFFFF6CC), Color(0xFFFFFAD1),
        Color(0xFFFCE4EC), Color(0xFFF3E5F5), Color(0xFFEDE7F6),
        Color(0xFFEAF6FF),

        Color(0xFFEAF2F8), Color(0xFFE8DAEF), Color(0xFFFADBD8),
        Color(0xFFFDEDEC), Color(0xFFF6DDCC), Color(0xFFF9EBEA),
        Color(0xFFE3F6FC), Color(0xFFE8F6F3)
    )


    val colorCategories = listOf(
        ColorCategory(CategoryColorPallet.VIBRANTES, vibrantColors),
        ColorCategory(CategoryColorPallet.ESCURAS, darkColors),
        ColorCategory(CategoryColorPallet.EXTRAS, extraColors),
        ColorCategory(CategoryColorPallet.CLARAS, lightColors),
    )

}