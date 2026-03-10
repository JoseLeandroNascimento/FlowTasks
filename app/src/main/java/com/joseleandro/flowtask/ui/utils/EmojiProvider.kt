package com.joseleandro.flowtask.ui.utils

enum class EmojiCategory {
    SMILEYS,
    OBJECTS,
    TRAVEL,
    FOOD,
    SYMBOLS
}

object EmojiProvider {

    val emojis = mapOf(

        EmojiCategory.SMILEYS to listOf(
            "😀","😁","😂","🤣","😃","😄","😅","😆",
            "😉","😊","🙂","😍","😘","😎","🤓"
        ),

        EmojiCategory.OBJECTS to listOf(
            "📱","💻","⌚","📷","📚","📝","📌","📎"
        ),

        EmojiCategory.TRAVEL to listOf(
            "✈️","🚗","🚕","🚲","🚢","🚆","🏠","🏢"
        ),

        EmojiCategory.FOOD to listOf(
            "🍎","🍔","🍕","🍩","🍰","🍺","☕"
        ),

        EmojiCategory.SYMBOLS to listOf(
            "❤️","⭐","🔥","⚡","✔️","❗","❓"
        )
    )
}