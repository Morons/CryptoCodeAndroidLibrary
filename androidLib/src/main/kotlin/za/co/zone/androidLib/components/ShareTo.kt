package za.co.zone.androidLib.components

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import za.co.zone.androidLib.requests.ReportShareIntentRequest
import za.co.zone.androidLib.util.shareTo

@Composable
fun ShareTo(
	messageName: String,
	messageText: String,
	contentUri: Uri?,
	toRecipients: List<String>,
	whatsAppNumber: String
) {
	LocalContext.current.shareTo(
		ReportShareIntentRequest(
			messageName = messageName,
			messageText = messageText,
			contentUri = contentUri,
			toRecipients = toRecipients,
			whatsAppNumber = whatsAppNumber
		)
	)
}