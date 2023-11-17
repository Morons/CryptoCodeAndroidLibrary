package za.co.zone.androidLib.requests

import android.net.Uri

data class ReportShareIntentRequest(
	val messageName: String,
	val messageText: String,
	val contentUri: Uri? = null,
	val toRecipients: List<String?>? = null,
	val whatsAppNumber: String? = null,
)