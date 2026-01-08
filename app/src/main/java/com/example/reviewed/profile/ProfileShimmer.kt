package com.example.reviewed.profile

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp

@Composable
fun shimmerBrush(): Brush {
    val transition = rememberInfiniteTransition(label = "")
    val x by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            tween(1200, easing = LinearEasing)
        ),
        label = ""
    )

    return Brush.linearGradient(
        colors = listOf(
            Color.DarkGray.copy(0.4f),
            Color.LightGray.copy(0.6f),
            Color.DarkGray.copy(0.4f)
        ),
        start = Offset(x - 200f, 0f),
        end = Offset(x, 0f)
    )
}

@Composable
fun ProfileShimmer() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(110.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(shimmerBrush())
        )

        Spacer(Modifier.height(16.dp))

        repeat(2) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                repeat(2) {
                    Box(
                        Modifier
                            .weight(1f)
                            .height(80.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(shimmerBrush())
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
        }

        Spacer(Modifier.height(16.dp))

        repeat(5) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(shimmerBrush())
            )
            Spacer(Modifier.height(10.dp))
        }
    }
}
