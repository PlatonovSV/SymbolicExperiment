package ru.openunity.symbolicexperiment.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive


enum class ExperimentScreen {
    Start,
    First,
    Second,
    Third,
    Answer
}

@Composable
fun SymbolicExperimentApp(experimentViewModel: ExperimentViewModel = viewModel()) {
    val experimentUiState by experimentViewModel.uiState.collectAsState()
    when (experimentUiState.destination) {
        ExperimentScreen.Start.name -> {
            StartScreen(
                startFirst = { experimentViewModel.startFirstExperiment() },
                startSecond = { experimentViewModel.startSecondExperiment() },
                startThird = { experimentViewModel.startThirdExperiment() },
                showAnswer = { experimentViewModel.showAnswer() },
                modifier = Modifier
            )
        }

        ExperimentScreen.First.name -> {
            Experiment(
                symbol = experimentUiState.symbols[experimentUiState.charIndex].char,
                fontSize = experimentUiState.symbols[experimentUiState.charIndex].fontSize,
                changeImage = { experimentViewModel.changeImage() },
                finalAction = { experimentViewModel.toStart() },
                step = 500L,
                count = experimentUiState.symbols.size,
                modifier = Modifier
            )
        }

        ExperimentScreen.Second.name -> Experiment(
            symbol = experimentUiState.symbols[experimentUiState.charIndex].char,
            fontSize = experimentUiState.symbols[experimentUiState.charIndex].fontSize,
            changeImage = { experimentViewModel.changeImage() },
            finalAction = { experimentViewModel.toStart() },
            step = 500L,
            count = experimentUiState.symbols.size,
            modifier = Modifier
        )

        ExperimentScreen.Third.name -> Experiment(
            symbol = experimentUiState.symbols[experimentUiState.charIndex].char,
            fontSize = experimentUiState.symbols[experimentUiState.charIndex].fontSize,
            changeImage = { experimentViewModel.changeImage() },
            finalAction = { experimentViewModel.toStart() },
            step = 1000L,
            count = experimentUiState.symbols.size,
            modifier = Modifier
        )

        ExperimentScreen.Answer.name -> AnswersScreen(
            answer = experimentUiState.answer,
            toStart = { experimentViewModel.toStart() },
            modifier = Modifier
        )
    }
}

@Composable
fun AnswersScreen(
    answer: String,
    toStart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(60.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = answer,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.size(50.dp))
        Button(onClick = toStart) {
            Text(text = "Back")
        }

    }

}

@Composable
fun StartScreen(
    startFirst: () -> Unit,
    startSecond: () -> Unit,
    startThird: () -> Unit,
    showAnswer: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = startFirst) {
            Text(text = "Start First")
        }
        Button(onClick = startSecond) {
            Text(text = "Start Second")
        }
        Button(onClick = startThird) {
            Text(text = "Start Third")
        }
        Spacer(modifier = Modifier.size(150.dp))
        Button(onClick = showAnswer) {
            Text(text = "Show answer")
        }

    }
}

@Composable
fun RememberCountdownTimerState(
    action: () -> Unit,
    finalAction: () -> Unit,
    initialMillis: Long,
    step: Long
) {
    val timeLeft = remember { mutableLongStateOf(initialMillis) }

    LaunchedEffect(initialMillis, step) {
        while (isActive && timeLeft.longValue > 0) {
            val remainingTime = timeLeft.longValue - step.coerceAtMost(timeLeft.longValue)
            timeLeft.longValue = remainingTime
            delay(step.coerceAtMost(timeLeft.longValue))
            action()
        }
        delay(step)
        finalAction()
    }
}


@Composable
fun Experiment(
    changeImage: () -> Unit,
    finalAction: () -> Unit,
    symbol: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 150.sp,
    step: Long,
    count: Int
) {
    RememberCountdownTimerState(changeImage, finalAction, step = step, initialMillis = step * count)
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = symbol,
            fontSize = fontSize,
            modifier = Modifier
        )
    }
}



