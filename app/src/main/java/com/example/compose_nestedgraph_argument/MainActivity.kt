package com.example.compose_nestedgraph_argument

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
//import androidx.navigation.compose.rememberNavController
////import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost


import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.navArgument
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

const val ID_DETAIL_ARGUMENT_KEY = "id"
const val NAME_DETAIL_ARGUMENT_KEY2 = "name"

const val ID_LOGIN_ARGUMENT_KEY = "login_id"
const val NAME_LOGIN_ARGUMENT_KEY = "login_name"

sealed class Destination(var route: String) {

    object Home : Destination("home_screen")


    /*********************** required arguments ****************************/

    object Detail : Destination("detail_screen/{$ID_DETAIL_ARGUMENT_KEY}/{$NAME_DETAIL_ARGUMENT_KEY2}") {

        /*********************** single arguments ****************************/
        fun passId(id: Int):String{
            return "detail_screen/$id"  // or below
//            return this.route.replace(oldValue = "{$DETAIL_ARGUMENT_KEY}" , newValue = id.toString())
        }
        /*********************** multiple arguments ****************************/
        // to send multiple arguments
        fun passRequiredNameAndId(id: Int, name: String): String {
            return "detail_screen/$id/$name"
        }

    }







    /*********************** optional arguments ****************************/

    object Login : Destination("login_screen?id={$ID_LOGIN_ARGUMENT_KEY}&name={$NAME_LOGIN_ARGUMENT_KEY}") {

        /*********************** single arguments ****************************/
        fun passOptionalId(id: Int = 0):String  // 0 and "" are default values
        {
            return "login_screen?id=$id"
        }

        /*********************** multiple arguments ****************************/
        fun passOptionalNameAndId(id: Int = 0, name: String = ""):String  // 0 and "" are default values
        {
            return "login_screen?id=$id&name=$name"
        }
    }


    object SignUp : Destination("signUp_screen")
}


@Composable
fun NavigationAppHost(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Destination.Home.route) {

        composable(Destination.Home.route) { HomeScreen(navController) }


        // to get required argument
        composable(
            route = Destination.Detail.route,
            arguments = listOf(

                navArgument(ID_DETAIL_ARGUMENT_KEY) {
                    type = NavType.IntType
                },

                navArgument(NAME_DETAIL_ARGUMENT_KEY2) {
                    type = NavType.StringType
                },

                )
        ) {
            Log.d("Args", it.arguments?.getInt(ID_DETAIL_ARGUMENT_KEY).toString())
            Log.d("Args", it.arguments?.getString(NAME_DETAIL_ARGUMENT_KEY2).toString())
            DetailScreen(navController)
        }


        // to get optional argument
        composable(
            route = Destination.Login.route,
            arguments = listOf(
                navArgument(ID_LOGIN_ARGUMENT_KEY) {
                    type = NavType.IntType
                    defaultValue = 0  // in optional Arguments using default value
                },

                navArgument(NAME_LOGIN_ARGUMENT_KEY) {
                    type = NavType.StringType
                    defaultValue = ""  // in optional Arguments using default value
                },
            )

        ) {
            Log.d("Args-Login", it.arguments?.getInt(ID_LOGIN_ARGUMENT_KEY).toString())
            Log.d("Args-Login", it.arguments?.getString(NAME_LOGIN_ARGUMENT_KEY).toString())
            LoginScreen(navController)
        }








        composable(Destination.SignUp.route) { SignUpScreen(navController) }
    }
}