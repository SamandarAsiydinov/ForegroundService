package com.example.foregoundservice

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foregoundservice.ui.theme.Purple500
import com.example.foregoundservice.util.foregroundStartService

@Composable
fun NotificationChannel(context: Context) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Purple500)
                .padding(15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Foreground Service",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    context.foregroundStartService("Start")
                },
                modifier = Modifier
                    .width(100.dp)
                    .height(45.dp)
                    .clip(RoundedCornerShape(5.dp))
            ) {
                Text(
                    text = "Trigger",
                    color = Color.White,
                    fontSize = 15.sp
                )
            }

            Spacer(modifier = Modifier.height(50.dp))

            Button(
                onClick = {
                    context.foregroundStartService("Exit")
                },
                modifier = Modifier
                    .width(100.dp)
                    .height(45.dp)
                    .clip(RoundedCornerShape(5.dp))
            ) {
                Text(
                    text = "Exit",
                    color = Color.White,
                    fontSize = 15.sp
                )
            }
        }
    }
}