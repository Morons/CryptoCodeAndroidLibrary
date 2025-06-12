# by CryptoCode

## a Utility Library for Android Users

Initial Commit only include kotlinx.datetime utils, others will follow.

![Android](https://img.shields.io/badge/android-blue.svg?logo=android)
[![Kotlin](https://img.shields.io/badge/kotlin-2.1.21-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![JitPack](https://jitpack.io/v/Morons/CryptoCodeAndroidLibrary.svg)](https://jitpack.io/#Morons/CryptoCodeAndroidLibrary)

### Sample time related functions

**NOTEs:**
1. use .toEpochMilliseconds() to convert to Long values
2. **ALWAYS** store dates un UCT
3. for this demo I used `pastXDaysUtc(days = 5).toEpochMilliseconds().relativeTimeSpanUtc()` to get a Long TimeDate value 
4. for this demo I used `now()` (kotlinx.datetime.Clock.System.now) for Instant

* `Instant.dateTimePeriod()` = PT0.000324000S
* `nowToLongSystemDefault()` = 1700058378780
* `currentMoment()` = 2023-11-15T14:26:18.780031Z
* `getCurrentTimeInSeconds()` = 1700058378
* `currentDayFormatted()` = 2023 NOVEMBER 15
* `currentMomentUtc()` = 2023-11-15T14:26:18.780062Z
* `nowToLongUtc()` = 1700058378780
* `nextMonthUtc()` = 2023-12-01T00:00:00Z
* `firstDayUtc()` = 2023-11-01T00:00:00Z
* `lastDayUtc()` = 2023-11-30T00:00:00Z
* `dateInUtc()` = 2023-11-15
* `in30DaysUtc()` = 2023-12-15T14:26:18.780031Z
* `inXDaysUtc(days = 20)` = 2023-12-05T14:26:18.780031Z
* `past15DaysUtc()` = 2023-10-31T14:26:18.780031Z
* `past60DaysUtc()` = 2023-09-16T14:26:18.780031Z
* `pastXDaysUtc(days = 20)` = 2023-10-26T14:26:18.780031Z
* `Long.relativeTimeSpanUtc()` =  5 days ago

### Other Utils

* AppExtensions - a collection of Extension functions
* AppFileUtils - Internal and Media Storage Access, require setup of File Url Authority
* BitmapUtils - Several Bitmap related utilities, converting and decoding etc. see sources
* ContextExt - for Context related Extensions such as Share Intents etc, see [Share Utils] below
* EmailManager - simple regex email validator
* PasswordManager - notably generatePassword() and evaluatePassword() read javadocs
* Resource - Philipp Lackner's Resource Class
* UiText - Philipp Lackner's UiText Class (extended)
* UnitExt - Extension functions to serve Ui unit conversions and formatting
* ValidationUtil - various field input validations call these from ViewModel Events
* VersionCheck - making version checks easier.

### Compose Utils

* AppBooleanField()
* RowScope.AppBottomNavItem()
* AppDialog()
* AppInfoDialog()
* AppDropDownSelection()
* AppExpandingText()
* AppNavigationIcon()
* AppOptionSelection()
* AppTextField()
* AppToolBar()
* rememberWindowInfo()

### Share Utils
These are NOT Share intents but is helpers to those.  This part will be expanded soon.

* ShareTo()
* ShareToMailReport()
* ShareToWhatsAppReport()

### Known bugs/problems
AppTextField() still has a problem with Leading Icons, it is because if there is no leading Icon it still reserves space in 
front of the TextField for it.  This need to be replaced with a 0.dp sized Icon or sum thing, in progress

P.S. Request more Utilities if you need.

## Configuration

### Gradle - Kotlin

In your root build.gradle.kts

```kotlin
repositories {
	mavenCentral()
	maven("https://jitpack.io")
}
```
add the dependency
```kotlin
dependencies {
	implementation("com.github.Morons:CryptoCodeAndroidLibrary:1.0.6")
}
```
