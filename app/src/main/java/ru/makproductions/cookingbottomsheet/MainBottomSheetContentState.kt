package ru.makproductions.cookingbottomsheet

sealed class MainBottomSheetContentState {

    object NoBottomSheetContent : MainBottomSheetContentState()
    object ButtonBottomSheetContent : MainBottomSheetContentState()
    object TextInputBottomSheetContent : MainBottomSheetContentState()
}