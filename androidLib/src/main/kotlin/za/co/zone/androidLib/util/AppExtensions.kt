package za.co.zone.androidLib.util

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize.Companion.Zero

fun String.wordsToUpperCase(delimiter: String = " "): String {
	return split(delimiter).joinToString(delimiter) { word ->
		word.replaceFirstChar(Char::titlecaseChar)
	}
}

fun String.cleanPhoneNumber(): String {
	val spaces = this.replace(oldValue = " ", newValue = "")
	val dots = spaces.replace(oldValue = ".", newValue = "")
	return dots.replace(oldValue = "-", newValue = "")
}

fun String?.splitToStrings(): List<String> =
	if (this?.isNotEmpty() == true) {
		split(",").map { it.trim() }
	} else emptyList()

fun Modifier.shimmerEffect(): Modifier = composed {
	var size by remember { mutableStateOf(Zero) }
	val transition = rememberInfiniteTransition(label = "Shimmer")
	val startOffsetX by transition.animateFloat(
		initialValue = -2 * size.width.toFloat(),
		targetValue = 2 * size.width.toFloat(),
		animationSpec = infiniteRepeatable(
			animation = tween(
				durationMillis = 1000,
				easing = FastOutSlowInEasing
			)
		),
		label = "Shimmer"
	)
	background(
		brush = linearGradient(
			colors = listOf(
				MaterialTheme.colorScheme.onPrimaryContainer,
				MaterialTheme.colorScheme.onSurface,
				MaterialTheme.colorScheme.onPrimaryContainer
			),
			start = Offset(x = startOffsetX, y = 0f),
			end = Offset(x = startOffsetX + size.width.toFloat(), y = size.height.toFloat())
		)
	).onGloballyPositioned { size = it.size }
}