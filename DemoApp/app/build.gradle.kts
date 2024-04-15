plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {

    namespace = "hr.tomislavplaninic.demo"
    compileSdk = 34

    defaultConfig {
        applicationId = "hr.tomislavplaninic.demo"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        dataBinding = true
        buildConfig = true
    }

    buildTypes {
        debug {
            buildConfigField("String", "RAWG_API_KEY", "\"d03e7c2510a24d0ebc03dc38bd70e3f1\"")
            buildConfigField("String", "FIREBASE_URL", "\"https://demoapp-4e846-default-rtdb.europe-west1.firebasedatabase.app\"")
        }
        release {
            buildConfigField("String", "RAWG_API_KEY", "\"d03e7c2510a24d0ebc03dc38bd70e3f1\"")
            buildConfigField("String", "FIREBASE_URL", "\"https://demoapp-4e846-default-rtdb.europe-west1.firebasedatabase.app\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity:1.8.2")
    implementation("com.google.firebase:firebase-firestore:24.11.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.recyclerview:recyclerview-selection:1.1.0")
    implementation("com.squareup.picasso:picasso:2.8")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation("com.airbnb.android:lottie:5.2.0")
    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    implementation("androidx.room:room-ktx:$room_version")

    implementation("androidx.room:room-rxjava2:$room_version")

    implementation("androidx.room:room-rxjava3:$room_version")

    implementation("androidx.room:room-guava:$room_version")

    testImplementation("androidx.room:room-testing:$room_version")

    implementation("androidx.room:room-paging:$room_version")
    implementation("androidx.databinding:databinding-runtime:8.3.2")
    implementation ("com.google.firebase:firebase-auth:22.3.1")
    implementation ("com.google.firebase:firebase-database:20.3.1")



}