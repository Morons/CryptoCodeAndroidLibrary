
buildscript {
	repositories {
		google()
		mavenCentral()
		gradlePluginPortal()
		maven("https://jitpack.io")
	}
}

plugins {
	id("com.android.application") version "8.2.0" apply false
	id("org.jetbrains.kotlin.android") version "1.9.21" apply false
	id("com.android.library") version "8.2.0" apply false
	id("org.jetbrains.kotlin.plugin.serialization") version "1.9.21" apply false
	id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0" apply false
}