package ru.makproductions.cookingbottomsheet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.makproductions.cookingbottomsheet.ui.theme.CookingComposeBottomSheetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel = viewModel(MainViewModel::class.java)
            CookingComposeBottomSheetTheme {
                MainContent(viewModel.state.collectAsState().value)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainContent(state: MainState) {
    // A surface container using the 'background' color from the theme
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    LaunchedEffect(key1 = state.bottomSheetContent, block = {
        if (state.bottomSheetContent is MainBottomSheetContentState.NoBottomSheetContent) {
            bottomSheetScaffoldState.bottomSheetState.collapse()
        } else {
            bottomSheetScaffoldState.bottomSheetState.expand()
        }
    })
    BottomSheetScaffold(
        backgroundColor = Color.LightGray,
        sheetElevation = 0.dp,
        scaffoldState = bottomSheetScaffoldState,
        sheetGesturesEnabled = false,
        sheetContent = {
            MainBottomSheetContent(state)
        },
        modifier = Modifier.fillMaxSize(),
    ) {
        Greeting("Bottomsheet cookers")
    }

}

@Composable
fun MainBottomSheetContent(state: MainState) {
    when (state.bottomSheetContent) {
        MainBottomSheetContentState.NoBottomSheetContent -> {}
        MainBottomSheetContentState.ButtonBottomSheetContent -> ButtonContent()
        MainBottomSheetContentState.TextInputBottomSheetContent -> TextInputContent()
    }
}

@Composable
fun TextInputContent() {
    TextField(value = "", onValueChange = {})
}

@Composable
fun ButtonContent() {
    Button({}) {
        Text(text = "Click me")
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CookingComposeBottomSheetTheme {
        Greeting("Android")
    }
}