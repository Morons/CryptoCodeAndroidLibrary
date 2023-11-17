package za.co.zone.androidLib.requests

import android.graphics.Bitmap

data class SendBitmapIntentRequest(
	val messageName: String,
	val messageText: String,
	val messageType: String = "*/*",
	val contentBitmap: Bitmap
)