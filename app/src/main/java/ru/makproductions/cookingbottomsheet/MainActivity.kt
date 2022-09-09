package ru.makproductions.cookingbottomsheet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
        backgroundColor = Color.White,
        sheetBackgroundColor = Color.Cyan,
        sheetElevation = 0.dp,
        scaffoldState = bottomSheetScaffoldState,
        sheetGesturesEnabled = false,
        sheetContent = {
            MainBottomSheetContent(state)
        },
        modifier = Modifier.fillMaxSize(),
    ) {
        Column {
            Greeting("Bottomsheet cookers")
            val viewModel = viewModel(MainViewModel::class.java)
            Button(onClick = {
                viewModel.onChangeBottomSheetStateClick()
            }) {
                Text("Change bottomSheet state")
            }
        }
    }

}

@Composable
fun MainBottomSheetContent(state: MainState) {
    Box(modifier = Modifier.fillMaxWidth()) {
        when (state.bottomSheetContent) {
            MainBottomSheetContentState.NoBottomSheetContent -> {}
            MainBottomSheetContentState.ButtonBottomSheetContent -> ButtonContent(
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
            MainBottomSheetContentState.TextInputBottomSheetContent -> TextInputContent(
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
        }
    }
}

@Composable
fun TextInputContent(modifier: Modifier) {
    TextField(value = "", onValueChange = {}, modifier = modifier.fillMaxWidth())
}

@Composable
fun ButtonContent(modifier: Modifier) {
    Button(modifier = modifier, onClick = {}) {
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