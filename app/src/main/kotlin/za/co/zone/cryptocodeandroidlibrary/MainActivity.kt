package za.co.zone.cryptocodeandroidlibrary

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import kotlinx.datetime.Clock.System.now
import za.co.zone.androidLib.util.*
import za.co.zone.cryptocodeandroidlibrary.Constants.GLOBAL_TAG
import za.co.zone.cryptocodeandroidlibrary.ui.theme.CryptoCodeAndroidLibraryTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			CryptoCodeAndroidLibraryTheme {
				// A surface container using the 'background' color from the theme
				Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
					Log.d(GLOBAL_TAG, "Instant.dateTimePeriod()=${now().dateTimePeriod()}")
					Log.d(GLOBAL_TAG, "nowToLongSystemDefault()=${nowToLongSystemDefault()}")
					Log.d(GLOBAL_TAG, "currentMoment()=${currentMoment()}")
					Log.d(GLOBAL_TAG, "getCurrentTimeInSeconds()=${getCurrentTimeInSeconds()}")
					Log.d(GLOBAL_TAG, "currentDayFormatted()=${currentDayFormatted()}")
					Log.d(GLOBAL_TAG, "currentMomentUtc()=${currentMomentUtc()}")
					Log.d(GLOBAL_TAG, "nowToLongUtc()=${nowToLongUtc()}")
					Log.d(GLOBAL_TAG, "nextMonthUtc()=${nextMonthUtc()}")
					Log.d(GLOBAL_TAG, "firstDayUtc()=${firstDayUtc()}")
					Log.d(GLOBAL_TAG, "lastDayUtc()=${lastDayUtc()}")
					Log.d(GLOBAL_TAG, "dateInUtc()=${dateInUtc()}")
					Log.d(GLOBAL_TAG, "in30DaysUtc()=${in30DaysUtc()}")
					Log.d(GLOBAL_TAG, "inXDaysUtc(days = 20)=${inXDaysUtc(days = 20)}")
					Log.d(GLOBAL_TAG, "past15DaysUtc()=${past15DaysUtc()}")
					Log.d(GLOBAL_TAG, "past60DaysUtc()=${past60DaysUtc()}")
					Log.d(GLOBAL_TAG, "pastXDaysUtc(days = 20)=${pastXDaysUtc(days = 20)}")
					Log.d(
						GLOBAL_TAG, "Long.relativeTimeSpanUtc() for this demo I did " +
								"pastXDaysUtc(days = 5).toEpochMilliseconds().relativeTimeSpanUtc()\n" +
								"=${pastXDaysUtc(days = 5).toEpochMilliseconds().relativeTimeSpanUtc()}"
					)
				}
			}
		}
	}
}