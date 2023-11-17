package za.co.zone.androidLib.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Visibility
import androidx.compose.material.icons.twotone.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * @param trailingIcon if null then the button is disabled!
 * @param keyboardOptions = KeyboardType.Password, enables password entry, param trailingIcon takes precedence!
 **/
@Composable
fun AppTextField(
	modifier: Modifier = Modifier,
	value: String = "",
	focusRequester: FocusRequester = FocusRequester(),
	readOnly: Boolean = false,
	textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
	hint: String = "",
	singleLine: Boolean = true,
	maxLines: Int = 1,
	error: String = "",
	dropdownFlag: Boolean = false,
	leadingIcon: ImageVector? = null,
	leadingIconTint: Color = Color.Unspecified,
	onLeadingButtonClick: () -> Unit = {},
	trailingIcon: ImageVector? = null,
	trailingIconTint: Color = Color.Unspecified,
	onTrailingButtonClick: () -> Unit = {},
	keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
	keyboardActions: KeyboardActions = KeyboardActions.Default,
	isPasswordVisible: Boolean = false,
	isPasswordToggleDisplayed: Boolean = keyboardOptions.keyboardType == KeyboardType.Password,
	onPasswordToggleClick: (Boolean) -> Unit = {},
	onValueChange: (String) -> Unit = {},
	borderColor: Color = MaterialTheme.colorScheme.primary,
	colors: TextFieldColors = OutlinedTextFieldDefaults.colors()
) {
	Column(
		modifier = modifier.fillMaxWidth()
	) {
		if (readOnly || dropdownFlag) {
			Box(
				modifier = modifier.then(
					Modifier
						.fillMaxWidth()
						.padding(vertical = 6.dp)
						.border(
							width = 1.dp,
							color = borderColor,
							shape = RoundedCornerShape(size = 4.dp)
						)
				),
				contentAlignment = Alignment.CenterStart
			) {
				val endPadding = if (!dropdownFlag) 16.dp else 0.dp
				Row(
					modifier = Modifier
						.fillMaxWidth()
						.requiredHeight(6.dp)
						.padding(start = 16.dp, end = endPadding)
						.padding(vertical = 6.dp),
					horizontalArrangement = Arrangement.SpaceBetween,
					verticalAlignment = Alignment.CenterVertically
				) {
					Text(
						modifier = Modifier.weight(1f),
						text = hint
					)
					Text(
						text = value,
						style = textStyle.copy(fontWeight = FontWeight.Bold)
					)
					if (leadingIcon != null) {
						IconButton(
							onClick = { onLeadingButtonClick() }
						) {
							Icon(
								imageVector = leadingIcon,
								contentDescription = null,
								modifier = Modifier.size(24.dp),
								tint = leadingIconTint
							)
						}
					}
					if (trailingIcon != null) {
						IconButton(
							onClick = { onTrailingButtonClick() }
						) {
							Icon(
								imageVector = trailingIcon,
								contentDescription = null,
								modifier = Modifier.size(24.dp),
								tint = trailingIconTint
							)
						}
					} else if (isPasswordToggleDisplayed) {
						IconButton(
							onClick = { onPasswordToggleClick(!isPasswordVisible) },
							modifier = Modifier.semantics { testTag = "password_toggle" }
						) {
							Icon(
								imageVector = if (isPasswordVisible) {
									Icons.TwoTone.VisibilityOff
								} else {
									Icons.TwoTone.Visibility
								},
								tint = if (isPasswordVisible)
									MaterialTheme.colorScheme.secondary
								else MaterialTheme.colorScheme.primary,
								contentDescription = if (isPasswordVisible)
									"Hide Password"
								else "Show Password"
							)
						}
					}
				}
			}
		} else {
			OutlinedTextField(
				value = value,
				onValueChange = { onValueChange(it) },
				modifier = Modifier
					.fillMaxWidth()
					.semantics { testTag = "standard_text_field" }
					.focusRequester(focusRequester = focusRequester),
				textStyle = textStyle,
				label = {
					Text(
						text = hint,
						style = MaterialTheme.typography.titleSmall
					)
				},
				placeholder = {
					Text(
						text = hint,
						style = MaterialTheme.typography.titleMedium
					)
				},
				// FIXME: https://github.com/material-components/material-components-android/issues/3528
				leadingIcon = {
					if (leadingIcon != null) {
						IconButton(onClick = { onLeadingButtonClick() }) {
							Icon(
								imageVector = leadingIcon,
								contentDescription = null,
								modifier = Modifier.size(24.dp)
							)
						}
					} else {}
				},
				trailingIcon = {
					if (trailingIcon != null) {
						IconButton(
							onClick = { onTrailingButtonClick() }
						) {
							Icon(
								imageVector = trailingIcon,
								contentDescription = null,
								modifier = Modifier.size(32.dp),
								tint = trailingIconTint
							)
						}
					} else if (isPasswordToggleDisplayed) {
						IconButton(
							onClick = { onPasswordToggleClick(!isPasswordVisible) },
							modifier = Modifier.semantics { testTag = "password_toggle" }
						) {
							Icon(
								imageVector = if (isPasswordVisible) {
									Icons.TwoTone.VisibilityOff
								} else {
									Icons.TwoTone.Visibility
								},
								tint = if (isPasswordVisible) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
								contentDescription = if (isPasswordVisible)
									"Hide Password"
								else "Show Password"
							)
						}
					}
				},
				isError = error != "",
				visualTransformation = if (!isPasswordVisible && isPasswordToggleDisplayed) {
					PasswordVisualTransformation()
				} else {
					VisualTransformation.None
				},
				keyboardOptions = keyboardOptions,
				keyboardActions = keyboardActions,
				singleLine = singleLine,
				maxLines = maxLines,
				colors = colors
			)
		}
		if (error.isNotBlank()) {
			Text(
				text = error,
				style = MaterialTheme.typography.titleSmall,
				color = MaterialTheme.colorScheme.error,
				textAlign = TextAlign.End,
				modifier = Modifier.fillMaxWidth()
			)
		}
	}
}