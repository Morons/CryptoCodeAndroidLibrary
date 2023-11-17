package za.co.zone.androidLib.util

import android.content.res.Resources
import android.util.TypedValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.math.RoundingMode
import java.text.DecimalFormat


fun Int.dpToPx(): Int {
	return TypedValue.applyDimension(
		TypedValue.COMPLEX_UNIT_DIP,
		this.toFloat(),
		Resources.getSystem().displayMetrics
	).toInt()
}

fun Int.spToPx(): Int {
	return TypedValue.applyDimension(
		TypedValue.COMPLEX_UNIT_SP,
		this.toFloat(),
		Resources.getSystem().displayMetrics
	).toInt()
}

fun Int.toDisplayMetrics(): Float {
	return TypedValue.applyDimension(
		TypedValue.COMPLEX_UNIT_PX,
		this.toFloat(),
		Resources.getSystem().displayMetrics
	)
}

fun Int.spToDp(): Int {
	val pixels = this.spToPx()
	return pixels.pxToDp()
}

fun Int.dpToSp(): Int {
	val pixels = this.dpToPx()
	return pixels.pxToSp()
}

fun Int.pxToDp(): Int {
	return (this / Resources.getSystem().displayMetrics.density).toInt()
}

fun Int.pxToSp(): Int {
	return (this / Resources.getSystem().displayMetrics.density).toInt()
}

fun Float.pxToDp(): Int {
	return (this / Resources.getSystem().displayMetrics.density).toInt()
}

fun Float.toDp(): Dp {
	return (this / Resources.getSystem().displayMetrics.density).dp
}

fun Dp.toPx(): Float {
	return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.value, Resources.getSystem().displayMetrics)
}

fun Double.roundUp(scale: Int): Double {
	return try {
		this.toBigDecimal().setScale(scale, RoundingMode.UP).toDouble()
	} catch (e: NumberFormatException) {
		Double.NaN
	}
}

fun Double.roundUpToString(scale: Int): String {
	return when {
		this.isNaN() -> "NaN"
		this.isInfinite() -> "Inf"
		else -> {
			val df = DecimalFormat("0.${"0".repeat(scale)}")
			df.roundingMode = RoundingMode.UP
			val doubleValue = this.toBigDecimal().setScale(scale, RoundingMode.HALF_UP).toDouble()
			df.format(doubleValue)
		}
	}
}