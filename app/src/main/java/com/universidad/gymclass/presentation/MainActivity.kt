package com.universidad.gymclass.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.universidad.gymclass.presentation.navigation.GymClassNavigation
import com.universidad.gymclass.presentation.theme.GymClassTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GymClassTheme {
                GymClassNavigation()
            }
        }
    }
}