package za.co.zone.androidLib.util

import android.text.format.DateUtils
import kotlinx.datetime.*
import kotlinx.datetime.Clock.System.now
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days

private val currentMoment: Instant = now()  // Current Clock time of Device
private val currentMomentUtc: Instant = now()

private val datetimeInUtc: LocalDateTime = currentMoment.toLocalDateTime(TimeZone.UTC)
private val dateInUtc: LocalDate = datetimeInUtc.date
private val datetimeInSystemZone: LocalDateTime = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())
private val monthNumber: Int = dateInUtc.monthNumber
private val dayOfMonthNumber: Int = dateInUtc.dayOfMonth
private val yearNumber: Int = dateInUtc.year
private val time: LocalTime = datetimeInUtc.time
private val firstDayUtc: Instant = Instant.parse(
	isoString = "${yearNumber.toString().padStart(length = 4, padChar = '0')}-${
		monthNumber.toString().padStart(length = 2, padChar = '0')
	}-01T00:00:00Z"
)
private val nextMonth: Instant = firstDayUtc.plus(value = 1, unit = DateTimeUnit.MONTH, timeZone = TimeZone.UTC)
private val lastDay: Instant = nextMonth.minus(value = 1, unit = DateTimeUnit.DAY, timeZone = TimeZone.UTC)
private val durationToNextMonth: Duration = nextMonth - currentMoment

fun Instant.dateTimePeriod(): DateTimePeriod = this.periodUntil(now(), TimeZone.UTC)
fun nowToLongSystemDefault(): Long =
	datetimeInSystemZone.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()

fun currentMoment(): Instant = currentMoment
fun getCurrentTimeInSeconds(): Int = currentMomentUtc().epochSeconds.toInt()
fun currentDayFormatted(): String =
	with(currentMoment().toLocalDateTime(TimeZone.currentSystemDefault())) { "$year $month $dayOfMonth" }

fun currentMomentUtc(): Instant = currentMomentUtc
fun nowToLongUtc(): Long = datetimeInUtc.toInstant(TimeZone.UTC).toEpochMilliseconds()
fun nextMonthUtc(): Instant = currentMoment + durationToNextMonth
fun firstDayUtc(): Instant = firstDayUtc
fun lastDayUtc(): Instant = lastDay
fun dateInUtc(): LocalDate = dateInUtc
fun in30DaysUtc(): Instant = datetimeInUtc.toInstant(TimeZone.UTC) + 30.days
fun inXDaysUtc(days: Int): Instant = datetimeInUtc.toInstant(TimeZone.UTC) + days.days
fun past15DaysUtc(): Instant = (datetimeInUtc.toInstant(TimeZone.UTC) - 15.days)
fun past60DaysUtc(): Instant = (datetimeInUtc.toInstant(TimeZone.UTC) - 60.days)
fun pastXDaysUtc(days: Int): Instant = (datetimeInUtc.toInstant(TimeZone.UTC) - days.days)

/**
 * Returns a string describing 'time' as a time relative to 'now'.
 * Time spans in the past are formatted like "42 minutes ago".
 * Time spans in the future are formatted like "In 42 minutes".
 * 1653989297297.relativeTimeSpan()
 * @see getRelativeTimeSpanString
 **/
fun Long.relativeTimeSpanUtc(): String {
	val time = if (this.toString().length == 13) this else this * 1000
	return getRelativeTimeSpanString(
		time = time,
		now = nowToLongUtc(),
		minResolution = DateUtils.SECOND_IN_MILLIS
	).toString()
}

/**
 * Returns a string describing 'time' as a time relative to 'now'.
 * <p>
 * Time spans in the past are formatted like "42 minutes ago".
 * Time spans in the future are formatted like "In 42 minutes".
 *
 * @param time, supplied via Extension, the time to describe, in milliseconds
 * @param now the current time in milliseconds
 * @param minResolution the minimum time-span to report. For example, a time 3 seconds in the
 *     past will be reported as "0 minutes ago" if this is set to MINUTE_IN_MILLIS. Pass one of
 *     0, MINUTE_IN_MILLIS, HOUR_IN_MILLIS, DAY_IN_MILLIS, WEEK_IN_MILLIS
 *
 **/
fun getRelativeTimeSpanString(time: Long, now: Long, minResolution: Long): CharSequence? {
	val flags = DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR or DateUtils.FORMAT_ABBREV_MONTH
	return DateUtils.getRelativeTimeSpanString(time, now, minResolution, flags)
}