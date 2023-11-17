package za.co.zone.androidLib.components

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import io.ktor.util.decodeBase64Bytes

/**
 * This Requires Glide and ktor-client-core
 **/
@Composable
fun loadPicture(
	imageString: String,
	@DrawableRes defaultDrawable: Int? = null,
	@DrawableRes errorDrawable: Int? = null
): MutableState<Bitmap?> {
	
	val requestOptions = errorDrawable?.let { errorId ->
		RequestOptions.placeholderOf(defaultDrawable ?: errorId).error(errorId)
	}
	
	val bitmapState: MutableState<Bitmap?> = remember { mutableStateOf(value = null) }
	val byteArrayImage: ByteArray = imageString.decodeBase64Bytes()
	if (imageString.isNotBlank()) {
		if (requestOptions != null)
			Glide.with(LocalContext.current)
				.setDefaultRequestOptions(requestOptions)
				.asBitmap()
				.load(byteArrayImage)
				.transition(withCrossFade())
				.into(object : CustomTarget<Bitmap>() {
					override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
						bitmapState.value = resource
					}
					
					override fun onLoadCleared(placeholder: Drawable?) {
					}
				})
		else Glide.with(LocalContext.current)
			.asBitmap()
			.load(byteArrayImage)
			.transition(withCrossFade())
			.into(object : CustomTarget<Bitmap>() {
				override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
					bitmapState.value = resource
				}
				
				override fun onLoadCleared(placeholder: Drawable?) {
				}
			})
	} else {
		if (defaultDrawable != null) if (requestOptions != null) Glide.with(LocalContext.current)
			.setDefaultRequestOptions(requestOptions)
			.asBitmap()
			.load(defaultDrawable)
			.into(object : CustomTarget<Bitmap>() {
				override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
					bitmapState.value = resource
				}
				
				override fun onLoadCleared(placeholder: Drawable?) {
				}
			})
		else Glide.with(LocalContext.current).asBitmap().load(defaultDrawable).into(object : CustomTarget<Bitmap>() {
			override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
				bitmapState.value = resource
			}
			
			override fun onLoadCleared(placeholder: Drawable?) {
			}
		})
	}
	return bitmapState
}