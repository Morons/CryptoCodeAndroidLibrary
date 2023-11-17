package za.co.zone.androidLib.components

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import za.co.zone.androidLib.requests.MailSendIntentRequest
import za.co.zone.androidLib.util.shareToMail

@Composable
fun ShareToMailReport(
	messageName: String,
	messageText: String,
	contentUri: Uri?,
	toRecipients: List<String>
) {
	LocalContext.current.shareToMail(
		MailSendIntentRequest(
			messageName = messageName,
			messageText = messageText,
			contentUri = contentUri,
			toRecipients = toRecipients
		)
	)
}