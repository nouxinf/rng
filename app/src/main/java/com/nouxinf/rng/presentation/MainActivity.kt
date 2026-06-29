package com.nouxinf.rng.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.wear.compose.foundation.lazy.TransformingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberTransformingLazyColumnState
import androidx.wear.compose.material3.AppScaffold
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.EdgeButton
import androidx.wear.compose.material3.ListHeader
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.material3.SurfaceTransformation
import androidx.wear.compose.material3.Text
import androidx.wear.compose.material3.lazy.rememberTransformationSpec
import androidx.wear.compose.material3.lazy.transformedHeight
import androidx.wear.compose.ui.tooling.preview.WearPreviewDevices
import androidx.wear.compose.ui.tooling.preview.WearPreviewFontScales
import com.nouxinf.rng.R
import com.nouxinf.rng.presentation.theme.RNGTheme
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.wear.compose.material3.*
import androidx.compose.foundation.Image
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.rememberPickerState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearApp()
        }
    }
}

@Composable
fun WearApp() {
    val navController = rememberSwipeDismissableNavController()

    RNGTheme {
        AppScaffold {
            SwipeDismissableNavHost(
                navController = navController,
                startDestination = "home"
            ) {
                composable("home") {
                    HomeScreen(
                        onCoinFlipClick = {
                            navController.navigate("coin_flip")
                        },
                        onDiceRollClick = {
                            navController.navigate("dice_roll")
                        },
                        onCustomRangeClick = {
                            navController.navigate("custom_range")
                        },
                        onAttributionClick = {
                            navController.navigate("attribution")
                        }
                    )
                }

                composable("coin_flip") {
                    CoinFlipScreen(
                        onBack = { navController.popBackStack() }
                    )
                }
                composable("dice_roll") {
                    DiceRollScreen(
                        onBack = { navController.popBackStack() }
                    )
                }
                composable("custom_range") {
                    CustomRangeScreen(
                        onBack = { navController.popBackStack() }
                    )
                }
                composable("attribution") {
                    AttributionScreen(
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}
@Composable
fun HomeScreen(
    onCoinFlipClick: () -> Unit,
    onDiceRollClick: () -> Unit,
    onCustomRangeClick: () -> Unit,
    onAttributionClick: () -> Unit,
) {
    val listState = rememberTransformingLazyColumnState()
    val transformationSpec = rememberTransformationSpec()

    ScreenScaffold(scrollState = listState, edgeButton = {
        EdgeButton(
        onClick = onAttributionClick,
        buttonSize = EdgeButtonSize.Small
    ) {
        Text("Attribution")
    }}) { contentPadding ->
        TransformingLazyColumn(
            contentPadding = contentPadding,
            state = listState
        ) {
            item {
                ListHeader(
                    modifier = Modifier
                        .fillMaxWidth()
                        .transformedHeight(this, transformationSpec),
                    transformation = SurfaceTransformation(transformationSpec),
                ) {
                    Text("Welcome to RNG for WearOS!")
                }
            }

            item {
                Button(
                    onClick = onCoinFlipClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .transformedHeight(this, transformationSpec),
                    transformation = SurfaceTransformation(transformationSpec),
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.heads),
                            contentDescription = "Coin Flip",
                        )
                    }
                ) {
                    Text("Coin Flip")
                }
            }
            item {
                Button(
                    onClick = onDiceRollClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .transformedHeight(this, transformationSpec),
                    transformation = SurfaceTransformation(transformationSpec),
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.dice6),
                            contentDescription = "Dice Roll",
                        )
                    }
                ) {
                    Text("Dice Roll")
                }
            }
            item {
                Button(
                    onClick = onCustomRangeClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .transformedHeight(this, transformationSpec),
                    transformation = SurfaceTransformation(transformationSpec),
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.random),
                            contentDescription = "Custom Range RNG",
                        )
                    }
                ) {
                    Text("Custom Range")
                }
            }
        }
    }
}
@Composable
fun CoinFlipScreen(onBack: () -> Unit) {
    val listState = rememberTransformingLazyColumnState()
    val transformationSpec = rememberTransformationSpec()
    val haptic = LocalHapticFeedback.current
    var message by remember { mutableStateOf("Waiting..")}
    var coinIcon by remember {  mutableIntStateOf(R.drawable.questionmarkcoin)}
    fun flipCoin() {
        val coin = (0..1).random()
        message = if (coin == 0) {
            "Heads!"
        } else {
            "Tails!"
        }
        coinIcon = if (coin == 0) {
            R.drawable.heads
        } else {
            R.drawable.tails
        }
        haptic.performHapticFeedback(HapticFeedbackType.Confirm)

    }

    ScreenScaffold { contentPadding ->
        TransformingLazyColumn(
            contentPadding = contentPadding,
            state = listState
        ) {

            item {
                ListHeader(
                    modifier = Modifier
                        .fillMaxWidth()
                        .transformedHeight(this, transformationSpec),
                    transformation = SurfaceTransformation(transformationSpec),
                ) {
                    Text("Coin Flip")
                }
            }
            item {
                Button(
                onClick = { flipCoin()},
                shape = androidx.compose.foundation.shape.CircleShape
            ) {
                Text("Flip!")
            }}
            item {
                Text(message)
            }
            item {
                Image(
                    painter = painterResource(id = coinIcon),
                    contentDescription = "Heads"
                )
            }

        }
    }
}
@Composable
fun DiceRollScreen(onBack: () -> Unit) {
    val listState = rememberTransformingLazyColumnState()
    val transformationSpec = rememberTransformationSpec()
    val haptic = LocalHapticFeedback.current
    var diceNumber by remember { mutableStateOf("Waiting")}
    var diceIcon by remember { mutableIntStateOf(R.drawable.dicequestionmark)}

    fun rollDice() {
        val random = (1..6).random()
        diceNumber = random.toString()
        haptic.performHapticFeedback(HapticFeedbackType.Confirm)
        when (random) {
            1 -> diceIcon = R.drawable.dice1
            2 -> diceIcon = R.drawable.dice2
            3 -> diceIcon = R.drawable.dice3
            4 -> diceIcon = R.drawable.dice4
            5 -> diceIcon = R.drawable.dice5
            6 -> diceIcon = R.drawable.dice6
        }
    }

    ScreenScaffold { contentPadding ->
        TransformingLazyColumn(
            contentPadding = contentPadding,
            state = listState
        ) {

            item {
                ListHeader(
                    modifier = Modifier
                        .fillMaxWidth()
                        .transformedHeight(this, transformationSpec),
                    transformation = SurfaceTransformation(transformationSpec),
                ) {
                    Text("Dice Roll")
                }
            }
            item {
                Button(
                    onClick = { rollDice() },
                    shape = androidx.compose.foundation.shape.CircleShape
                ) {
                    Text("Roll!")
                }}
            item {
                Text(diceNumber)
            }
            item {
                Image(
                    painter = painterResource(id = diceIcon),
                    contentDescription = "Dice Icon"
                )
            }

        }
    }
}
@Composable
fun CustomRangeScreen(onBack: () -> Unit) {
    val numbersRange = (0..100).toList()
    var selectedPickerIndex by remember { mutableIntStateOf(0) }
    val haptic = LocalHapticFeedback.current
    var generatedValue by remember { mutableStateOf("Waiting...")}
    val firstPickerState = rememberPickerState(
        initialNumberOfOptions = numbersRange.size
    )
    val secondPickerState = rememberPickerState(
        initialNumberOfOptions = numbersRange.size
    )
    LaunchedEffect(Unit) {
        secondPickerState.scrollToOption(100)
    }

    fun generateNumber() {
        haptic.performHapticFeedback(HapticFeedbackType.Confirm)
        val firstNumber = numbersRange[firstPickerState.selectedOptionIndex]
        val secondNumber = numbersRange[secondPickerState.selectedOptionIndex]
        var randomNum = 0
        // to prevent the app from crashing when the first number is larger than the second number we check
        randomNum = if (firstNumber <= secondNumber) {
            (numbersRange[firstPickerState.selectedOptionIndex]..numbersRange[secondPickerState.selectedOptionIndex]).random()
        } else {
            (numbersRange[secondPickerState.selectedOptionIndex]..numbersRange[firstPickerState.selectedOptionIndex]).random()
        }

        generatedValue = randomNum.toString()
    }

    ScreenScaffold { contentPadding ->
        Column(
            modifier = Modifier.fillMaxWidth().padding(top = 18.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("Custom Range")
            PickerGroup(
                selectedPickerState =
                    if (selectedPickerIndex == 0) firstPickerState else secondPickerState,
                autoCenter = false,
            ) {
                PickerGroupItem(
                    pickerState = firstPickerState,
                    selected = selectedPickerIndex == 0,
                    onSelected = { selectedPickerIndex = 0 },
                    option = { optionIndex, _ -> Text(text = optionIndex.toString()) },
                    contentDescription = { "First number" },
                    modifier = Modifier.size(80.dp, 100.dp),
                )

                PickerGroupItem(
                    pickerState = secondPickerState,
                    selected = selectedPickerIndex == 1,
                    onSelected = { selectedPickerIndex = 1 },
                    option = { optionIndex, _ -> Text(text = optionIndex.toString()) },
                    contentDescription = { "Second Number" },
                    modifier = Modifier.size(80.dp, 100.dp),
                )
            }

            // Text("Selected: ${numbersRange[firstPickerState.selectedOptionIndex]}")

            Button(onClick = { generateNumber() }) {
                Text("Roll!")
            }
            Text("Number: $generatedValue")
        }
    }
}
@Composable
fun AttributionScreen(onBack: () -> Unit) {
    val listState = rememberTransformingLazyColumnState()
    // val transformationSpec = rememberTransformationSpec()

    ScreenScaffold { contentPadding ->
        TransformingLazyColumn(
            contentPadding = contentPadding,
            state = listState
        ) {
            item {
                Text(
                    text = "Font Awesome Free icons by Fonticons, Inc. (https://fontawesome.com), licensed under CC BY 4.0 (https://creativecommons.org/licenses/by/4.0/). Icons have been modified by converting them to Android Vector Drawable XML format and inverting their colors.",
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 16.dp)
                )
            }


        }
    }
}

@WearPreviewDevices
@WearPreviewFontScales
@Composable
fun DefaultPreview() {
    WearApp()
}