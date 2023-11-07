package com.danilovfa.pexels.ui.composables.bottomnavigation

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.danilovfa.pexels.ui.screen.NavGraphs
import com.danilovfa.pexels.ui.screen.appCurrentDestinationAsState
import com.danilovfa.pexels.ui.screen.destinations.Destination
import com.danilovfa.pexels.ui.screen.startAppDestination

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

