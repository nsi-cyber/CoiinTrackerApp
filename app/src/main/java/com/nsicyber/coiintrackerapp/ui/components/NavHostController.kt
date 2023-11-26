package com.nsicyber.coiintrackerapp.ui.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nsicyber.coiintrackerapp.ui.screens.DetailScreen
import com.nsicyber.coiintrackerapp.ui.screens.HomeScreen
import com.nsicyber.coiintrackerapp.ui.screens.LoginScreen
import com.nsicyber.coiintrackerapp.ui.screens.ProfileScreen
import com.nsicyber.coiintrackerapp.ui.screens.SignUpScreen
import com.nsicyber.coiintrackerapp.ui.screens.SplashScreen


var navHostController: NavHostController? = null

var login = "login"
var signup = "signUp"
var home = "home"
var detail = "detail"
var profile = "profile"
var splash = "splash"


@Composable
fun NavigationController() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = splash) {

        composable(splash) {
            SplashScreen()
        }

        composable(login) {
            LoginScreen()
        }

        composable(signup) {
            SignUpScreen()
        }
        composable(home) {
            HomeScreen()
        }

        composable(profile) {
            ProfileScreen()
        }
        composable(
            "$detail?id={id}", arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                },
            )
        ) { navBackStackEntry ->
            DetailScreen(navBackStackEntry.arguments?.getString("id"))
        }




    }
    navHostController = navController;

}
