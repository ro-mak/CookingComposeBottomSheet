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

    fun onMoreClick() {
        when (state.value.modalBottomSheetState) {
            MainModalBottomSheetState.NoBottomSheetContent -> state.value =
                state.value.copy(modalBottomSheetState = MainModalBottomSheetState.ButtonBottomSheetContent)
            MainModalBottomSheetState.ButtonBottomSheetContent -> state.value =
                state.value.copy(modalBottomSheetState = MainModalBottomSheetState.TextInputBottomSheetContent)
            MainModalBottomSheetState.TextInputBottomSheetContent -> state.value =
                state.value.copy(modalBottomSheetState = MainModalBottomSheetState.NoBottomSheetContent)
        }
    }

    fun onOpenDialogBottomSheetClick() {
        events.tryEmit(OpenBottomSheetDialogEvent())
    }

    val events: MutableStateFlow<MainEvent> = MutableStateFlow(NoEvent())
    val state: MutableStateFlow<MainState> =
        MutableStateFlow(
            MainState(
                MainBottomSheetContentState.NoBottomSheetContent,
                MainModalBottomSheetState.NoBottomSheetContent
            )
        )


}