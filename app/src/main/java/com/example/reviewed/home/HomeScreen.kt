package com.example.reviewed.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.reviewed.R
import kotlinx.coroutines.delay

data class Category(val id: String, val title: String, val icon: Int)
data class LaundryShop(val id: String, val imageUrl: String, val rating: Double)

@Composable
fun HomeScreen(navController: NavController) {

    var isLoading by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(2000)
        isLoading = false
    }

    val categories = remember {
        listOf(
            Category("Laundry", "Laundry", R.drawable.homelaundry),
            Category("Home Cleaning", "Home \nCleaning", R.drawable.homecleaning),
            Category("Home Repairs", "Home \nRepairs", R.drawable.renovation),
            Category("Home Renovations", "Home \nRenovations", R.drawable.construction)
        )
    }

    val laundries = remember {
        listOf(
            LaundryShop("1","https://images.unsplash.com/photo-1581578731548-c64695cc6952",4.5),
            LaundryShop("2","https://images.unsplash.com/photo-1626806787461-102c1bfaaea1",4.6)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0E0E0E))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            Spacer(Modifier.height(40.dp))

            // Fixed top area (will not scroll)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                TopProfileBar(
                    onProfileClick = { navController.navigate("profile") },
                    onNotificationClick = { navController.navigate("notifications") }
                )

                Spacer(Modifier.height(5.dp))
            }

            // Scrollable content (below the fixed top area)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
            ) {
                HeaderSection()

                Spacer(Modifier.height(40.dp))

                HomeSearchBar()
                Spacer(Modifier.height(14.dp))

                FadeContent(
                    isLoading = isLoading,
                    shimmer = { ShimmerLocationCard() },
                    content = { LocationCard() }
                )

                Spacer(Modifier.height(24.dp))
                Text("Category", color = Color.White, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(12.dp))

                FadeContent(
                    isLoading = isLoading,
                    shimmer = { ShimmerCategoryRow() },
                    content = { CategoryRow(categories, navController) }
                )
                Spacer(modifier = Modifier.height(20.dp))

                PromoBanner()

                Spacer(Modifier.height(28.dp))

                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                    Text("Services Near You", color = Color.White, fontWeight = FontWeight.Bold)
                    Text("See All", color = Color(0xFFFFC107))
                }

                Spacer(Modifier.height(12.dp))

                FadeContent(
                    isLoading = isLoading,
                    shimmer = { ShimmerLaundryRow() },
                    content = { LaundryRow(laundries, navController) }
                )

                Spacer(Modifier.height(120.dp))
            }
        }

        BottomNavBar(navController = navController, modifier = Modifier.align(Alignment.BottomCenter))
    }

}
