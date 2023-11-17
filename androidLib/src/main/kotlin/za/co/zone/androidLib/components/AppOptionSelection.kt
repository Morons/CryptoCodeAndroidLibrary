package za.co.zone.androidLib.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import za.co.zone.androidLib.states.SelectOptionListState
import za.co.zone.androidLib.states.SelectedOptionState

/**
 * Meant to be used as menu
 * **/
@Composable
fun AppOptionSelection(
	modifier: Modifier = Modifier,
	selectOptionList: List<SelectOptionListState>,
	offset: DpOffset = DpOffset.Zero,
	onDismissRequest: (Boolean) -> Unit,
	onClick: (SelectedOptionState) -> Unit,
	visible: Boolean
) {
	var expanded by rememberSaveable { mutableStateOf(value = visible) }
	var itemHeight by remember { mutableStateOf(0.dp) }
	val density = LocalDensity.current
	val configuration = LocalConfiguration.current
	val screenWidth = configuration.screenWidthDp.dp
	
	Box {
		DropdownMenu(
			expanded = expanded,
			onDismissRequest = {
				onDismissRequest(false)
				expanded = false
			},
			modifier = Modifier.width(screenWidth / 2),
			offset = offset
		) {
			selectOptionList.forEachIndexed { index, option ->
				DropdownMenuItem(
					text = {
						Column(
							modifier = Modifier.fillMaxWidth()
						) {
							Box(
								modifier = modifier.onSizeChanged { itemHeight = with(density) { it.height.toDp() } }
							) {
								Row(
									verticalAlignment = CenterVertically
								) {
									if (option.icon != null)
										Image(
											modifier = Modifier
												.size(24.dp)
												.padding(end = 4.dp),
											imageVector = option.icon,
											contentDescription = null
										)
									Column(
										modifier = Modifier
											.padding(8.dp)
											.padding(horizontal = 4.dp)
									) {
										Text(
											modifier = Modifier,
											text = option.text,
											overflow = TextOverflow.Ellipsis,
											style = MaterialTheme.typography.bodySmall
										)
										if (option.subText.isNotBlank()) {
											Text(
												text = option.subText,
												style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
											)
										}
									}
								}
							}
							if (index < selectOptionList.size - 1) Divider(
								modifier = Modifier.padding(top = 8.dp),
								thickness = Dp.Hairline,
								color = MaterialTheme.colorScheme.onSurface
							)
						}
					},
					onClick = {
						onClick(SelectedOptionState(id = option.id, text = option.text))
						expanded = false
					}
				)
			}
		}
	}
}