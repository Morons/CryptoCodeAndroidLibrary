package za.co.zone.androidLib.util

import java.util.regex.Matcher
import java.util.regex.Pattern

class EmailManager {

	// https://regex101.com/r/hGcSQu/1
	private val regex =
		"([A-Za-z0-9!#-'*+\\-/=?^_`{-~\\xA0-\\x{10FFFF}]+(?:\\.[A-Za-z0-9!#-'*+\\-/=?^_`{-~\\xA0-\\x{10FFFF}])|(?:[ !#-\\[\\]-~\\xA0-\\x{10FFFF}]|\\\\[ -~])*)@((?:[A-Za-z0-9](?:[A-Za-z0-9\\-]*[A-Za-z0-9])?|[\\x00-\\x{10FFFF}]*[\\x80-\\x{10FFFF}]+[\\x00-\\x{10FFFF}]*)(?:\\.(?:[A-Za-z0-9](?:[A-Za-z0-9\\-]*[A-Za-z0-9])?|[\\x00-\\x{10FFFF}]*[\\x80-\\x{10FFFF}]+[\\x00-\\x{10FFFF}]*))*)"
	private val emailAddressPattern = Pattern.compile(regex)

	fun evaluateEmail(mailAddressToTest: String): Boolean {
		val matcher: Matcher = emailAddressPattern.matcher(mailAddressToTest)
		return matcher.matches()
	}
}