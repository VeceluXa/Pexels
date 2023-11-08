package com.danilovfa.pexels.ui.view.bottomnavigation

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.danilovfa.pexels.features.NavGraphs
import com.danilovfa.pexels.features.appCurrentDestinationAsState
import com.danilovfa.pexels.features.destinations.Destination
import com.danilovfa.pexels.features.startAppDestination


@Composable
fun BottomBar(
    navController: NavController,
) {
    val navigationTabs = navigationTabs(LocalContext.current)

    val currentDestination: Destination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination

    BottomNavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = Color.Transparent
    ) {
        navigationTabs.forEach { navigationTab ->
            BottomNavigationTab(
                navController = navController,
                tab = navigationTab,
                destinations = navigationTabs,
                selected = currentDestination == navigationTab.direction,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}

