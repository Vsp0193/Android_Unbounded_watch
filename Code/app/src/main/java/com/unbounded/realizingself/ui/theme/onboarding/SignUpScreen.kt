package com.unbounded.realizingself.ui.theme.onboarding


import android.widget.Toast
import com.unbounded.realizingself.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.unbounded.realizingself.ui.theme.grad1
import com.unbounded.realizingself.ui.theme.grad2
import com.peak.unbounded.features.ui.componentutils.EmailState
import com.peak.unbounded.features.ui.componentutils.EmailStateSaver
import com.peak.unbounded.features.ui.componentutils.PasswordState
import com.peak.unbounded.features.ui.componentutils.UiUtils
import com.peak.unbounded.features.ui.componentutils.UserFNameState
import com.peak.unbounded.features.ui.componentutils.UserLNameState
import com.unbounded.realizingself.NAVIGATION.OnBoardingGraph
import com.unbounded.realizingself.commonview.LogoView
import com.unbounded.realizingself.ui.theme.RealizingSelfTheme
import com.unbounded.realizingself.ui.theme.black
import com.unbounded.realizingself.ui.theme.cyanColor
import com.unbounded.realizingself.ui.theme.onboarding.signup.SignUpViewModel
import com.unbounded.realizingself.ui.theme.primary
import com.unbounded.realizingself.ui.theme.secondary
import com.unbounded.realizingself.ui.theme.transparant
import com.unbounded.realizingself.utils.AppUtility


@Composable
fun SignUpScreen(
    navHostController: NavHostController,
) {

    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    val passwordState = remember { PasswordState() }
    val focusRequester = remember { FocusRequester() }
    val emailState by rememberSaveable(stateSaver = EmailStateSaver) {
        mutableStateOf(EmailState(""))
    }
    val fNameState = remember { UserFNameState() }
    val lNameState = remember { UserLNameState() }
    val viewModel: SignUpViewModel = hiltViewModel()
    // val signUpResult by viewModel.signUpLivedata.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(grad1, grad2)
                )
            ),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(grad1, grad2)
                    )
                ),
            //  verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LogoView()
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(77.dp)
            )
            Card {
                Text(
                    text = "Welcome to Unbounded",
                    style = TextStyle(
                        fontSize = 24.sp,
                        lineHeight = 49.sp,
                        fontFamily = FontFamily(Font(R.font.dm_sans_regular)),

                        fontWeight = FontWeight(500),
                        color = black,
                    ),
                    modifier = Modifier
                        .wrapContentSize()
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(17.dp)
                )

                UiUtils.Email(
                    readOnly = false,
                    enable = true,
                    emailState = emailState,
                    placeHolder = "Email ",
                    onImeAction = { focusRequester.requestFocus() })
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(27.dp)
                )


                Button(
                    onClick = {

                        if (AppUtility.isNetworkAvailable(context)) {

                            if (fNameState.text.isNotEmpty() && lNameState.text.isNotEmpty() &&
                                emailState.isValid && passwordState.isValid
                            ) {
                                viewModel.signUp(
                                    fNameState.text,
                                    lNameState.text,
                                    emailState.text,
                                    passwordState.text
                                )
                            } else {
                                emailState.enableShowErrors()
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "Please check your internet connection",
                                Toast.LENGTH_SHORT
                            ).show()
                        }


                    },
                    Modifier
                        .shadow(
                            elevation = 44.dp,
                            spotColor = Color(0x331B4FAD),
                            ambientColor = Color(0x331B4FAD)
                        )
                        .fillMaxWidth()
                        .height(44.dp),
                    enabled = emailState.isValid && passwordState.isValid,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primary,
                        contentColor = secondary,
                        disabledContainerColor= secondary,
                        disabledContentColor=transparant
                    )
                ) {
                    Text(
                        text = stringResource(R.string.continue_),

                        style = TextStyle(
                            fontSize = 17.sp,
                            lineHeight = 49.sp,
                            fontFamily = FontFamily(Font(R.font.dm_sans_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFFFFFFFF),

                            textAlign = TextAlign.Center,
                        )
                    )

                    when (val response = signUpResult) {
                        is Response.Error -> Toast(message = response.errorMessage.toString())
                        is Response.Loading -> {
                            SpinningProgressBar(
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        is Response.Success -> {
                            Toast(message = response.data.message.toString())
                            LaunchedEffect(key1 = true) {
                                navHostController.navigate(route = OnBoardingGraph.SignInScreen.route)
                            }

                        }

                        else -> {}
                    }


                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(37.dp)
                )
                ClickableText(style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 49.sp,
                    fontFamily = FontFamily(Font(R.font.dm_sans_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,
                ), text = buildAnnotatedString {
                    append("Don't have an account? ")
                    withStyle(
                        style = SpanStyle(
                            fontSize = 16.sp,
                            // textDecoration = TextDecoration.Underline, // No underline
                            fontFamily = FontFamily(Font(R.font.dm_sans_regular)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFFFFFFFF),
                        ),
                    ) {

                    }

                    withStyle(
                        style = SpanStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.dm_sans_regular)),
                            fontWeight = FontWeight(400),
                            color = cyanColor,

                            ),
                    ) {
                        append("Sign in here")
                    }
                }, onClick = {
                    println("Clicked offset $it")
                    navHostController.navigate(OnBoardingGraph.SignInScreen.route)
                    // naveHostController.popBackStack()
                })


            }

        }
    }

}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignUpScreenPreviewNew() {
    RealizingSelfTheme {
        val naveHostController: NavHostController = rememberNavController()
        SignUpScreen(naveHostController)
    }
}
