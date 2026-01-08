package com.example.reviewed.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.*
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.util.Locale

/* -------------------- FADE CONTENT -------------------- */

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FadeContent(
    isLoading: Boolean,
    shimmer: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    AnimatedContent(
        targetState = isLoading,
        transitionSpec = { fadeIn(tween(350)) with fadeOut(tween(200)) },
        label = "fade_content"
    ) { loading ->
        if (loading) shimmer() else content()
    }
}

/* -------------------- TOP PROFILE -------------------- */

@Composable
fun TopProfileBar(
    onProfileClick: () -> Unit,
    onNotificationClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircleIcon(Icons.Default.Person, onProfileClick)
        CircleIcon(Icons.Default.Notifications, onNotificationClick)
    }
}

@Composable
private fun CircleIcon(icon: ImageVector, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(Color(0xFF1F1F1F))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, null, tint = Color.White)
    }
}

/* -------------------- HEADER -------------------- */

@Composable
fun HeaderSection() {
    Column {
        Text("Hey, Casey 👋", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text("Get smart experience in Home Cleaning", color = Color.Gray)
    }
}

/* -------------------- SEARCH -------------------- */

@Composable
fun HomeSearchBar() {
    var query by remember { mutableStateOf("") }
    val focusManager = androidx.compose.ui.platform.LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        TextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)),
            placeholder = { Text("Search for a service...", color = Color.Gray) },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray)
            },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { query = ""; focusManager.clearFocus() }) {
                        Icon(Icons.Default.Close, contentDescription = null, tint = Color.Gray)
                    }
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = { focusManager.clearFocus() }
            )
        )
    }
}

/* -------------------- LOCATION -------------------- */

@Composable
fun LocationCard() {
    val context = LocalContext.current
    var locationText by remember { mutableStateOf("Tap to enable location") }
    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { hasPermission = it }

    LaunchedEffect(hasPermission) {
        if (hasPermission) {
            try {
                val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                location?.let {
                    val geocoder = Geocoder(context, Locale.getDefault())
                    val address = geocoder.getFromLocation(it.latitude, it.longitude, 1)?.first()
                    locationText = listOfNotNull(
                        address?.thoroughfare,
                        address?.subLocality,
                        address?.locality
                    ).distinct().joinToString(", ")
                }
            } catch (_: Exception) {
                locationText = "Location unavailable"
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color.White.copy(alpha = 0.05f))
            .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(18.dp))
            .clickable { if (!hasPermission) launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION) }
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.LocationOn, null, tint = Color(0xFFFFC107))
            Spacer(Modifier.width(10.dp))
            Column {
                Text("Your Location", color = Color.Gray, fontSize = 12.sp)
                Text(locationText, color = Color.White, maxLines = 1)
            }
        }
    }
}

/* -------------------- CATEGORY (LazyRow) -------------------- */

@Composable
fun CategoryRow(categories: List<Category>, navController: NavController) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items(categories, key = { it.id }) { category ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFF1A1A1A))
                        .clickable {
                            navController.navigate("booking/${category.id}")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(category.icon),
                        contentDescription = category.title,
                        modifier = Modifier.size(40.dp)
                    )
                }
                Spacer(Modifier.height(6.dp))
                Text(category.title, color = Color.White, fontSize = 12.sp)
            }
        }
    }
}

/* -------------------- PROMO BANNER -------------------- */

@Composable
fun PromoBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFC107), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "Get 20% OFF your first booking!",
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Limited time offer",
                color = Color.Black,
                fontSize = 13.sp
            )
        }
    }
}

/* -------------------- LAUNDRY (LazyRow) -------------------- */

@Composable
fun LaundryRow(laundries: List<LaundryShop>, navController: NavController) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(laundries, key = { it.id }) { shop ->
            Box(
                modifier = Modifier
                    .width(220.dp)
                    .height(150.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .clickable {
                        navController.navigate("laundry/${shop.id}")
                    }
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(shop.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}