package com.example.plantmate.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.plantmate.R
import com.example.plantmate.data.DataSource
import com.example.plantmate.ui.components.BottomNavBar
import com.example.plantmate.setLanguage
import java.util.Locale

@Composable
fun ProfileScreen(navController: NavHostController) {

    val navbarItems = DataSource().loadNavbar()

    var languageMenuExpanded by remember { mutableStateOf(false) }

    val currentLanguage =
        if (Locale.getDefault().language == "id") "Bahasa Indonesia" else "English"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Column(modifier = Modifier.fillMaxSize()) {

            // ============================
            //           HEADER
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

                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.6f))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = "Profile",
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    Text(
                        text = "Unknown",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // ============================
            //         MENU LIST
            // ============================
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
            ) {

                MenuItem(
                    title = stringResource(R.string.account)
                )

                // ---------- LANGUAGE DROPDOWN ----------
                Box {
                    MenuItem(
                        title = stringResource(R.string.language),
                        trailingText = currentLanguage,
                        onClick = { languageMenuExpanded = true }
                    )

                    DropdownMenu(
                        expanded = languageMenuExpanded,
                        onDismissRequest = { languageMenuExpanded = false }
                    ) {

                        DropdownMenuItem(
                            text = { Text("Bahasa Indonesia") },
                            onClick = {
                                languageMenuExpanded = false
                                setLanguage("id")
                            }
                        )

                        DropdownMenuItem(
                            text = { Text("English") },
                            onClick = {
                                languageMenuExpanded = false
                                setLanguage("en")
                            }
                        )
                    }
                }

                // ---------- LOGOUT ----------
                MenuItem(
                    title = stringResource(R.string.logout),
                    titleColor = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }

        // ============================
        //       BOTTOM NAV
        // ============================
        BottomNavBar(
            navbarItems = navbarItems,
            navController = navController,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun MenuItem(
    title: String,
    titleColor: Color = MaterialTheme.colorScheme.onBackground,
    trailingText: String? = null,
    onClick: () -> Unit = {}
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = title,
                fontSize = 16.sp,
                color = titleColor,
                modifier = Modifier.weight(1f)
            )

            if (trailingText != null) {
                Text(
                    text = trailingText,
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.width(8.dp))
            }

            Text(
                text = ">",
                color = Color.Gray
            )
        }

        Divider(color = Color(0xFFE7E9E2))
    }
}
