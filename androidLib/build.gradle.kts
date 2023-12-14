plugins {
	id("com.android.library")
	id("org.jetbrains.kotlin.android")
	`maven-publish`
}

android {
	namespace = "za.co.zone.lib"
	compileSdk = 34
	
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
		jvmToolchain(jdkVersion = 17)
	}
	buildFeatures {
		compose = true
	}
	composeOptions {
		kotlinCompilerExtensionVersion = "1.5.6"
	}
}

publishing {
	publications {
		register<MavenPublication>(name = "release") {
			groupId = "com.github.Morons"
			artifactId = "android-library"
			version = "1.0.7"
			
			afterEvaluate {
				from(components["release"])
			}
		}
	}
}

dependencies {
	
	implementation("io.ktor:ktor-client-core:2.3.7")
	implementation("com.github.bumptech.glide:glide:4.16.0")
	annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")
	implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
	implementation("org.mongodb:bson:4.11.1")
	implementation("androidx.core:core-ktx:1.12.0")
	implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
	implementation("androidx.activity:activity-compose:1.8.1")
	implementation(platform("androidx.compose:compose-bom:2023.10.01"))
	implementation("androidx.compose.ui:ui")
	implementation("androidx.compose.ui:ui-graphics")
	implementation("androidx.compose.ui:ui-tooling-preview")
	implementation("androidx.compose.material3:material3:1.1.2")
	implementation("androidx.compose.material:material-icons-core")
	implementation("androidx.compose.material:material-icons-extended:1.5.4")
	testImplementation("org.jetbrains.kotlin:kotlin-test:1.9.21")
	androidTestImplementation("androidx.test.ext:junit:1.1.5")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
	androidTestImplementation(platform("androidx.compose:compose-bom:2023.10.01"))
	androidTestImplementation("androidx.compose.ui:ui-test-junit4")
	debugImplementation("androidx.compose.ui:ui-tooling")
	debugImplementation("androidx.compose.ui:ui-test-manifest:1.5.4")
	
}