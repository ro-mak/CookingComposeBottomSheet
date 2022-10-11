package ru.makproductions.cookingbottomsheet

import java.util.*

sealed class MainEvent

data class NoEvent(val id: String = UUID.randomUUID().toString()) : MainEvent()

data class OpenBottomSheetDialogEvent(val id: String = UUID.randomUUID().toString()) : MainEvent()