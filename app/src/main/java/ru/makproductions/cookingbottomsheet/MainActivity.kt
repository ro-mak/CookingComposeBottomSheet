package ru.makproductions.cookingbottomsheet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
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
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    LaunchedEffect(key1 = state.modalBottomSheetState, block = {
        if (state.modalBottomSheetState is MainModalBottomSheetState.NoBottomSheetContent) {
            modalBottomSheetState.hide()
        } else {
            modalBottomSheetState.show()
        }
    })
    LaunchedEffect(key1 = state.bottomSheetContent, block = {
        if (state.bottomSheetContent is MainBottomSheetContentState.NoBottomSheetContent) {
            bottomSheetScaffoldState.bottomSheetState.collapse()
        } else {
            bottomSheetScaffoldState.bottomSheetState.expand()
        }
    })
    val viewModel = viewModel(MainViewModel::class.java)
    ModalBottomSheetLayout(
        sheetContent = {
            MainModalBottomSheetContent(state)
        }, sheetState = modalBottomSheetState
    ) {
        BottomSheetScaffold(
            backgroundColor = Color.White,
            sheetBackgroundColor = colorResource(id = R.color.purple_500),
            sheetElevation = 0.dp,
            topBar = {
                TopAppBar {
                    IconButton(onClick = {
                        viewModel.onMoreClick()
                    }) {
                        Image(painterResource(id = R.drawable.ic_baseline_more_vert_24), "")
                    }
                }
            },
            scaffoldState = bottomSheetScaffoldState,
            sheetGesturesEnabled = false,
            sheetContent = {
                MainBottomSheetContent(state)
            },
            modifier = Modifier.fillMaxSize(),
        ) {
            Column {
                Greeting("Bottomsheet cookers")
                Button(onClick = {
                    viewModel.onChangeBottomSheetStateClick()
                }) {
                    Text("Change bottomSheet state")
                }
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
fun MainModalBottomSheetContent(state: MainState) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 1.dp)
    ) {
        when (state.modalBottomSheetState) {
            MainModalBottomSheetState.NoBottomSheetContent -> {}
            MainModalBottomSheetState.ButtonBottomSheetContent -> ButtonContent(
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
            MainModalBottomSheetState.TextInputBottomSheetContent -> TextInputContent(
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
        }
    }
}

@Composable
fun TextInputContent(modifier: Modifier) {
    TextField(
        value = "",
        onValueChange = {},
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Gray, RoundedCornerShape(16.dp))

    )
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