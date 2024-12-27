package com.unbounded.realizingself.data.local

object AppConstants {

    const val DATASTORE_NAME = "unbounded_application"
    const val DATASTORE_USER_ID = "user_id"
    const val APP_BASE_URL = "https://meditation-app-backend.herokuapp.com/"
    //const val APP_BASE_URL = "https://prod-meditation-app-backend.herokuapp.com/"
    const val USER_NAME = "user_name"
    const val USER_EMAIL = "user_email"
    const val USER_ID = "user_id"
    const val USER_F_NAME = "user_f_name"
    const val USER_L_NAME = "user_l_name"
    const val USER_IMAGE = "user_image"
    const val DATE_OF_JOIN = "date_of_join"
    const val USER_IS_ONBOARDING = "user_is_onboarding"
    const val USER_TOKEN = "user_token"
    const val USER_SETTINGS = "user_settings"
    const val QUICK_TIPS = "quick_tips"
    const val IS_ONBOARDING = "IsOnboarding"
    const val REMINDER_ACTIVATE = "activate"
    const val REMINDER_DEACTIVATE = "deactivate"

    const val USER_MOBILE_NO = "user_mobile_no"
    const val CANCEL = "Cancel"
    const val SAVE = "Save"
    const val EDIT = "Edit"
    const val IS_Time_ELAPSED = "IS_Time_ELAPSED"

    enum class SplashState { Shown, Completed }

    enum class OnBoarding(val value:String){
        SignIn("SIGN_IN"),
        SignUp("SIGN_UP")
    }

}