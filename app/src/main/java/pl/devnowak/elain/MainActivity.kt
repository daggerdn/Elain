package pl.devnowak.elain

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import pl.devnowak.elain.screens.SharedViewModel
import pl.devnowak.elain.ui.ShelterDetailsScreen
import pl.devnowak.elain.ui.ShelterListScreen
import pl.devnowak.elain.ui.theme.ElainTheme
import timber.log.Timber

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            ElainTheme {
                val navController = rememberNavController()
                val sharedViewModel: SharedViewModel = viewModel()

                val items = listOf(
                    Routes.List,
                    Routes.Details
                )
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavContainer(navController = navController, sharedViewModel = sharedViewModel)
                }
                Scaffold(bottomBar = {
                    BottomNavigation {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        items.forEach { route ->
                            BottomNavigationItem(
                                icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                                label = { Text(route.routeSchema) },
                                selected = currentDestination?.hierarchy?.any { it.route == route.routeSchema } == true,
                                onClick = {
                                    navController.navigate(route.routeSchema) {
                                        // Pop up to the start destination of the graph to
                                        // avoid building up a large stack of destinations
                                        // on the back stack as users select items
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        // Avoid multiple copies of the same destination when
                                        // reselecting the same item
                                        launchSingleTop = true
                                        // Restore state when reselecting a previously selected item
                                        restoreState = true
                                    }
                                }
                            )
                    }
                    }
                }) { innerPadding ->
                    NavHost(navController, startDestination = Routes.List.routeSchema, Modifier.padding(innerPadding)) {
                        composable(Routes.List.routeSchema) {
                            ShelterListScreen(
                                navController = navController,
                                navigateToDetails = {
                                    Timber.d("Inside lambda")
                                    navController.navigate("details") },
                                sharedViewModel = sharedViewModel
                            )
                        }
                        composable(Routes.Details.routeSchema) {
                            ShelterDetailsScreen(
                                navController = navController,
                                sharedViewModel = sharedViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NavContainer(navController: NavHostController, sharedViewModel: SharedViewModel) {



    NavHost(navController = navController, startDestination = Routes.List.routeSchema) {
        composable(route = Routes.List.routeSchema) {

            ShelterListScreen(
                navController = navController,
                navigateToDetails = {
                    Timber.d("Inside lambda")
                    navController.navigate("details") },
                sharedViewModel = sharedViewModel
            )
        }

        composable(route = Routes.Details.routeSchema) {

            ShelterDetailsScreen(
                navController = navController,
                sharedViewModel = sharedViewModel
            )
        }
    }
}

sealed class Routes(val routeSchema: String) {
    object List : Routes("list")
    object Details : Routes("details") { // details/{$NAV_ARGUMENT_ID}
        fun create(id: String) = routeSchema.replace("{$NAV_ARGUMENT_ID}", id)
    }

    companion object {
        const val NAV_ARGUMENT_ID = "id"
    }
}