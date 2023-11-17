package za.co.zone.androidLib.states

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class SelectOptionListState(
	val id: String = "",
	val text: String = "",
	val textColor: Color = Color.Unspecified,
	val subText: String = "",
	val subTextColor: Color = Color.Unspecified,
	val infoFlag: Boolean = false,
	val icon: ImageVector? = null
)