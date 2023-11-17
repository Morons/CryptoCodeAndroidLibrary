package za.co.zone.androidLib.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import za.co.zone.androidLib.states.AppInfoState


@Composable
fun AppDialog(
	infoState: AppInfoState? = null,
	onAction: () -> Unit,
	actionButtonText: String = "Yes",
	dismissFlag: (Boolean) -> Unit,
	dismissButtonText: String = "No",
	warningString: String,
	areYouSureString: String
) {
	var infoFlag by rememberSaveable { mutableStateOf(value = false) }
	
	Dialog(
		onDismissRequest = { dismissFlag(false) }
	) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.background(color = MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.large)
				.border(width = 1.dp, color = MaterialTheme.colorScheme.onSurface, shape = MaterialTheme.shapes.large)
				.padding(16.dp)
		) {
			Column(
				Modifier.fillMaxWidth(),
				verticalArrangement = Arrangement.Center,
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				Text(
					
					textAlign = TextAlign.Center,
					text = warningString,
					style = TextStyle(
						color = MaterialTheme.colorScheme.secondaryContainer,
						fontWeight = FontWeight.Bold,
						fontSize = 16.sp
					)
				)
				if (infoState != null) {
					Row(
						modifier = Modifier,
						horizontalArrangement = Arrangement.SpaceAround,
						verticalAlignment = Alignment.CenterVertically
					) {
						Text(text = "Please check this Info")
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
				}
				Text(
					text = areYouSureString,
					style = TextStyle(
						color = MaterialTheme.colorScheme.onSurface,
						fontWeight = FontWeight.Bold,
						fontSize = 16.sp
					)
				)
			}
			Row(
				Modifier
					.fillMaxWidth()
					.padding(vertical = 8.dp),
			) {
				OutlinedButton(
					modifier = Modifier
						.weight(1f)
						.padding(end = 4.dp),
					onClick = { dismissFlag(false) },
				) {
					Text(
						textAlign = TextAlign.Center,
						text = dismissButtonText
					)
				}
				OutlinedButton(
					modifier = Modifier
						.weight(1f)
						.padding(start = 4.dp),
					onClick = {
						dismissFlag(false)
						onAction()
					}
				) {
					Text(
						textAlign = TextAlign.Center,
						text = actionButtonText
					)
				}
			}
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