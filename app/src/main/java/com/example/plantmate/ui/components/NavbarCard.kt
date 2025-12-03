package com.example.plantmate.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.navigation.NavController
import com.example.plantmate.model.NavbarIcon

@Composable
fun BottomNavBar(
    navbarItems: List<NavbarIcon>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        navbarItems.forEachIndexed { index, item ->
            BottomNavItem(
                item = item,
                selected = index == selectedIndex,
                onClick = {
                    onItemSelected(index)
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
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
            style = MaterialTheme.typography.labelSmall.copy(
                color = tintColor
            )
        )
    }
}
