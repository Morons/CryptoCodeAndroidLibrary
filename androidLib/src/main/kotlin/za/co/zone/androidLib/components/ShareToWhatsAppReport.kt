package za.co.zone.androidLib.components

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import za.co.zone.androidLib.requests.WhatsAppSendIntentRequest
import za.co.zone.androidLib.util.createShareIntentToWhatsApp

@Composable
fun ShareToWhatsAppReport(
	messageName: String,
	messageText: String,
	contentUri: Uri?,
	whatsAppNumber: String
) {
	LocalContext.current.createShareIntentToWhatsApp(
		WhatsAppSendIntentRequest(
			messageName = messageName,
			messageText = messageText,
			contentUri = contentUri,
			whatsAppNumber = whatsAppNumber
		)
	)
}