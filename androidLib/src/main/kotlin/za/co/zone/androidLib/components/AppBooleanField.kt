package za.co.zone.androidLib.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import za.co.zone.androidLib.states.AppInfoState


@Composable
fun AppBooleanField(
	modifier: Modifier = Modifier,
	errorModifier: Modifier = Modifier,
	checked: Boolean,
	error: String = "",
	infoState: AppInfoState? = null,
	readOnly: Boolean = false,
	onCheckedChange: (Boolean) -> Unit = {},
	hint: String
) {
	var infoFlag by rememberSaveable { mutableStateOf(value = false) }
	Column(modifier = Modifier.fillMaxWidth()) {
		Row(
			modifier = modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically
		) {
			Text(text = hint, style = MaterialTheme.typography.titleSmall)
			if (infoState != null) {
				IconButton(
					onClick = { infoFlag = true }
				) {
					Icon(
						modifier = Modifier.size(24.dp),
						imageVector = Icons.TwoTone.Info,
						contentDescription = null,
						tint = MaterialTheme.colorScheme.secondary
					)
				}
			}
			Switch(enabled = !readOnly, checked = checked, onCheckedChange = onCheckedChange)
		}
		if (error.isNotBlank()) {
			Text(
				text = error,
				style = MaterialTheme.typography.titleSmall,
				color = MaterialTheme.colorScheme.error,
				textAlign = TextAlign.End,
				modifier = Modifier
					.fillMaxWidth()
					.then(errorModifier)
			)
		}
		if (infoFlag) {
			if (infoState != null) {
				val info = AppInfoState(
					message = infoState.message,
					header = infoState.header,
					table = infoState.table
				)
				AppInfoDialog(
					dismissFlag = { infoFlag = false },
					infoState = info
				)
			}
		}
	}
}