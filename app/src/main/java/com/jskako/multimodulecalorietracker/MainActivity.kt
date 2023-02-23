package com.jskako.multimodulecalorietracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.jskako.core.domain.preferences.AppPreferences
import com.jskako.multimodulecalorietracker.navigation.Route
import com.jskako.multimodulecalorietracker.ui.theme.MultiModuleCalorieTrackerTheme
import com.jskako.onboarding_presentation.welcome.WelcomeScreen
import com.jskako.onboarding_presentation.activity.ActivityScreen
import com.jskako.onboarding_presentation.age.AgeScreen
import com.jskako.onboarding_presentation.gender.GenderScreen
import com.jskako.onboarding_presentation.goal.GoalScreen
import com.jskako.onboarding_presentation.height.HeightScreen
import com.jskako.onboarding_presentation.nutrient_goal.NutrientGoalScreen
import com.jskako.onboarding_presentation.weight.WeightScreen
import com.jskako.tracker_presentation.search.SearchScreen
import com.jskako.tracker_presentation.tracker_overview.TrackerOverviewScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var appPreferences: AppPreferences
    private var shouldShowOnBoarding = true

    @OptIn(
        ExperimentalMaterial3Api::class, ExperimentalCoilApi::class,
        ExperimentalComposeUiApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val job = async(IO) {
                    shouldShowOnBoarding = appPreferences.loadShouldShowOnBoarding()
                }
                job.await()

                setContent {
                    MultiModuleCalorieTrackerTheme {
                        val navController = rememberNavController()
                        val snackbarHostState = remember { SnackbarHostState() }
                        Scaffold(
                            modifier = Modifier.fillMaxSize(),
                            snackbarHost = { SnackbarHost(snackbarHostState) }
                        ) {
                            NavHost(
                                modifier = Modifier
                                    .padding(it),
                                navController = navController,
                                startDestination = if (shouldShowOnBoarding) Route.WELCOME else Route.TRACKER_OVERVIEW
                            ) {
                                composable(Route.WELCOME) {
                                    WelcomeScreen(onNextClick = {
                                        navController.navigate(Route.GENDER)
                                    })
                                }
                                composable(Route.AGE) {
                                    AgeScreen(
                                        snackbarHostState = snackbarHostState,
                                        onNextClick = {
                                            navController.navigate(Route.HEIGHT)
                                        }
                                    )
                                }
                                composable(Route.GENDER) {
                                    GenderScreen(onNextClick = {
                                        navController.navigate(Route.AGE)
                                    })
                                }
                                composable(Route.HEIGHT) {
                                    HeightScreen(
                                        snackbarHostState = snackbarHostState,
                                        onNextClick = {
                                            navController.navigate(Route.WEIGHT)
                                        }
                                    )

                                }
                                composable(Route.WEIGHT) {
                                    WeightScreen(
                                        snackbarHostState = snackbarHostState,
                                        onNextClick = {
                                            navController.navigate(Route.ACTIVITY)
                                        }
                                    )
                                }
                                composable(Route.NUTRIENT_GOAL) {
                                    NutrientGoalScreen(
                                        snackbarHostState = snackbarHostState,
                                        onNextClick = {
                                            navController.navigate(Route.TRACKER_OVERVIEW)
                                        }
                                    )
                                }
                                composable(Route.ACTIVITY) {
                                    ActivityScreen(
                                        onNextClick = {
                                            navController.navigate(Route.GOAL)
                                        }
                                    )
                                }
                                composable(Route.GOAL) {
                                    GoalScreen(
                                        onNextClick = {
                                            navController.navigate(Route.NUTRIENT_GOAL)
                                        }
                                    )
                                }

                                composable(Route.TRACKER_OVERVIEW) {
                                    TrackerOverviewScreen(onNavigateToSearch = { mealName, day, month, year ->
                                        navController.navigate(
                                            Route.SEARCH + "/$mealName" +
                                                    "/$day" +
                                                    "/$month" +
                                                    "/$year"
                                        )
                                    })
                                }
                                composable(
                                    route = Route.SEARCH + "/{mealName}/{dayOfMonth}/{month}/{year}",
                                    arguments = listOf(
                                        navArgument("mealName") {
                                            type = NavType.StringType
                                        },
                                        navArgument("dayOfMonth") {
                                            type = NavType.IntType
                                        },
                                        navArgument("month") {
                                            type = NavType.IntType
                                        },
                                        navArgument("year") {
                                            type = NavType.IntType
                                        }
                                    )
                                ) { navBackStackEntry ->
                                    val mealName =
                                        navBackStackEntry.arguments?.getString("mealName") ?: ""
                                    val dayOfMonth =
                                        navBackStackEntry.arguments?.getInt("dayOfMonth") ?: 0
                                    val month = navBackStackEntry.arguments?.getInt("month") ?: 0
                                    val year = navBackStackEntry.arguments?.getInt("year") ?: 0
                                    SearchScreen(
                                        snackbarHostState = snackbarHostState,
                                        mealName = mealName,
                                        dayOfMonth = dayOfMonth,
                                        month = month,
                                        year = year,
                                        onNextClick = {
                                            navController.navigateUp()
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}