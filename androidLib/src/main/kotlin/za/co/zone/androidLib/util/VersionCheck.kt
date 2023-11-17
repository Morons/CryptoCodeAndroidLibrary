package za.co.zone.androidLib.util

import android.os.Build

object VersionCheck {
	
	fun isAtLeastQ28() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
	fun isAtLeastQ29() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
	fun isAtLeastQ30() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R
	fun isAtLeastQ31() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
	fun isAtLeastQ32() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S_V2
	fun isAtLeastQ33() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
	fun isAtLeastQ34() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE
}