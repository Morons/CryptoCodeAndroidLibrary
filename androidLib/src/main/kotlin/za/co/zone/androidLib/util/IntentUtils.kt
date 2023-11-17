package za.co.zone.androidLib.util

import android.content.*
import android.content.pm.PackageManager
import za.co.zone.androidLib.util.VersionCheck.isAtLeastQ33

object IntentUtils {
	fun getSelectiveIntentChooser(context: Context, queryIntent: Intent?, dataIntent: Intent?): Intent? {
		val appList =
			if (isAtLeastQ33()) {
				context.packageManager.queryIntentActivities(
					queryIntent!!,
					PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_DEFAULT_ONLY.toLong())
				)
			} else {
				context.packageManager.queryIntentActivities(
					queryIntent!!,
					PackageManager.GET_META_DATA
				)
			}
		var finalIntent: Intent? = null
		if (appList.isNotEmpty()) {
			val targetedIntents: MutableList<Intent> = ArrayList()
			for (resolveInfo in appList) {
				val packageName = if (resolveInfo.activityInfo != null) resolveInfo.activityInfo.packageName else null
				val allowedIntent = Intent(dataIntent)
				allowedIntent.component = ComponentName(packageName!!, resolveInfo.activityInfo.name)
				allowedIntent.setPackage(packageName)
				targetedIntents.add(allowedIntent)
			}
			if (targetedIntents.isNotEmpty()) {
				//Share Intent
				val startIntent = targetedIntents.removeAt(index = 0)
				val chooserIntent = Intent.createChooser(startIntent, "")
				chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedIntents.toTypedArray())
				chooserIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
				finalIntent = chooserIntent
			}
		}
		if (finalIntent == null) finalIntent = dataIntent // As a fallback, we are using the sent data intent
		
		return finalIntent
	}
}