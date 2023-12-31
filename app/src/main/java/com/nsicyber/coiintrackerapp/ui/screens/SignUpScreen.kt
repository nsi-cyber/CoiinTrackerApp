package com.nsicyber.coiintrackerapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nsicyber.coiintrackerapp.R
import com.nsicyber.coiintrackerapp.ui.components.BaseView
import com.nsicyber.coiintrackerapp.ui.components.InputTextField
import com.nsicyber.coiintrackerapp.ui.components.PasswordInputTextField
import com.nsicyber.coiintrackerapp.viewmodel.SignupViewModel


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SignUpScreen() {
    val viewModel = hiltViewModel<SignupViewModel>();



    BaseView(canGoBack = true, viewModel = viewModel, useIsBusy = true, isVerticalPaddingEnabled = false) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {


            Image(
                modifier = Modifier.size(246.dp),
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = ""
            )


//Texts
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "Sign Up",

                    // Heading / H2 / ExtraBold
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight(800),
                        color = Color(0xFF1B1725),
                    )
                )
                Text(
                    text = "To enter the beauty of cryptoworld, sign up.",

                    // Body/Small/semibold
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight(600),
                        color = Color(0x8064748B),
                    )
                )


            }

//Input
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                InputTextField(placeholder = "Enter your e-Mail",
                    value = viewModel.mail.value,
                    onValueChange = {
                        viewModel.mail.value = it
                    })


                PasswordInputTextField(
                    placeholder = "Enter your Password",
                    onValueChange = {
                        viewModel.password1.value = it
                    },
                    value = viewModel.password1.value,
                )

                PasswordInputTextField(
                    placeholder = "Enter your Password Again",
                    onValueChange = {
                        viewModel.password2.value = it
                    },
                    value = viewModel.password2.value,
                )
            }


//Buttons
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {


                Row(
                    Modifier
                        .clickable {
                            viewModel.signUpApiCall()


                        }
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFF1B1725),
                            shape = RoundedCornerShape(size = 8.dp)
                        )
                        .padding(start = 36.dp, top = 15.dp, end = 36.dp, bottom = 15.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        8.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Sign Up",

                        // Body/Large/bold
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 20.8.sp,
                            fontWeight = FontWeight(700),
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center,
                        )
                    )

                }


            }

        }
    }
}