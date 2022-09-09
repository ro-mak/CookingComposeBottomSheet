package ru.makproductions.cookingbottomsheet

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel : ViewModel() {

    fun onChangeBottomSheetStateClick() {
        when (state.value.bottomSheetContent) {
            MainBottomSheetContentState.NoBottomSheetContent -> state.value =
                state.value.copy(bottomSheetContent = MainBottomSheetContentState.ButtonBottomSheetContent)
            MainBottomSheetContentState.ButtonBottomSheetContent -> state.value =
                state.value.copy(bottomSheetContent = MainBottomSheetContentState.TextInputBottomSheetContent)
            MainBottomSheetContentState.TextInputBottomSheetContent -> state.value =
                state.value.copy(bottomSheetContent = MainBottomSheetContentState.NoBottomSheetContent)
        }
    }

    val state: MutableStateFlow<MainState> =
        MutableStateFlow(MainState(MainBottomSheetContentState.NoBottomSheetContent))


}