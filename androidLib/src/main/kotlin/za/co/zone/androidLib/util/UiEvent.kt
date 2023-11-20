package za.co.zone.androidLib.util


sealed class UiEvent : Event() {
	data class ShowSnackbar(val uiText: UiText) : UiEvent()
	data class Navigate(val route: String) : UiEvent()
	object ReturnTo : UiEvent()
	object NavigateUp : UiEvent()
	object PopBackStack : UiEvent()
	object OnLogout : UiEvent()
	object OnLogin : UiEvent()
}