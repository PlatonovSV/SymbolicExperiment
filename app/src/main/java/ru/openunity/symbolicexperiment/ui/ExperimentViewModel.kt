package ru.openunity.symbolicexperiment.ui

import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.openunity.symbolicexperiment.model.Symbol

class ExperimentViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ExperimentUiState())
    val uiState = _uiState.asStateFlow()

    fun startFirstExperiment() {
        val listSize = 6
        _uiState.update { experimentUiState ->
            val allChars: MutableList<String> = mutableListOf(
                "0",
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9"
            )
            allChars.shuffle()
            val sizes: MutableList<Boolean> = mutableListOf(
                true,
                false,
                true,
                false,
                true,
                false
            )
            sizes.shuffle()
            val list: MutableList<Symbol> = mutableListOf()
            repeat(listSize) {
                list.add(Symbol(allChars[it], run { if (sizes[it]) 200.sp else 75.sp }))
            }
            experimentUiState.copy(
                charIndex = 0,
                destination = ExperimentScreen.First.name,
                symbols = list,
                answer = list.toString()
            )
        }
    }

    fun startSecondExperiment() {
        val listSize = 6
        _uiState.update { experimentUiState ->
            val allChars: MutableList<String> = mutableListOf(
                "❤️",
                "\uD83D\uDC4D",
                "\uD83D\uDE2D",
                "\uD83D\uDE21",
                "\uD83E\uDD37\u200D♂️",
                "\uD83D\uDC4C",
                "\uD83E\uDD21",
                "\uD83E\uDD2F",
                "\uD83E\uDEE1",
                "\uD83D\uDC4A",
            )
            allChars.shuffle()
            val sizes: MutableList<Boolean> = mutableListOf(
                true,
                false,
                true,
                false,
                true,
                false
            )
            sizes.shuffle()
            val list: MutableList<Symbol> = mutableListOf()
            repeat(listSize) {
                list.add(Symbol(allChars[it], run { if (sizes[it]) 200.sp else 75.sp }))
            }
            experimentUiState.copy(
                charIndex = 0,
                destination = ExperimentScreen.Second.name,
                symbols = list,
                answer = list.toString()
            )
        }
    }

    fun startThirdExperiment() {
        val listSize = 10
        _uiState.update { experimentUiState ->
            val allPict: MutableList<String> = mutableListOf(
                "❤️",
                "\uD83D\uDC4D",
                "\uD83D\uDE2D",
                "\uD83D\uDE21",
                "\uD83E\uDD37\u200D♂️",
                "\uD83D\uDC4C",
                "\uD83E\uDD21",
                "\uD83E\uDD2F",
                "\uD83E\uDEE1",
                "\uD83D\uDC4A",
            )
            allPict.shuffle()
            val allNum: MutableList<String> = mutableListOf(
                "0",
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9"
            )
            allNum.shuffle()
            val choose: MutableList<Boolean> = mutableListOf(
                true,
                false,
                true,
                false,
                true,
                false,
                true,
                false,
                true,
                false
            )
            choose.shuffle()
            val list: MutableList<Symbol> = mutableListOf()
            repeat(listSize) {
                list.add(Symbol(run { if (choose[it]) allPict[it] else allNum[it]}, 150.sp))
            }
            experimentUiState.copy(
                charIndex = 0,
                destination = ExperimentScreen.Third.name,
                symbols = list,
                answer = list.toString()
            )
        }
    }

    fun changeImage() {
        _uiState.update { experimentUiState ->
            experimentUiState.copy(
                charIndex = if (experimentUiState.charIndex == experimentUiState.symbols.lastIndex) {
                    experimentUiState.charIndex
                } else {
                    experimentUiState.charIndex.inc()
                }
            )
        }
    }

    fun toStart() {
        _uiState.update { experimentUiState ->
            experimentUiState.copy(
                destination = ExperimentScreen.Start.name
            )
        }
    }

    fun showAnswer() {
        _uiState.update { experimentUiState ->
            experimentUiState.copy(
                destination = ExperimentScreen.Answer.name
            )
        }
    }
}