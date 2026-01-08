package com.example.reviewed.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.reviewed.navigation.ROUT_HOME
import com.example.reviewed.navigation.ROUT_PROFILE
import com.example.reviewed.navigation.ROUT_SPLASH

data class NavItem(val route: String, val icon: ImageVector)

@Composable
fun BottomNavBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        NavItem(ROUT_HOME, Icons.Default.Home),
        NavItem(ROUT_SPLASH, Icons.Default.Place),
        NavItem(ROUT_SPLASH, Icons.Default.ShoppingCart),
        NavItem(ROUT_PROFILE, Icons.Default.Person)
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val selectedIndex = items.indexOfFirst { it.route == currentRoute }.takeIf { it >= 0 } ?: 0

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        color = Color(0xFF1A1A1A),
        shape = RoundedCornerShape(30.dp),
        shadowElevation = 12.dp
    ) {
        Row(
            Modifier.padding(vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items.forEachIndexed { index, item ->
                val scale by animateFloatAsState(
                    targetValue = if (selectedIndex == index) 1.2f else 1f
                )

                Icon(
                    imageVector = item.icon,
                    contentDescription = item.route,
                    tint = if (selectedIndex == index) Color(0xFFFFC107) else Color.Gray,
                    modifier = Modifier
                        .size(26.dp)
                        .scale(scale)
                        .clickable {
                            if (currentRoute != item.route) {
                                navController.navigate(item.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                }
                            }
                        }
                )
            }
        }
    }
}