package com.example.reviewed.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.reviewed.R
import com.example.reviewed.home.BottomNavBar

@Composable
fun ProfileContent(user: UserProfile) {

    val navController = rememberNavController()

    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.height(30.dp))

        // PROFILE CARD
        Card(
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color.DarkGray),
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Profile",
                        tint = Color.LightGray,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(Modifier.width(12.dp))

                Column(Modifier.weight(1f)) {
                    Text(user.name, color = Color.White, fontWeight = FontWeight.Bold)
                    Text(user.phone, color = Color.Gray)
                }

                Button(
                    onClick = { /* Edit */ },
                    modifier = Modifier
                        .offset(8.dp, 8.dp)
                        .height(36.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFC107)
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        "Edit Profile",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.labelSmall.fontSize
                    )
                }
            }
        }

        Spacer(Modifier.height(18.dp))

        // QUICK ACTIONS
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ProfileActionCard("Bookings", R.drawable.booking,"Laundry")
            ProfileActionCard("Addresses", R.drawable.adresses)
            ProfileActionCard("Payments",R.drawable.payments)
            ProfileActionCard("Reviews",R.drawable.reviews)
        }

        Spacer(Modifier.height(20.dp))

        if (user.isProvider) {
            ProviderStats(user)
            Spacer(Modifier.height(20.dp))
        }

        ProfileSection("Account") {
            ProfileItem("Personal Information", "Name, phone, email")
            Spacer(Modifier.height(16.dp))
            ProfileItem("Notifications")
            Spacer(Modifier.height(16.dp))
            ProfileItem("Security", "Password & privacy")

        }

        Spacer(Modifier.height(16.dp))

        ProfileSection("Support") {
            ProfileItem("Help & Support")
            Spacer(Modifier.height(16.dp))
            ProfileItem("Terms & Privacy")
        }

        Spacer(Modifier.height(30.dp))

        Button(
            onClick = { /* logout */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3A1C1C)
            )
        ) {
            Text("Logout", color = Color.Red)
        }
    }
}
