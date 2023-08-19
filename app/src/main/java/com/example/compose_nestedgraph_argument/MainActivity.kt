package com.example.compose_nestedgraph_argument

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable


import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.compose_nestedgraph_argument.home_feature.DetailScreen
import com.example.compose_nestedgraph_argument.home_feature.HomeScreen
import com.example.compose_nestedgraph_argument.login_feature.LoginScreen
import com.example.compose_nestedgraph_argument.login_feature.SignUpScreen
import com.example.compose_nestedgraph_argument.ui.theme.Compose_NestedGraph_ArgumentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Compose_NestedGraph_ArgumentTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()
                    NavigationAppHost(navController = navController)
                }
            }
        }
    }
}


/******************************************************************************/

const val ID_ARGUMENT_KEY = "id"
const val NAME_ARGUMENT_KEY = "name"

const val AUTHENTICATION_ROUTE = "authentication"
const val HOME_ROUTE = "home"
const val ROOT_ROUTE = "root"


sealed class Destination(var route: String) {

    object Home : Destination("home_screen")


    object Detail : Destination("detail_screen/{$ID_ARGUMENT_KEY}/{$NAME_ARGUMENT_KEY}") {
        fun passRequiredNameAndId(id: Int, name: String): String {
            return "detail_screen/$id/$name"
        }
    }


    object Login : Destination("login_screen?id={$ID_ARGUMENT_KEY}&name={$NAME_ARGUMENT_KEY}") {
        fun passOptionalNameAndId(
            id: Int = 0,
            name: String = ""
        ): String  // 0 and "" are default values
        {
            return "login_screen?id=$id&name=$name"
        }
    }


    object SignUp : Destination("signUp_screen")
}


@Composable
fun NavigationAppHost(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = HOME_ROUTE,  // important
        route = ROOT_ROUTE    // add it
    ) {

        homeNavGraph(navController)
        authNavGraph(navController)

    }
}


fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {

    navigation(
        startDestination = Destination.Home.route,
        route = HOME_ROUTE
    ){
        composable(Destination.Home.route) { HomeScreen(navController) }
        // to get required argument
        composable(
            route = Destination.Detail.route,
            arguments = listOf(

                navArgument(ID_ARGUMENT_KEY) {
                    type = NavType.IntType
                },
                navArgument(NAME_ARGUMENT_KEY) {
                    type = NavType.StringType
                },
            )
        ) {
            Log.d("Args", it.arguments?.getInt(ID_ARGUMENT_KEY).toString())
            Log.d("Args", it.arguments?.getString(NAME_ARGUMENT_KEY).toString())
            DetailScreen(navController)
        }
    }


}
fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        startDestination = Destination.Login.route,
        route = AUTHENTICATION_ROUTE
    ) {

        // to get optional argument
        composable(
            route = Destination.Login.route,
            arguments = listOf(
                navArgument(ID_ARGUMENT_KEY) {
                    type = NavType.IntType
                    defaultValue = 0  // in optional Arguments using default value
                },

                navArgument(NAME_ARGUMENT_KEY) {
                    type = NavType.StringType
                    defaultValue = ""  // in optional Arguments using default value
                },
            )

        ) {
            Log.d("Args-Login", it.arguments?.getInt(ID_ARGUMENT_KEY).toString())
            Log.d("Args-Login", it.arguments?.getString(NAME_ARGUMENT_KEY).toString())
            LoginScreen(navController)
        }


        composable(Destination.SignUp.route) { SignUpScreen(navController) }

    }
}