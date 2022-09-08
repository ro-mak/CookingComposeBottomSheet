package ru.makproductions.cookingbottomsheet

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel : ViewModel() {

    val state: MutableStateFlow<MainState> =
        MutableStateFlow(MainState(MainBottomSheetContentState.NoBottomSheetContent))


}