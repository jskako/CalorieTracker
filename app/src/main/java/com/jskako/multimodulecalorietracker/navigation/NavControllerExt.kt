package com.jskako.multimodulecalorietracker.navigation

import androidx.navigation.NavController
import com.jskako.core.util.UiEvent

fun NavController.navigate(event: UiEvent.Navigate) {
    this.navigate(event.route)
}