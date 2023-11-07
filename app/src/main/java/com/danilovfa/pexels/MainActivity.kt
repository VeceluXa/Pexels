package com.danilovfa.pexels

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.danilovfa.pexels.ui.screen.root.RootScreen
import com.danilovfa.pexels.ui.theme.PexelsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().run {

        }

        setContent {
            PexelsTheme {
                RootScreen()
            }
        }
    }
}