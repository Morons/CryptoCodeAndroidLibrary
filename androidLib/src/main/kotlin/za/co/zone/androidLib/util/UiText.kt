package za.co.zone.androidLib.util

import androidx.annotation.StringRes

sealed class UiText {
	
	data class DynamicString(val value: String) : UiText()
	data class StringResource(@StringRes val id: Int) : UiText()
	data class StringResourceException(@StringRes val id: Int, val exception: Exception) : UiText()
	data class StringResourceString(@StringRes val id: Int, val exception: String) : UiText()
	data class StringResourceInt(@StringRes val id: Int, val intParam: Int) : UiText()

	companion object {
		fun errorUnknown(): UiText {
			return DynamicString(value = "Error Unknown")
		}
	}
}