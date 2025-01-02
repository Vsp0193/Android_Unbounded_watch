package com.unbounded.realizingself.NAVIGATION

sealed class OnBoardingGraph(val route: String) {
    object RoutineListScreen : OnBoardingGraph(route = "RoutineListScreen")
    object StartScreen : OnBoardingGraph(route = "StartScreen")
    object PagerScreen : OnBoardingGraph(route = "PagerScreen")
    object ForgotPassword : OnBoardingGraph(route = "forgot_password")
    object Home : OnBoardingGraph(route = "home")
    object OtpScreen : OnBoardingGraph(route = "otp_screen")
}