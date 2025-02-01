package com.example.rickmorty.ui.screens.main

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
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
import com.example.rickmorty.ui.screens.favorite.FavoriteCharactersScreen
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
    val activeIconSize = 44.dp
    val inactiveIconSize = 20.dp

    val isBottomAppBarVisible = remember { mutableStateOf(true) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            private var lastScrollPosition = 0f

            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                if (delta > 20) {
                    isBottomAppBarVisible.value = false
                } else if (delta < 20) {
                    isBottomAppBarVisible.value = true
                }
                lastScrollPosition = delta
                return Offset.Zero
            }
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(nestedScrollConnection),
        bottomBar = {
            if (isNotDetail && isBottomAppBarVisible.value) {
                BottomAppBar {
                    bottomNavListOf.forEachIndexed { index, bottomNavItem ->
                        val isSelected = bottomNavItem.route == currentDestination

                        val iconSize by animateFloatAsState(
                            targetValue = if (isSelected) activeIconSize.value else inactiveIconSize.value,
                            animationSpec = tween(durationMillis = 800)
                        )

                        val rotationAngle by animateFloatAsState(
                            targetValue = if (isSelected) 360f else 0f,
                            animationSpec = tween(durationMillis = 800)
                        )

                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                navController.navigate(bottomNavItem.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        inclusive = true
                                    }
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
                                        imageVector = if (isSelected) {
                                            bottomNavItem.selectedIcon
                                        } else {
                                            bottomNavItem.unselectedIcon
                                        },
                                        contentDescription = bottomNavItem.title,
                                        modifier = Modifier
                                            .size(iconSize.dp)
                                            .rotate(rotationAngle)
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
        },
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
    NavHost(
        navController = navController,
        startDestination = NavRoute.Characters,
    ) {
        composable(
            NavRoute.Characters,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(300)
                )
            }, exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(300)
                )
            }
        ) {
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

        composable(
            NavRoute.CharactersDetail,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            }
        ) {
            val id = it.arguments?.getString("id")?.toInt() ?: 0
            CharactersDetailScreen(id, navController)
        }

        composable(NavRoute.Episodes,
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(300)
                )
            }) {
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

        composable(
            NavRoute.EpisodesDetail,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            })
        {
            val id = it.arguments?.getString("id")?.toInt() ?: 0
            EpisodesDetailScreen(id, navController)
        }

        composable(NavRoute.Locations,
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(300)
                )
            }
        ) {
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

        composable(
            NavRoute.LocationsDetail,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            }
        ) {
            val id = it.arguments?.getString("id")?.toInt() ?: 0
            LocationsDetailScreen(id, navController)
        }

        composable(
            NavRoute.Favorites,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(300)
                )
            }, exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(300)
                )
            }

        ) {
            FavoriteCharactersScreen()
        }
    }
}