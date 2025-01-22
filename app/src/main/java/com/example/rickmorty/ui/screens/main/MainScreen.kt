package com.example.rickmorty.ui.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.rickmorty.ui.nav.NavRoute
import com.example.rickmorty.ui.nav.bottomNavListOf
import com.example.rickmorty.ui.screens.characters.CharactersScreen
import com.example.rickmorty.ui.screens.characters.detail.CharactersDetailScreen
import com.example.rickmorty.ui.screens.episodes.EpisodeScreen
import com.example.rickmorty.ui.screens.episodes.detail.EpisodesDetailScreen
import com.example.rickmorty.ui.screens.locations.LocationScreen
import com.example.rickmorty.ui.screens.locations.detail.LocationsDetailScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route
    val detailRoutes = listOf(
        NavRoute.EpisodesDetail,
        NavRoute.CharactersDetail,
        NavRoute.LocationsDetail
    )
    val isNotDetail = currentDestination !in detailRoutes

    Scaffold(modifier = Modifier
        .fillMaxSize(),
        topBar = {
            if (isNotDetail) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            fontSize = 26.sp,
                            text = currentDestination ?: "unknown",
                            fontStyle = FontStyle.Italic
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.LightGray,

                    )
                )
            }
        },
        bottomBar = {
            if (isNotDetail) {
                NavigationBar {
                    NavigationBar {
                        bottomNavListOf.forEachIndexed { index, bottomNavItem ->
                            NavigationBarItem(
                                selected = bottomNavItem.route == currentDestination,
                                onClick = {
                                    navController.navigate(bottomNavItem.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            inclusive = true
                                            saveState = true
                                        }
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    BadgedBox(
                                        badge = {
                                            if (bottomNavItem.badges > 0) {
                                                Text("${bottomNavItem.badges}")
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = if (bottomNavItem.route == currentDestination) {
                                                bottomNavItem.selectedIcon
                                            } else {
                                                bottomNavItem.unselectedIcon
                                            },
                                            contentDescription = bottomNavItem.title
                                        )
                                    }
                                },
                                label = {
                                    Text(bottomNavItem.title)
                                }
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NavHostInit(navController)
        }
    }
}

@Composable
fun NavHostInit(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavRoute.Characters) {

        composable(NavRoute.Characters) {
            CharactersScreen(
                navigate = { id ->
                    navController.navigate(
                        NavRoute.CharactersDetail.replace(
                            oldValue = "{id}",
                            newValue = id.toString()
                        )
                    )
                }
            )
        }

        composable(NavRoute.CharactersDetail) {
            val id = it.arguments?.getString("id")?.toInt() ?: 0
            CharactersDetailScreen(id)
        }

        composable(NavRoute.Episodes) {
            EpisodeScreen(
                navigate = { id ->
                    navController.navigate(
                        NavRoute.EpisodesDetail.replace(
                            oldValue = "{id}",
                            newValue = id.toString()
                        )
                    )
                }
            )
        }

        composable(NavRoute.EpisodesDetail) {
            val id = it.arguments?.getString("id")?.toInt() ?: 0
            EpisodesDetailScreen(id)
        }

        composable(NavRoute.Locations) {
            LocationScreen(
                navigate = { id ->
                    navController.navigate(
                        NavRoute.LocationsDetail.replace(
                            oldValue = "{id}",
                            newValue = id.toString()
                        )
                    )
                }
            )
        }

        composable(NavRoute.LocationsDetail) {
            val id = it.arguments?.getString("id")?.toInt() ?: 0
            LocationsDetailScreen(id)
        }
    }
}
