package com.hafihaf.calcul_ovator

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object CommandScreen : Screen("command_screen")
}
