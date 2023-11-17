package za.co.zone.androidLib.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import za.co.zone.androidLib.models.NavigationItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigationIcon(
	navigationItem: NavigationItem,
	selected: Boolean
) {
	val badgeCount = navigationItem.badgeCount
	BadgedBox(
		badge = {
			if (navigationItem.badgeCount != null) {
				Text(
					modifier = Modifier.padding(2.dp),
					text = badgeCount.toString()
				)
			} else if (navigationItem.hasNews) Badge()
		}
	) {
		val selectedIcon = navigationItem.selectedIcon ?: navigationItem.unselectedIcon
		val unSelectedIcon = navigationItem.unselectedIcon
		Icon(
			modifier = Modifier.size(24.dp),
			imageVector = if (selected) selectedIcon else unSelectedIcon,
			contentDescription = navigationItem.label
		)
	}
}