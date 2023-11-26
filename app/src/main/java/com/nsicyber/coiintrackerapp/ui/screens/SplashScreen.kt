package com.nsicyber.coiintrackerapp.ui.screens

import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.nsicyber.coiintrackerapp.ui.components.navHostController
import com.nsicyber.coiintrackerapp.ui.components.signup
import com.nsicyber.coiintrackerapp.viewmodel.LoginViewModel
import com.nsicyber.coiintrackerapp.viewmodel.SplashViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SplashScreen() {
    val viewModel = hiltViewModel<SplashViewModel>();

    BaseView(
        canGoBack = false,
        viewModel = viewModel,
        useIsBusy = false, canScroll = true,
        isVerticalPaddingEnabled = false
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center

            ) {


                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 54.dp),
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Keep Tracking",

                    // Heading / H2 / ExtraBold
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight(800),
                        color = Color(0xFF1B1725),
                    )
                )

            }
        }

    }
}

