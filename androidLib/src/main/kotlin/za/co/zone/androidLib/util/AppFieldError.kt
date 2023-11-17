package za.co.zone.androidLib.util

sealed class AppFieldError : Error() {
	object AppFieldEmpty : AppFieldError()
	object WeightInputError : AppFieldError()
	object NumberNegativeOrZero : AppFieldError()
	object InputTooShort : AppFieldError()
	object InvalidEmail : AppFieldError()
	object InvalidPassword : AppFieldError()
	object InvalidURL : AppFieldError()
	object InvalidDouble : AppFieldError()
	object InvalidNumber : AppFieldError()
	object InvalidNumberScope : AppFieldError()
	object InvalidBoolean : AppFieldError()
	object None : AppFieldError()
}