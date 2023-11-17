package za.co.zone.androidLib.requests

import android.net.Uri

data class MailSendIntentRequest(
	val messageName: String,
	val messageText: String,
	val contentUri: Uri? = null,
	val toRecipients: List<String?>? = null,
	val packageName: String = "com.google.android.gm"
)