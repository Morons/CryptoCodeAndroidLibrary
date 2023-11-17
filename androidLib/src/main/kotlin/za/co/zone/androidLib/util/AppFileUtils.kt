package za.co.zone.androidLib.util

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import za.co.zone.androidLib.util.VersionCheck.isAtLeastQ29
import java.io.*


object AppFileUtils {
	
	private const val TEMP_IMAGE_FILENAME = "tempImage.png"
	
	/**
	 * Before using you need to setup File Url Authority
	 **/
	fun getImageUriFromInternalStorage(context: Context, fileName: String, fileURLAuthority: String): Uri? {
		val imagePath = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "images")
		val file = File(imagePath, fileName)
		return context.let { FileProvider.getUriForFile(it, fileURLAuthority, file) }
	}
	
	fun Bitmap.saveImageToInternalStorage(context: Context, fileName: String) {
		
		val filesDir: File = File(context.filesDir, "images").apply { mkdirs() }
		val outputFile = File(filesDir, fileName.lowercase())
		val fileExtension = isFileNameExtension(outputFile)
		FileOutputStream(outputFile)
			.use { outputStream ->
				if (fileExtension == "jpg") {
					if (!this.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)) {
						throw IOException("Couldn't save bitmap.")
					}
				} else {
					if (!this.compress(Bitmap.CompressFormat.PNG, 100, outputStream)) {
						throw IOException("Couldn't save bitmap.")
					}
				}
			}
	}
	
	private fun isFileNameExtension(fileName: File): String {
		fileName.apply {
			return when (extension.lowercase()) {
				in "png" -> "png"
				in "jpg", "jpeg" -> "jpg"
				else -> "png"
			}
		}
	}
	
	
	fun Bitmap.saveImageToSharedMediaStore(context: Context): Uri? {
		val contentUri: Uri = if (isAtLeastQ29()) {
			MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
		} else {
			MediaStore.Images.Media.EXTERNAL_CONTENT_URI
		}
		val contentResolver = context.contentResolver
		val newImageDetails = ContentValues()
		newImageDetails.put(MediaStore.Images.Media.DISPLAY_NAME, TEMP_IMAGE_FILENAME)
		val imageContentUri = contentResolver.insert(contentUri, newImageDetails)
		try {
			contentResolver.openFileDescriptor(imageContentUri!!, "w", null).use { fileDescriptor ->
				val fd: FileDescriptor = fileDescriptor!!.fileDescriptor
				val outputStream: OutputStream = FileOutputStream(fd)
				val bufferedOutputStream = BufferedOutputStream(outputStream)
				this.compress(Bitmap.CompressFormat.PNG, 100, bufferedOutputStream)
				bufferedOutputStream.flush()
				bufferedOutputStream.close()
			}
		} catch (e: IOException) {
			Log.e("Android Library", "Error saving bitmap", e)
			return null
		}
		return imageContentUri
	}
	
}