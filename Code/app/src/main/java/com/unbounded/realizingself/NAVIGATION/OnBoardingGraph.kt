package com.unbounded.realizingself.NAVIGATION

sealed class OnBoardingGraph(val route: String) {
    object LandingScreen : OnBoardingGraph(route = "landing_screen")
    object SignInScreen : OnBoardingGraph(route = "sign_in_screen")
    object SignUpScreen : OnBoardingGraph(route = "sign_up_screen")
    object ForgotPassword : OnBoardingGraph(route = "forgot_password")
    object Home : OnBoardingGraph(route = "home")
    object OtpScreen : OnBoardingGraph(route = "otp_screen")
}