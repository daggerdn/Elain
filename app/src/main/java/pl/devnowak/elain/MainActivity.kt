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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pl.devnowak.elain.Routes.Companion.NAV_ARGUMENT_ID
import pl.devnowak.elain.app.details.ShelterDetailsScreen
import pl.devnowak.elain.screens.kennel.list.KennelListScreen
import pl.devnowak.elain.screens.shared.SharedViewModel
import pl.devnowak.elain.screens.shelter.list.ShelterListScreen
import pl.devnowak.elain.ui.theme.ElainTheme
import timber.log.Timber

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            ElainTheme {
                val navController = rememberNavController()
                val sharedViewModel: SharedViewModel = viewModel()


                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavContainer(
                        navController = navController,
                        sharedViewModel = sharedViewModel
                    )
                }
                BottomNav(navController = navController, sharedViewModel = sharedViewModel)
            }
        }
    }
}


@Composable
fun BottomNav(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    val items = listOf(
        Routes.List,
        Routes.Details,
        Routes.Kennel,
    )
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
            composable(route = Routes.List.routeSchema) {

                ShelterListScreen(
                    navController = navController,
                    navigateToDetails = {
                        Timber.d("Inside lambda")
                        //navController.navigate("details/$it")
                        navController.navigate(route = Routes.Details.create("$it"))
                    },
                    sharedViewModel = sharedViewModel
                )
            }
            composable(
                route = Routes.Details.routeSchema,
                arguments = listOf(navArgument(NAV_ARGUMENT_ID) { type = NavType.StringType })
            ) {
                val shelterId = it.arguments?.getString(NAV_ARGUMENT_ID)!!
                ShelterDetailsScreen(
                    navController = navController,
                    sharedViewModel = sharedViewModel,
                    id = shelterId.toInt(),
                )
            }
            composable(route = Routes.Kennel.routeSchema) {
                KennelListScreen(
                    navController = navController,
                )
            }
        }
    }
}
@Composable
fun NavContainer(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    NavHost(navController = navController, startDestination = Routes.List.routeSchema) {
        composable(route = Routes.List.routeSchema) {

            ShelterListScreen(
                navController = navController,
                navigateToDetails = {
                    Timber.d("Inside lambda")
                    //navController.navigate("details/$it")
                    navController.navigate(route = Routes.Details.create("$it"))
                },
                sharedViewModel = sharedViewModel
            )
        }

        composable(
            route = Routes.Details.routeSchema,
            arguments = listOf(navArgument(NAV_ARGUMENT_ID) { type = NavType.StringType })
        ) {
            val shelterId = it.arguments?.getString(NAV_ARGUMENT_ID)!!
            ShelterDetailsScreen(
                navController = navController,
                sharedViewModel = sharedViewModel,
                id = shelterId.toInt(),
            )
        }

        composable(route = Routes.Kennel.routeSchema) {
            KennelListScreen(
                navController = navController,
            )
        }
    }
}

sealed class Routes(val routeSchema: String) {
    object List : Routes("list")
    object Kennel : Routes("kennel")
    object Details : Routes("details/{$NAV_ARGUMENT_ID}") {
        fun create(id: String) = routeSchema.replace("{$NAV_ARGUMENT_ID}", id)
    }

    companion object {
        const val NAV_ARGUMENT_ID = "id"
    }
}