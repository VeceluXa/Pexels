package com.danilovfa.pexels.presentation.screen.details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(navArgsDelegate = DetailsNavArgs::class)
fun DetailsScreen(
    destinationsNavigator: DestinationsNavigator,
    detailsNavArgs: DetailsNavArgs,
) {
    Text(text = detailsNavArgs.photo.authorName)
}