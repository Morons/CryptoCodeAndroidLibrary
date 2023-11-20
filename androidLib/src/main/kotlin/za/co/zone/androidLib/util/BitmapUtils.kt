package za.co.zone.androidLib.util

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Base64.DEFAULT
import android.util.Base64.decode
import androidx.annotation.DrawableRes
import org.bson.BsonBinarySubType
import org.bson.types.Binary
import java.io.*
import java.util.*

@Throws(IOException::class)
fun Uri.getByteArrayStringFromUri(context: Context): String {
	val iStream: InputStream? = context.contentResolver.openInputStream(this)
	val imageByteArray: ByteArray? = iStream?.readBytes()
	iStream?.close()
	return Base64.getEncoder().encodeToString((Binary(BsonBinarySubType.BINARY, imageByteArray).data))
}

fun String.decodeImageString(): ByteArray = decode(this, DEFAULT)

fun ByteArray.toBitmap(): Bitmap? =
	if (isNotEmpty()) BitmapFactory.decodeByteArray(this, 0, this.size) else null

fun String.toBitmap(): Bitmap? =
	if (this.isNotEmpty()) this.decodeImageString().toBitmap() else null

fun Context.drawableToBitmap(@DrawableRes drawable: Int): Bitmap =
	BitmapFactory.decodeResource(resources, drawable).copy(Bitmap.Config.ARGB_8888, true)

fun Drawable.toBitmap(): Bitmap {
	if (this is BitmapDrawable) {
		return this.bitmap
	}
	val bitmap = Bitmap.createBitmap(this.intrinsicWidth, this.intrinsicHeight, Bitmap.Config.ARGB_8888)
	val canvas = Canvas(bitmap)
	this.setBounds(0, 0, canvas.width, canvas.height)
	this.draw(canvas)
	return bitmap
}