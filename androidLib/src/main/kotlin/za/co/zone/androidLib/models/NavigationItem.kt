package za.co.zone.androidLib.models

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
	val route: String,
	val unselectedIcon: ImageVector,
	val selectedIcon: ImageVector? = null,
	val hasNews: Boolean = false,
	val badgeCount: UInt? = null,
	val label: String = ""
)