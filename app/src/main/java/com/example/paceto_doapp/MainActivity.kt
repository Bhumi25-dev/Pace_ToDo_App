package com.example.paceto_doapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.paceto_doapp.ui.presentation.navigation.AppNavGraph
import com.example.paceto_doapp.ui.theme.PaceToDoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PaceToDoAppTheme {
                AppNavGraph()
            }
        }
    }
}