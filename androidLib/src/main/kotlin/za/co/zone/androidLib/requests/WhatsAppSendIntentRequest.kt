package za.co.zone.androidLib.requests

import android.net.Uri

data class WhatsAppSendIntentRequest(
	val messageName: String,
	val messageText: String,
	val contentUri: Uri? = null,
	val whatsAppNumber: String? = null,
	val packageName: String = "com.whatsapp"
)