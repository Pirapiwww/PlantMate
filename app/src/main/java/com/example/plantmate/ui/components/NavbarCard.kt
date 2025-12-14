package com.example.plantmate.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.plantmate.model.NavbarIcon

@Composable
fun BottomNavBar(
    navbarItems: List<NavbarIcon>,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute =
        navBackStackEntry.value?.destination?.route
            ?.substringBefore("?") // ⬅️ aman untuk route dengan argumen

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        navbarItems.forEach { item ->

            val isSelected = currentRoute == item.route

            BottomNavItem(
                item = item,
                selected = isSelected,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )

        }
    }
}

@Composable
fun BottomNavItem(
    item: NavbarIcon,
    selected: Boolean,
    onClick: () -> Unit
) {
    val tintColor = if (selected) Color(0xFF4CAF50) else Color.Gray

    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(item.icon),
            contentDescription = null,
            modifier = Modifier.size(30.dp),
            colorFilter = ColorFilter.tint(tintColor)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stringResource(item.title),
            style = MaterialTheme.typography.labelSmall,
            color = tintColor
        )
    }
}
