package com.example.reviewed.profile

data class UserProfile(
    val id: String,
    val name: String,
    val phone: String,
    val imageUrl: String?,
    val isProvider: Boolean,
    val rating: Double = 0.0,
    val jobsCompleted: Int = 0
)

data class ProfileMenuItem(
    val title: String,
    val subtitle: String? = null,
    val icon: ProfileIcon,
    val route: String
)

enum class ProfileIcon {
    BOOKINGS,
    ADDRESS,
    PAYMENTS,
    REVIEWS,
    USER,
    NOTIFICATION,
    SECURITY,
    SUPPORT,
    TERMS,
    LOGOUT
}
