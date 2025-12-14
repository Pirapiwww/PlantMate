package com.example.plantmate.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.plantmate.data.DataSource
import com.example.plantmate.ui.components.BottomNavBar
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.draw.paint
import com.example.plantmate.R

@Composable
fun ProfileScreen(navController: NavHostController) {

    val navbarItems = DataSource().loadNavbar()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Column(modifier = Modifier.fillMaxSize()) {

            // ============================
            //         GREEN HEADER
            // ============================
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .paint(
                        painter = painterResource(id = R.drawable.profile_bg),
                        contentScale = ContentScale.Crop
                    ),
                contentAlignment = Alignment.TopCenter
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 80.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Foto Profil
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.6f))
                    ) {
                        Image(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    // Nama
                    Text(
                        text = "Unknown",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(12.dp))


                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // ============================
            //        MENU LIST
            // ============================
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
            ) {
                MenuItem("Account Details")
                MenuItem("Log Out")
            }

            Spacer(modifier = Modifier.weight(1f))
        }

        // ============================
        //       BOTTOM NAVIGATION
        // ============================
        BottomNavBar(
            navbarItems = navbarItems,
            navController = navController,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

    }
}

@Composable
private fun MenuItem(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clickable { }
            .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, fontSize = 16.sp, modifier = Modifier.weight(1f))
        Text(">", color = Color.Gray)
    }

    Divider(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFFE7E9E2)
    )
}


