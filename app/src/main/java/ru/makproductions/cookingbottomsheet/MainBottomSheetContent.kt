package ru.makproductions.cookingbottomsheet

sealed class MainBottomSheetContent {

    object NoBottomSheetContent : MainBottomSheetContent()
    object ButtonBottomSheetContent : MainBottomSheetContent()
    object TextInputBottomSheetContent : MainBottomSheetContent()
}