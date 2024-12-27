package com.unbounded.realizingself.NAVIGATION

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.gson.Gson
import com.peak.unbounded.features.data.local.DataViewModel
import com.unbounded.realizingself.RoutineListScreen
import com.unbounded.realizingself.model.segment.UserPreset
import com.unbounded.realizingself.model.segment.segmets.timer.UserTimeSegment
import com.unbounded.realizingself.screens.StartScreen

@Composable
fun ScreenNavigationGraph(
    modifier: Modifier

) {
    val navHostController = rememberNavController()
    NavHost(
        navController = navHostController,
        startDestination = OnBoardingGraph.RoutineListScreen.route,

        ) {
        composable(route = OnBoardingGraph.RoutineListScreen.route) {

            RoutineListScreen(

                navHostController,
            )
        }
        composable(
            "${OnBoardingGraph.StartScreen.route}./{usersegment}",
            arguments = listOf(
                navArgument("usersegment") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // Retrieve the Parcelable User object from the arguments
            val userPreset = backStackEntry.arguments?.getString("usersegment")?.let { Gson().fromJson(it, UserTimeSegment::class.java) }

           // val userPreset = backStackEntry.arguments?.getSerializable<UserPreset>("user")
            userPreset?.let {
                StartScreen(
                    data =it
                )
            }
        }


    }
}

