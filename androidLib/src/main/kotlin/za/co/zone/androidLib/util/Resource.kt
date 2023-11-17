package za.co.zone.androidLib.util

typealias SimpleResource = Resource<Unit>

sealed class Resource<T>(val data: T? = null, val uiText: UiText? = null) {
	class Success<T>(data: T?) : Resource<T>(data = data)
	class Error<T>(uiText: UiText, data: T? = null) : Resource<T>(data = data, uiText = uiText)
	class Loading<T>(val isLoading: Boolean = true) : Resource<T>(data = null)
}