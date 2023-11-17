package za.co.zone.androidLib.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolBar(
	onNavigateUp: () -> Unit = {},
	showBackArrow: Boolean = true,
	actions: @Composable RowScope.() -> Unit = {},
	toolBarTitle: @Composable () -> Unit = {}
) {
	TopAppBar(
		title = toolBarTitle,
		navigationIcon = {
			if (showBackArrow) {
					IconButton(onClick = { onNavigateUp() }) {
						Icon(imageVector = Icons.TwoTone.ArrowBack, contentDescription = "Back")
					}
			}
		},
		actions = actions
	)
}