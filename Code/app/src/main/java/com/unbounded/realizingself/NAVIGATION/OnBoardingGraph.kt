package com.unbounded.realizingself.NAVIGATION

sealed class OnBoardingGraph(val route: String) {
    object RoutineListScreen : OnBoardingGraph(route = "RoutineListScreen")
    object StartScreen : OnBoardingGraph(route = "StartScreen")
    object SignUpScreen : OnBoardingGraph(route = "sign_up_screen")
    object ForgotPassword : OnBoardingGraph(route = "forgot_password")
    object Home : OnBoardingGraph(route = "home")
    object OtpScreen : OnBoardingGraph(route = "otp_screen")
}