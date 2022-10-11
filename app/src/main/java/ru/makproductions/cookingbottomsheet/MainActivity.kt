package ru.makproductions.cookingbottomsheet

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ModalBottomSheetValue.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.makproductions.cookingbottomsheet.ui.theme.CookingComposeBottomSheetTheme

private val Float.pxToDp: Dp
    get() = (this / Resources.getSystem().displayMetrics.density).dp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val viewModelOldStyle: MainViewModel by viewModels()
        viewModelOldStyle.viewModelScope.launch {
            viewModelOldStyle.events.collect {
                when (it) {
                    is NoEvent -> {}
                    is OpenBottomSheetDialogEvent -> {
                        Log.d("Open", "BottomSheet")
                        MainBottomSheetDialogFragment().show(supportFragmentManager, "tag")
                    }
                }
            }
        }
        setContent {

            val viewModel = viewModel(MainViewModel::class.java)
            WindowInsets.systemBars
            CookingComposeBottomSheetTheme {
                MainContent(viewModel.state.collectAsState().value)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)
@Composable
fun MainContent(state: MainState) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = Hidden)
    val configuration = LocalConfiguration.current
    val statusBarColorDefault = colorResource(id = R.color.purple_700)
    val systemUiController = rememberSystemUiController()
    LaunchedEffect(key1 = modalBottomSheetState.offset.value, block = {
        launch(Dispatchers.IO) {
            val statusBarColorScrimmed =
                Color(
                    ColorUtils.blendARGB(
                        statusBarColorDefault.toArgb(),
                        Color.Black.toArgb(),
                        0.2f
                    )
                )
            val currentColor =
                if (modalBottomSheetState.targetValue == Expanded || modalBottomSheetState.targetValue == HalfExpanded) statusBarColorScrimmed else statusBarColorDefault
            withContext(Dispatchers.Main) {
                systemUiController.setSystemBarsColor(currentColor)
            }
        }
    })

    val bottomSheetHeight =
        configuration.screenHeightDp.dp - bottomSheetScaffoldState.bottomSheetState.offset.value.pxToDp
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
        }, sheetState = modalBottomSheetState,
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
    ) {
        BottomSheetScaffold(
            backgroundColor = Color.White,
            sheetBackgroundColor = colorResource(id = R.color.purple_500),
            sheetElevation = 0.dp,
            sheetPeekHeight = 0.dp,
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
            sheetShape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Column {
                Greeting("Bottomsheet cookers")
                Button(onClick = {
                    viewModel.onChangeBottomSheetStateClick()
                }) {
                    Text("Change bottomSheet state")
                }
                Button(onClick = {
                    viewModel.onOpenDialogBottomSheetClick()
                }) {
                    Text("Open dialog bottom sheet")
                }
            }
        }
    }

}

@Composable
fun MainBottomSheetContent(state: MainState) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .imePadding()
    ) {
        Spacer(modifier = Modifier.height(1.dp))
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