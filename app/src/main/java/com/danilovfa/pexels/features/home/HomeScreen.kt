package com.danilovfa.pexels.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.danilovfa.pexels.ui.view.toolbar.Search
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@Composable
@RootNavGraph(start = true)
@Destination
fun HomeScreen() {
    HomeLayout()
}

@Composable
private fun HomeLayout() {
    Column(
        modifier = Modifier
            .systemBarsPadding()
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
    ) {
        var searchValue by remember { mutableStateOf("") }
        Search(
            value = searchValue,
            onValueChange = { newValue ->
                searchValue = newValue
            },
            onValueResetClick = { searchValue = "" },
            placeholderText = "Search",
        )
    }
}