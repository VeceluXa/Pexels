package com.danilovfa.pexels.presentation.screen.root

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.danilovfa.pexels.presentation.common.theme.PexelsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSplashScreen()

        setContent {
            PexelsTheme {
                RootScreen()
            }
        }
    }

    private fun setupSplashScreen() {
        var keepSplashScreen = true

        lifecycleScope.launch {
            viewModel.isLoading.collectLatest { isLoading ->
                keepSplashScreen = isLoading
            }
        }

        installSplashScreen().apply {
            setKeepOnScreenCondition { keepSplashScreen }
        }
    }
}
