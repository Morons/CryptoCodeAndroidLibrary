plugins {
	id("com.android.library")
	id("org.jetbrains.kotlin.android")
	`maven-publish`
}

android {
	namespace = "za.co.zone.androidLib"
	compileSdk = 34
	
	defaultConfig {
		minSdk = 27
		
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		consumerProguardFiles("consumer-rules.pro")
	}
	
	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}
	}
	publishing {
		singleVariant(variantName = "release") {
			withSourcesJar()
		}
	}
	kotlin {
		jvmToolchain(jdkVersion = 17)
	}
}

publishing {
	publications {
		register<MavenPublication>(name = "release") {
			groupId = "com.github.Morons"
			artifactId = "android-library"
			version = "1.0"
			
			afterEvaluate {
				from(components["release"])
			}
		}
	}
}

dependencies {
	
	api("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")
	implementation("androidx.core:core-ktx:1.12.0")
	implementation("androidx.appcompat:appcompat:1.6.1")
	implementation("com.google.android.material:material:1.10.0")
	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.5")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}