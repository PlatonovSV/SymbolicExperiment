package ru.openunity.symbolicexperiment.ui

import ru.openunity.symbolicexperiment.model.Symbol

data class ExperimentUiState(
    val destination: String = ExperimentScreen.Start.name,
    val charIndex: Int = 0,
    val symbols: List<Symbol> = listOf(),
    val answer: String = "Empty"
)
