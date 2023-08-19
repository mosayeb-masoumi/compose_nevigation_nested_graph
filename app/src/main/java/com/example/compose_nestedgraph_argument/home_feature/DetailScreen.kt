package com.example.compose_nestedgraph_argument.home_feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.compose_nestedgraph_argument.Destination

@Composable
fun DetailScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Red.copy(alpha = 0.5f))
            .padding(all = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {



        Text(text = "Detail Screen")

        Button(onClick = {
            navController.popBackStack()
        }) {
            Text(text = "back button")
        }

        Button(onClick = {
            navController.navigate(Destination.SignUp.route)
        }) {
            Text(text = "goto SignUp screen")
        }

        Button(onClick = {
            navController.navigate(Destination.Login.route)
        }) {
            Text(text = "goto Login screen")
        }

    }
}