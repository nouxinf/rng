/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter to find the
 * most up to date changes to the libraries and their usages.
 */

package com.nouxinf.rng.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.wear.compose.foundation.lazy.TransformingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberTransformingLazyColumnState
import androidx.wear.compose.material3.AppScaffold
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.ButtonDefaults
import androidx.wear.compose.material3.EdgeButton
import androidx.wear.compose.material3.ListHeader
import androidx.wear.compose.material3.MaterialTheme
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
import androidx.core.view.HapticFeedbackConstantsCompat
import androidx.compose.material.icons.Icons
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.Picker
import androidx.wear.compose.material3.rememberPickerState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Check
import androidx.compose.foundation.layout.size
import androidx.wear.compose.material3.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearApp("Android")
        }
    }
}

@Composable
fun WearApp(greetingName: String) {
    val navController = rememberSwipeDismissableNavController()

    RNGTheme {
        AppScaffold {
            SwipeDismissableNavHost(
                navController = navController,
                startDestination = "home"
            ) {
                composable("home") {
                    HomeScreen(
                        greetingName = greetingName,
                        onCoinFlipClick = {
                            navController.navigate("coin_flip")
                        },
                        onDiceRollClick = {
                            navController.navigate("dice_roll")
                        },
                        onCustomRangeClick = {
                            navController.navigate("custom_range")
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
            }
        }
    }
}
@Composable
fun HomeScreen(
    greetingName: String,
    onCoinFlipClick: () -> Unit,
    onDiceRollClick: () -> Unit,
    onCustomRangeClick: () -> Unit,
) {
    val listState = rememberTransformingLazyColumnState()
    val transformationSpec = rememberTransformationSpec()

    ScreenScaffold(scrollState = listState) { contentPadding ->
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
        val randomNum = (numbersRange[firstPickerState.selectedOptionIndex]..numbersRange[secondPickerState.selectedOptionIndex]).random()
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


@WearPreviewDevices
@WearPreviewFontScales
@Composable
fun DefaultPreview() {
    WearApp("Preview Android")
}