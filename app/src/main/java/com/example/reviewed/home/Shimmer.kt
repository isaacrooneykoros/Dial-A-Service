package com.example.reviewed.home

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/* -------------------- SHIMMER BRUSH -------------------- */

@Composable
fun shimmerBrush(): Brush {
    val transition = rememberInfiniteTransition(label = "shimmer_transition")
    val translateX by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1200f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1200,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_translate"
    )

    return Brush.linearGradient(
        colors = listOf(
            Color.DarkGray.copy(alpha = 0.35f),
            Color.LightGray.copy(alpha = 0.6f),
            Color.DarkGray.copy(alpha = 0.35f)
        ),
        start = Offset(translateX - 300f, 0f),
        end = Offset(translateX, 0f)
    )
}

/* -------------------- CATEGORY SHIMMER -------------------- */

@Composable
fun ShimmerCategoryRow() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        repeat(4) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(shimmerBrush())
            )
        }
    }
}

/* -------------------- LOCATION SHIMMER -------------------- */

@Composable
fun ShimmerLocationCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(shimmerBrush())
    )
}

/* -------------------- LAUNDRY / SERVICE SHIMMER -------------------- */

@Composable
fun ShimmerLaundryRow() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        repeat(2) {
            Box(
                modifier = Modifier
                    .width(220.dp)
                    .height(150.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(shimmerBrush())
            )
        }
    }
}
