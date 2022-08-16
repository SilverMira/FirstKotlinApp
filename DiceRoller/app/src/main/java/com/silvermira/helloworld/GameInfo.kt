package com.silvermira.helloworld

import androidx.databinding.ObservableField

data class GameInfo(
    val playerName: ObservableField<String> = ObservableField(),
    val score: ObservableField<Int> = ObservableField(),
    val counter: ObservableField<Int> = ObservableField(),
)