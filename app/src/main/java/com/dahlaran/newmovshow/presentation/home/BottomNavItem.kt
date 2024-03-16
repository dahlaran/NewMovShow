package com.dahlaran.newmovshow.presentation.home

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val name: String,
    val route: String,
    val imageNotSelected: ImageVector,
    val imageSelected: ImageVector,
    val badgeCount: Int = 0
)