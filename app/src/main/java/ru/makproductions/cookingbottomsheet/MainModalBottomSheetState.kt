package ru.makproductions.cookingbottomsheet

sealed class MainModalBottomSheetState {

    object NoBottomSheetContent : MainModalBottomSheetState()
    object ButtonBottomSheetContent : MainModalBottomSheetState()
    object TextInputBottomSheetContent : MainModalBottomSheetState()
}