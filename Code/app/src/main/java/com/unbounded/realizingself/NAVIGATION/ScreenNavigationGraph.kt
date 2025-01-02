package com.unbounded.realizingself.NAVIGATION

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.unbounded.realizingself.DataTransferViewModel
import com.unbounded.realizingself.MeditationRoutineViewModel
import com.unbounded.realizingself.RoutineListScreen
import com.unbounded.realizingself.screens.PagerScreen
import com.unbounded.realizingself.screens.StartScreen

@Composable
fun ScreenNavigationGraph(
    modifier: Modifier,


) {
    val dataTransferViewModel: DataTransferViewModel = hiltViewModel()
    val navHostController = rememberNavController()
    val viewModel: MeditationRoutineViewModel = hiltViewModel()
    NavHost(
        navController = navHostController,
        startDestination = OnBoardingGraph.RoutineListScreen.route,

        ) {

        composable(route = OnBoardingGraph.RoutineListScreen.route) {

            RoutineListScreen(
                dataTransferViewModel,
                navHostController,
            )
        }
       /* composable(
            "${OnBoardingGraph.StartScreen.route}./{usersegment}",
            arguments = listOf(
                navArgument("usersegment") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // Retrieve the Parcelable User object from the arguments
            val userPreset = backStackEntry.arguments?.getString("usersegment")?.let { Gson().fromJson(it, UserTimeSegment::class.java) }
            Log.d("callfowedfwodw", "ScreenNavigationGraph: "+userPreset)
           // val userPreset = backStackEntry.arguments?.getSerializable<UserPreset>("user")
            userPreset?.let {
                StartScreen(
                    data =it
                )
            }
        }*/
 composable(route = OnBoardingGraph.StartScreen.route) {
     StartScreen(
         navHostController
     )
        }
        composable(route = OnBoardingGraph.PagerScreen.route) {
            PagerScreen(
                dataTransferViewModel,
         navHostController
     )
        }


    }
}

