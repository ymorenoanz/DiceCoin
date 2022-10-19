package com.yaritzama.dice_coin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.rotationMatrix
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yaritzama.MainViewContainerState
import com.yaritzama.dice_coin.ui.theme.Dice_CoinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Dice_CoinTheme {
                val vm : MainViewModel = viewModel()

                Column(modifier = Modifier.fillMaxSize()) {
                    Header(modifier = Modifier.fillMaxWidth(), vm = vm)
                    Body(modifier = Modifier.fillMaxSize(), vm)
                }
            }
        }
    }
}

@Composable
private fun Body(modifier: Modifier= Modifier, vm: MainViewModel) {

    val viewState = remember { vm.containerState }

    Box(modifier = modifier) {
        when(viewState.value){
            MainViewContainerState.ViewOne -> {
                Column(modifier = Modifier.align(Alignment.Center), verticalArrangement = Arrangement.Center) {
                   DicePreview()
                }
            }
            MainViewContainerState.ViewTwo -> {
                CoinPreview()
            }
        }
    }
}

@Composable
private fun Header(modifier: Modifier= Modifier, vm: MainViewModel) {

    val viewState = remember { vm.containerState }

    Row(modifier = modifier) {
        Tabs(
            title = "Roll a dice", modifier = Modifier
                .weight(1f)
                .background(Color.DarkGray) , isSelected = viewState.value == MainViewContainerState.ViewOne
        ) {
            vm.switchViews(MainViewContainerState.ViewOne)
        }
        Tabs(
            title = "Flip a coin", modifier = Modifier
                .weight(1f)
                .background(Color.DarkGray) , isSelected = viewState.value == MainViewContainerState.ViewTwo
        ) {
            vm.switchViews(MainViewContainerState.ViewTwo)
        }
    }
}

@Composable
fun Tabs(modifier: Modifier = Modifier, title: String, isSelected : Boolean, onClick : () -> Unit) {

    Box(modifier = modifier
        .clickable {
            onClick.invoke()
        }
        .background(if (isSelected) Color.Green else Color.Blue)
        .height(60.dp)) {
        Text(text = title, modifier = Modifier.align(Alignment.Center), color = Color.White)
    }
}


@Composable
fun DiceWithButtonAndImage( modifier : Modifier = Modifier)
{
    var result by remember { mutableStateOf(1) }
    val imageResource = when(result){
        1-> R.drawable.dice_1
        2-> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }
    Column(modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Image(painterResource(imageResource), contentDescription = result.toString())
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { result = (1..6).random()}) {
            Text(stringResource(R.string.roll))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DicePreview() {
    Dice_CoinTheme {
        DiceWithButtonAndImage(modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center))
    }
}


@Composable
fun FlipCoin(modifier : Modifier = Modifier){
    var resultCoin by remember { mutableStateOf(1) }

    val imageResource = when(resultCoin){
        1-> R.drawable.coin_head
        else -> R.drawable.coin_tail
    }

    //Animation for rotation
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec =
        infiniteRepeatable(tween(durationMillis = 1000,
            delayMillis = 1000, easing = LinearEasing),
            RepeatMode.Restart))


    Column(modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Image(painterResource(imageResource), contentDescription = resultCoin.toString(),
            modifier = Modifier
                .height(180.dp)
                .width(180.dp)
                //.rotate(angle)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { resultCoin = (1..2).random()}) {
            Text(stringResource(R.string.flip))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CoinPreview(){
    Dice_CoinTheme {
        FlipCoin(modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center))
    }
}