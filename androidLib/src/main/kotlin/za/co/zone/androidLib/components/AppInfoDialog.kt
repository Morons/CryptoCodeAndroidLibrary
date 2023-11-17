package za.co.zone.androidLib.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import za.co.zone.androidLib.states.AppInfoState

/**
 * Use "".padEnd(length = 20) for example to make the table anf Header align properly
 * **/
@Composable
fun AppInfoDialog(
	dismissFlag: (Boolean) -> Unit,
	infoState: AppInfoState
) {
	val message = infoState.message
	val header = infoState.header
	val table = infoState.table
	AlertDialog(
		onDismissRequest = { dismissFlag(false) },
		icon = {
			Icon(
				Icons.TwoTone.Info, contentDescription = null,
				tint = MaterialTheme.colorScheme.secondary
			)
		},
		text = {
			Column {
				Text(
					text = message,
					fontWeight = FontWeight.Normal,
					textAlign = TextAlign.Justify
				)
				if (header.isNotBlank()) {
					Text(
						text = header,
						fontFamily = FontFamily.Monospace,
						fontWeight = FontWeight.Bold
					)
				}
				if (table.isNotEmpty()) {
					Text(
						text = table.joinToString(separator = ""),
						fontFamily = FontFamily.Monospace,
						fontWeight = FontWeight.Normal
					)
				}
			}
		},
		confirmButton = {
			OutlinedButton(
				onClick = { dismissFlag(false) }
			) {
				Text(
					text = "Ok"
				)
			}
		}
	)
}