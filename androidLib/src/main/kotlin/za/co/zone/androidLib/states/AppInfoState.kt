package za.co.zone.androidLib.states

data class AppInfoState(
	val message: String,
	val header: String = "",
	val table: List<String> = emptyList()
)