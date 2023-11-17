package za.co.zone.androidLib.util

import android.annotation.SuppressLint
import android.content.*
import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.pdf.PdfDocument
import android.net.*
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import za.co.zone.androidLib.util.AppFileUtils.saveImageToSharedMediaStore
import za.co.zone.androidLib.requests.*
import java.io.*

fun Context.openUrlInBrowser(url: String) {
	val intent = Intent(Intent.ACTION_VIEW).apply {
		data = Uri.parse(url)
	}
	startActivity(intent)
}

fun Context.showKeyboard() {
	val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
	imm?.showSoftInput(null, SHOW_IMPLICIT)
}

/**
 * text/plain, text/rtf, text/html, text/json, receivers should register for text&#47;&#42;
 * image/jpg, image/png, image/gif, receivers should register for image&#47;&#42;
 * video/mp4, video/3gp, receivers should register for video&#47;&#42;
 * application/pdf, receivers should register for supported file extensions
 * Generic Share Intent
 **/

fun Context.createBitmapShareIntent(input: SendBitmapIntentRequest) {
	val messageName = input.messageName
	val messageText = input.messageText
	val messageType = input.messageType
	val imageBitmap = input.contentBitmap
	val contentUri = imageBitmap.saveImageToSharedMediaStore(context = this)

	val sendIntent = Intent().apply {
		action = Intent.ACTION_SEND
		type = messageType
		putExtra(Intent.EXTRA_SUBJECT, messageName)
		putExtra(Intent.EXTRA_TEXT, messageText)
		flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
				Intent.FLAG_GRANT_WRITE_URI_PERMISSION or
				Intent.FLAG_ACTIVITY_NEW_DOCUMENT
		clipData = ClipData.newRawUri(messageName, contentUri)
		putExtra(Intent.EXTRA_STREAM, contentUri)
	}
	if (sendIntent.resolveActivity(packageManager) != null) {
		startActivity(Intent.createChooser(sendIntent, "Send To:"))
	}
}

fun Context.createShareIntentToWhatsApp(sendPdfIntentRequest: WhatsAppSendIntentRequest) {
	val sendIntent = Intent().apply {
		with(sendPdfIntentRequest) {
			action = Intent.ACTION_SEND
			type = "*/*"
			clipData = ClipData.newRawUri(messageName, contentUri)
			flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
					Intent.FLAG_GRANT_WRITE_URI_PERMISSION or
					Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
					Intent.FLAG_ACTIVITY_NEW_TASK
			putExtra("jid", "$whatsAppNumber@s.whatsapp.net")
			putExtra(Intent.EXTRA_SUBJECT, messageName)
			putExtra(Intent.EXTRA_TITLE, messageName)
			putExtra(Intent.EXTRA_TEXT, messageText)  // whatsapp Text
			putExtra(Intent.EXTRA_STREAM, contentUri)
			setPackage(packageName)
		}
	}
	startActivity(Intent.createChooser(sendIntent, "Send To:"))
}

fun Context.shareTo(reportShareIntentRequest: ReportShareIntentRequest) {
	val messageName = reportShareIntentRequest.messageName
	val messageText = reportShareIntentRequest.messageText
	val contentUri = reportShareIntentRequest.contentUri
	val toRecipients = reportShareIntentRequest.toRecipients
	val whatsAppNumber = reportShareIntentRequest.whatsAppNumber
	val sendIntent: Intent = Intent().apply {
		action = Intent.ACTION_SEND
		type = "application/pdf"  // Works no Caption
//		type = "*/*"   // Works no Caption
		putExtra(Intent.EXTRA_SUBJECT, messageName) // clipData dominate this
		putExtra(Intent.EXTRA_EMAIL, toRecipients?.toTypedArray())
		putExtra("jid", "$whatsAppNumber@s.whatsapp.net")
		putExtra(Intent.EXTRA_TITLE, messageName) // clipData dominate this
//		putExtra(Intent.EXTRA_SUBJECT, messageText) // clipData dominate this
		putExtra(Intent.EXTRA_TEXT, messageText) // clipData dominate this
		flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
				Intent.FLAG_GRANT_WRITE_URI_PERMISSION or
				Intent.FLAG_ACTIVITY_NEW_DOCUMENT
		clipData = ClipData.newRawUri(messageName, contentUri)
		putExtra(Intent.EXTRA_STREAM, contentUri)
	}
	val shareIntent = Intent.createChooser(sendIntent, "Send to")
	startActivity(shareIntent)
}

fun Context.shareToMail(mailSendIntentRequest: MailSendIntentRequest) {
	val dataIntent = getEmailDataIntent(mailSendIntentRequest)
	val targetIntent = Intent.createChooser(dataIntent, "Send to")
	startActivity(targetIntent)
}

private fun getEmailDataIntent(mailSendIntentRequest: MailSendIntentRequest): Intent {

	return Intent().apply {
		with(mailSendIntentRequest) {
			action = Intent.ACTION_SEND
			type = "message/rfc822"
			putExtra(Intent.EXTRA_SUBJECT, messageName)
			putExtra(Intent.EXTRA_EMAIL, toRecipients?.toTypedArray())
			putExtra(Intent.EXTRA_TEXT, messageText)
			putExtra(Intent.EXTRA_STREAM, contentUri)
			flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
					Intent.FLAG_GRANT_WRITE_URI_PERMISSION or
					Intent.FLAG_ACTIVITY_NEW_DOCUMENT
			clipData = ClipData.newRawUri(messageName, contentUri)
			setPackage(packageName)
		}
	}
}

@SuppressLint("MissingPermission")
fun Context.isOnline(): Boolean {
	val result: Boolean
	val connectivityManager = ContextCompat.getSystemService(this, ConnectivityManager::class.java)
	val networkCapabilities = connectivityManager?.activeNetwork ?: return false
	val capabilities = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
	result = when {
		capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
		capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
		capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
		capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) && capabilities.hasCapability(
			NetworkCapabilities.NET_CAPABILITY_VALIDATED
		) -> true

		else -> false
	}
	return result
}

@SuppressLint("MissingPermission")
fun Context.connectionType(): String {
	val connectivityManager = ContextCompat.getSystemService(this, ConnectivityManager::class.java)
	val networkCapabilities = connectivityManager?.activeNetwork ?: return ""
	val capabilities = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return ""
	return when {
		capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "WiFi"
		capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "Cellular"
		capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> "Ethernet"
		else -> "Other"
	}
}


/**
 * You need to setup File URL Authority before using this
 **/
fun Context.getPdfFromCache(fileName: String, fileURLAuthority: String): Uri? {
	val pdfsDir = File(this.cacheDir, "pdfs")
	val file = File(pdfsDir, fileName)
	return this.let { FileProvider.getUriForFile(it, fileURLAuthority, file) }
}

fun Context.writePdfToCache(pdfDocument: PdfDocument, fileName: String): Boolean {
	var valid = false
	val pdfsDir = File(this.cacheDir, "pdfs").apply { mkdirs() }
	val file = File(pdfsDir, fileName)
	val fileOutputStream: OutputStream? = try {
		FileOutputStream(file)
	} catch (exception: Exception) {
		Log.e("Android Library", "Context.writePdfToCache: ${exception.localizedMessage}")
		null
	}
	if (fileOutputStream != null) {
		try {
			pdfDocument.writeTo(fileOutputStream)
			valid = true
		} catch (ioException: IOException) {
			Log.e("Android Library", "Context.writePdfToCache: ${ioException.localizedMessage}")
		}
	}
	fileOutputStream?.close()
	pdfDocument.close()
	return valid
}