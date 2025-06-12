
buildscript {
	repositories {
		google()
		mavenCentral()
		gradlePluginPortal()
		maven("https://jitpack.io")
	}
}

plugins {
	id("com.android.application") version "8.10.1" apply false
	id("org.jetbrains.kotlin.android") version "2.1.21" apply false
	id("org.jetbrains.kotlin.plugin.compose") version "2.1.21" apply false
	id("com.android.library") version "8.10.1" apply false
	id("org.jetbrains.kotlin.plugin.serialization") version "2.1.21" apply false
	id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0" apply false
}