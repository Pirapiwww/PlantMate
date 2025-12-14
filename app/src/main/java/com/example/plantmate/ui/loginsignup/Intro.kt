package com.example.plantmate.ui.loginsignup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plantmate.R

@Composable
fun IntroScreen(
    onSignInClicked: () -> Unit = {},
    onCreateAccountClicked: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.splash_bg),
                contentScale = ContentScale.Crop
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "The best\napp for\nyour plants",
                color = Color.Black,
                fontSize = 34.sp,
                lineHeight = 38.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = onSignInClicked,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(48.dp),
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFDDE6C7) // abu-abu lembut
                )
            ) {
                Text("Sign in", color = Color.Black)
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Create an account",
                color = Color.Black.copy(alpha = 0.8f),
                modifier = Modifier.clickable { onCreateAccountClicked() }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewIntroScreen() {
    IntroScreen()
}
