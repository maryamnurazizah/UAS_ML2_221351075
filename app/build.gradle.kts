plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.celestica"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.celestica"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        mlModelBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation("org.tensorflow:tensorflow-lite-metadata:0.1.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    // ViewPager2 untuk swipe antar card
    implementation ("androidx.viewpager2:viewpager2:1.0.0")

    // RecyclerView (biasanya sudah ada)
    implementation ("androidx.recyclerview:recyclerview:1.3.2")

    // TensorFlow Lite (Core Interpreter)
    implementation ("org.tensorflow:tensorflow-lite:2.12.0")

    // (Opsional) TensorFlow Lite support untuk model dengan metadata
    implementation ("org.tensorflow:tensorflow-lite-support:0.4.3")

    // (Opsional) Kalau model kamu pakai task khusus seperti image classification atau text
    implementation ("org.tensorflow:tensorflow-lite-task-vision:0.4.3")
    implementation ("org.tensorflow:tensorflow-lite-task-text:0.4.3")

}