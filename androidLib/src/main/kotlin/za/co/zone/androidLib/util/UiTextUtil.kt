package za.co.zone.androidLib.util

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun UiText.asString(): String {
	return when (this) {
		is UiText.DynamicString -> this.value
		is UiText.StringResource -> stringResource(this.id)
		is UiText.StringResourceException -> stringResource(this.id, exception)
		is UiText.StringResourceString -> stringResource(this.id, exception)
		is UiText.StringResourceInt -> stringResource(this.id, intParam)
	}
}

fun UiText.asString(context: Context): String {
	return when (this) {
		is UiText.DynamicString -> this.value
		is UiText.StringResource -> context.getString(this.id)
		is UiText.StringResourceException -> context.getString(this.id, exception)
		is UiText.StringResourceString -> context.getString(this.id, exception)
		is UiText.StringResourceInt -> context.getString(this.id, intParam)
	}
}