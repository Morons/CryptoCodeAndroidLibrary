package za.co.zone.androidLib.components

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import za.co.zone.androidLib.states.*

/**
 * Meant to be used as a Selection Option list - Single choice
 * @param borderColor will be the border color of the clickable Box
 **/
@Composable
fun AppDropDownSelection(
	modifier: Modifier = Modifier,
	selectOptionList: List<SelectOptionListState>,
	hint: String,
	borderColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
	infoState: AppInfoState? = null,
	readOnly: Boolean = false,
	style: TextStyle = MaterialTheme.typography.bodySmall,
	divider: Boolean = true,
	onClick: (SelectedOptionState) -> Unit,
	selectedOption: SelectedOptionState
) {
	var expanded by rememberSaveable { mutableStateOf(value = false) }
	var infoFlag by rememberSaveable { mutableStateOf(value = false) }
	var pressOffset by remember { mutableStateOf(DpOffset.Zero) }
	var itemHeight by remember { mutableStateOf(0.dp) }
	var descriptionSelectFieldSize by remember { mutableStateOf(Size.Zero) }
	val interactionSource = remember { MutableInteractionSource() }
	val density = LocalDensity.current
	
	Box(
		modifier = modifier.onSizeChanged { itemHeight = with(density) { it.height.toDp() } },
		contentAlignment = Alignment.CenterStart
	) {
		Box(
			modifier = Modifier
				.fillMaxWidth()
				.background(color = MaterialTheme.colorScheme.background, shape = RoundedCornerShape(size = 4.dp))
				.indication(interactionSource = interactionSource, indication = LocalIndication.current)
				.pointerInput(key1 = true) {
					detectTapGestures(
						onPress = { offset ->
							expanded = true
							pressOffset = DpOffset(x = offset.x.toDp(), y = offset.y.toDp())
							val press = PressInteraction.Press(pressPosition = offset)
							interactionSource.emit(interaction = press)
							tryAwaitRelease()
							interactionSource.emit(PressInteraction.Release(press))
						}
					)
				}
				.onGloballyPositioned { descriptionSelectFieldSize = it.size.toSize() }
				.clip(shape = RoundedCornerShape(4.dp))
		) {
			AppTextField(
				value = selectedOption.text.ifEmpty { hint },
				readOnly = selectedOption.text.isNotBlank(),
				hint = hint,
				dropdownFlag = true,
				leadingIcon = if (infoState != null) Icons.TwoTone.Info else null,
				leadingIconTint = MaterialTheme.colorScheme.secondaryContainer,
				onLeadingButtonClick = { infoFlag = true },
				trailingIcon = if (expanded) Icons.TwoTone.KeyboardArrowUp else Icons.TwoTone.KeyboardArrowDown,
				onTrailingButtonClick = { expanded = true },
				borderColor = borderColor
			)
		}
		if (!readOnly) {
			DropdownMenu(
				expanded = expanded,
				onDismissRequest = { expanded = false },
				modifier = Modifier.width(with(LocalDensity.current) { descriptionSelectFieldSize.width.toDp() - 125.dp }),
				offset = pressOffset.copy(y = pressOffset.y - itemHeight)
			) {
				selectOptionList.forEachIndexed { index, option ->
					DropdownMenuItem(
						text = {
							Column(
								modifier = Modifier.fillMaxWidth()
							) {
								Box(
									modifier = modifier
										.padding(vertical = 8.dp)
										.onSizeChanged { itemHeight = with(density) { it.height.toDp() } },
									contentAlignment = Alignment.CenterStart
								) {
									if (option.icon != null)
										Image(
											modifier = Modifier
												.size(24.dp)
												.padding(end = 4.dp),
											imageVector = option.icon,
											contentDescription = null
										)
									Column {
										Text(
											modifier = Modifier.padding(horizontal = 4.dp),
											text = option.text,
											overflow = TextOverflow.Ellipsis,
											style = style
										)
										if (option.subText.isNotBlank()) {
											Text(
												modifier = Modifier.padding(horizontal = 4.dp),
												text = option.subText,
												style = style.copy(fontWeight = FontWeight.Bold)
											)
										}
									}
								}
								if (divider) {
									if (index < selectOptionList.size - 1) Divider(
										modifier = Modifier.padding(top = 8.dp),
										thickness = Dp.Hairline,
										color = MaterialTheme.colorScheme.onSurface
									)
								}
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
		if (infoFlag) {
			if (infoState != null) {
				val info = AppInfoState(
					message = infoState.message,
					header = infoState.header,
					table = infoState.table
				)
				AppInfoDialog(
					dismissFlag = { infoFlag = false },
					infoState = info,
				)
			}
		}
	}
}