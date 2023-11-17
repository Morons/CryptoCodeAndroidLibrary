package za.co.zone.androidLib.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.AppBottomNavItem(
	modifier: Modifier = Modifier,
	icon: ImageVector? = null,
	contentDescription: String? = null,
	selected: Boolean = false,
	badgeCount: UInt? = null,
	selectedColor: Color = MaterialTheme.colorScheme.secondary,
	unselectedColor: Color = MaterialTheme.colorScheme.onPrimary,
	badgeTextColor: Color = MaterialTheme.colorScheme.onSecondary,
	enabled: Boolean = true,
	onClick: () -> Unit = {}
) {
	val lineLength = animateFloatAsState(
		targetValue = if (selected) 1f else 0f,
		animationSpec = tween(durationMillis = 300), label = ""
	)

	NavigationBarItem(
		modifier = modifier,
		enabled = enabled,
		selected = selected,
		onClick = onClick,
		icon = {
			Box(
				modifier = modifier
					.padding(8.dp)
					.drawBehind {
						if (lineLength.value > 0f) {
							drawLine(
								color = if (selected) selectedColor else unselectedColor,
								start = Offset(
									x = size.width / 2f - lineLength.value * size.width / 2f,
									y = size.height
								),
								end = Offset(x = size.width / 2f + lineLength.value * size.width / 2f, y = size.height),
								strokeWidth = 2.dp.toPx(),
								cap = StrokeCap.Round
							)
						}
					}
			) {
				if (icon != null) {
					Icon(
						modifier = Modifier.size(size = 24.dp),
						imageVector = icon,
						contentDescription = contentDescription,
					)
				}
				if (badgeCount != null) {
					(when {
						badgeCount > 99u -> {
							"99+"
						}

						else -> {
							"$badgeCount"
						}
					}).apply {
						Text(
							text = this,
							color = badgeTextColor,
							style = MaterialTheme.typography.bodySmall,
							textAlign = TextAlign.Center,
							modifier = Modifier
								.align(Alignment.TopEnd)
								.offset(5.dp, (-5).dp)
								.clip(CircleShape)
								.background(selectedColor)
						)
					}
				}
			}
		}
	)
}