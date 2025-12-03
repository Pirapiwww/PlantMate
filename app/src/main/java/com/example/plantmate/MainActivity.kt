package com.example.plantmate

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.rememberNavController
import com.example.plantmate.ui.theme.PlantMateTheme

import com.example.plantmate.ui.navigation.NavbarApp

class MainActivity : ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Supaya bottom Navbar (tab, home, back) bawaan hp disembunyikan
        WindowCompat.setDecorFitsSystemWindows(window, false)

        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            // Hide ONLY navigation bar
            controller.hide(WindowInsetsCompat.Type.navigationBars())

            // Allow swipe from bottom to show it temporarily
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        setContent {
            PlantMateTheme {

                val navController = rememberNavController()
                NavbarApp(navController)

            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PlantJournalScreenPreview() {
    PlantMateTheme {

    }
}

