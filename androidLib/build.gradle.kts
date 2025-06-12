plugins {
	id("com.android.library")
	id("org.jetbrains.kotlin.android")
	id("org.jetbrains.kotlin.plugin.compose")
	`maven-publish`
}

android {
	namespace = "za.co.zone.lib"
	compileSdk = 36
	
	defaultConfig {
		minSdk = 27
		
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		consumerProguardFiles("consumer-rules.pro")
	}
	
	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile(name = "proguard-android-optimize.txt"), "proguard-rules.pro")
		}
	}
	publishing {
		singleVariant(variantName = "release") {
			withSourcesJar()
		}
	}
	kotlin {
		jvmToolchain(jdkVersion = 21)
	}
	buildFeatures {
		compose = true
	}
	composeOptions {
		kotlinCompilerExtensionVersion = "1.5.15"
	}
}

publishing {
	publications {
		register<MavenPublication>(name = "release") {
			groupId = "com.github.Morons"
			artifactId = "android-library"
			version = "1.0.9"
			
			afterEvaluate {
				from(components["release"])
			}
		}
	}
}

dependencies {
	
	implementation("io.ktor:ktor-client-core:3.1.3")
	implementation("com.github.bumptech.glide:glide:4.16.0")
	annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")
	implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")
	implementation("org.mongodb:bson:5.5.1")
	implementation("androidx.core:core-ktx:1.16.0")
	implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.9.1")
	implementation("androidx.activity:activity-compose:1.10.1")
	implementation(platform("androidx.compose:compose-bom:2025.06.00"))
	implementation("androidx.compose.ui:ui")
	implementation("androidx.compose.ui:ui-graphics")
	implementation("androidx.compose.ui:ui-tooling-preview")
	implementation("androidx.compose.material3:material3:1.3.2")
	implementation("androidx.compose.material:material-icons-core")
	implementation("androidx.compose.material:material-icons-extended:1.7.8")
	testImplementation("org.jetbrains.kotlin:kotlin-test:2.1.21")
	androidTestImplementation("androidx.test.ext:junit:1.2.1")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
	androidTestImplementation(platform("androidx.compose:compose-bom:2025.06.00"))
	androidTestImplementation("androidx.compose.ui:ui-test-junit4")
	debugImplementation("androidx.compose.ui:ui-tooling")
	debugImplementation("androidx.compose.ui:ui-test-manifest:1.8.2")
	
}