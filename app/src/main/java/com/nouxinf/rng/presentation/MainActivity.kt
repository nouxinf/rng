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
                        }
                    )
                }

                composable("coin_flip") {
                    CoinFlipScreen(
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
    onCoinFlipClick: () -> Unit
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
                ) {
                    Text("Coin flip")
                }
            }
        }
    }
}
@Composable
fun CoinFlipScreen(onBack: () -> Unit) {
    val listState = rememberTransformingLazyColumnState()
    val transformationSpec = rememberTransformationSpec()

    var message by remember { mutableStateOf("Waiting..")}
    var coinIcon by remember {  mutableStateOf(R.drawable.questionmarkcoin)}
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
                    Text("coin flip screen")
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



@WearPreviewDevices
@WearPreviewFontScales
@Composable
fun DefaultPreview() {
    WearApp("Preview Android")
}