package com.example.reviewed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.reviewed.navigation.AppNavHost
import com.example.reviewed.ui.theme.ReviewedTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Optional: Makes content extend under system bars
        enableEdgeToEdge()

        setContent {
             ReviewedTheme{
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Create the NavController
                    val navController = rememberNavController()

                    // Start AppNavHost, which will show SplashScreen first
                    AppNavHost(navController = navController)
                }
            }
        }
    }
}
