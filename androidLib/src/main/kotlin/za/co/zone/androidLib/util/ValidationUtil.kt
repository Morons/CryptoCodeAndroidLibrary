package za.co.zone.androidLib.util


object ValidationUtil {
	
	private const val MIN_PASSWORD_LENGTH = 6
	private const val MIN_USER_NAME_LENGTH = 3
	
	private var emailManager = EmailManager()
	private var passwordManager = PasswordManager()
	
	fun validateEmail(email: String): AppFieldError? {
		val trimmedEmail = email.trim()
		if (trimmedEmail.isBlank()) return AppFieldError.AppFieldEmpty
		if (!emailManager.evaluateEmail(trimmedEmail)) return AppFieldError.InvalidEmail
		return null
	}
	
	fun validatePhoneNumber(phoneNumber: String): AppFieldError? {
		val trimmedPhone = phoneNumber.trim()
		if (trimmedPhone.isBlank()) return AppFieldError.AppFieldEmpty
		// TODO: test for Valid Phone numbers
		return null
	}
	
	fun validateNumber(number: Double): AppFieldError? {
		if (number.isNaN() || number <= 0.0) return AppFieldError.InvalidNumber
		return null
	}
	
	fun validateNumberScope(number: String, maxLimit: Double): AppFieldError? {
		return try {
			when {
				number.isBlank() -> AppFieldError.AppFieldEmpty
				!isDouble(number) -> AppFieldError.InvalidDouble
				number.toDouble() < 0.0 || number.toDouble() > maxLimit -> AppFieldError.InvalidNumberScope
				else -> null
			}
		} catch (e: NumberFormatException) {
			AppFieldError.InvalidDouble
		}
	}
	
	fun validateDoubleString(number: String): AppFieldError? {
		if (number.isBlank()) return AppFieldError.AppFieldEmpty
		return if (isDouble(number)) {
			null
		} else {
			AppFieldError.InvalidDouble
		}
	}
	
	private fun isInt(str: String): Boolean {
		return try {
			str.toInt()
			true
		} catch (e: NumberFormatException) {
			false
		}
	}
	
	fun validatePositiveIntString(number: String): AppFieldError? {
		if (number.isBlank()) return AppFieldError.AppFieldEmpty
		return if (isInt(number)) {
			if (number.toInt() <= 0) return AppFieldError.InvalidNumber
			else null
		} else {
			AppFieldError.InvalidNumber
		}
	}
	
	private fun isDouble(str: String): Boolean {
		return try {
			str.toDouble()
			true
		} catch (e: NumberFormatException) {
			false
		}
	}
	
	fun validateDoubleString(number: Double): AppFieldError? {
		if (number <= 0.0) return AppFieldError.NumberNegativeOrZero
		return if (number.isNaN()) {
			AppFieldError.InvalidDouble
		} else {
			null
		}
	}
	
	fun weightInputError(carcassWeight: String, carcassHangingWeight: String): AppFieldError? {
		if (carcassWeight.isBlank() || carcassWeight.isBlank()) return AppFieldError.AppFieldEmpty
		return when {
			carcassHangingWeight > carcassWeight -> AppFieldError.WeightInputError
			!isDouble(carcassHangingWeight) -> AppFieldError.InvalidDouble
			else -> null
		}
	}
	
	fun validateBoolean(value: Boolean, required: Boolean): AppFieldError? {
		if (value != required) return AppFieldError.InvalidBoolean
		return null
	}
	
	fun validateTextLength(text: String, length: Int): AppFieldError? {
		val trimmedText = text.trim()
		if (trimmedText.isBlank()) return AppFieldError.AppFieldEmpty
		if (trimmedText.length < length) return AppFieldError.InputTooShort
		return null
	}
	
	fun validateName(userName: String, minLength: Int = MIN_USER_NAME_LENGTH): AppFieldError? {
		
		val trimmedUserName = userName.trim()
		return if (trimmedUserName.isBlank()) AppFieldError.AppFieldEmpty
		else if ((trimmedUserName.length < minLength)) AppFieldError.InputTooShort
		else null
	}
	
	fun validateEmptyField(fieldText: String): AppFieldError? {
		
		val trimmedUserName = fieldText.trim()
		return if (trimmedUserName.isBlank()) AppFieldError.AppFieldEmpty
		else null
	}
	
	fun validatePassword(password: String, minLength: Int = MIN_PASSWORD_LENGTH): AppFieldError? {
		
		val trimmedPassword = password.trim()
		
		/* evaluatePassword: takes the password to test and returns a number from 0 to 1,
		* 0 is a very bad password and 1 is a perfect password.
		* */
		val evaluation = passwordManager.evaluatePassword(password)
		if (trimmedPassword.isBlank()) return AppFieldError.AppFieldEmpty
		if (trimmedPassword.length <= minLength) return AppFieldError.InputTooShort
		if (evaluation < 0.5F) return AppFieldError.InvalidPassword
		return null
	}
	
}