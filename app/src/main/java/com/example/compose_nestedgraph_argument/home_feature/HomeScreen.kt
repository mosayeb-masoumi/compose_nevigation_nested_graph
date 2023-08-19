package com.example.compose_nestedgraph_argument.home_feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
fun HomeScreen(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Green.copy(alpha = 0.5f))
            .padding(all = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        
        Text(text = "Home Screen")
        
        Button(onClick = {
            navController.navigate(route = Destination.Detail.passRequiredNameAndId(id = 1365 , name = "Mosayeb"))
        }) {
            Text(text = "send required Arguments - goto detail screen")
        }



        Spacer(modifier = Modifier.height(10.dp))



        Button(onClick = {
            // it's optional we can pass id and nameor not
            navController.navigate(route = Destination.Login.passOptionalNameAndId(id = 61 , name = "nabii"))
//            navController.navigate(route = Destination.Login.passOptionalNameAndId())
        }) {
            Text(text = "send Optional Argument - goto login screen")
        }


    }
}