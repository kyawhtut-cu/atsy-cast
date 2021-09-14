plugins {
    androidApp()
    dagger()
    kotlinAndroid()
    kotlinKapt()
    kotlinExtension()
    googleService()
    firebaseCrashlytics()
}

android {
    compileSdkVersion(Versions.compileSdkVersion)
    buildToolsVersion(Versions.buildToolsVersion)

    defaultConfig {
        applicationId = "com.kyawhut.atsycast"
        minSdkVersion(Versions.tvMinSdkVersion)
        targetSdkVersion(Versions.tvTargetSdkVersion)
        versionCode = 1
        versionName = "1.0"

        multiDexEnabled = true

        vectorDrawables {
            useSupportLibrary = true
        }

    }

    buildTypes {
        getByName("release") {
            minifyEnabled(false)
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(project(":share"))
    implementation(project(":free2air"))
    implementation(project(":msubpc"))
    implementation(project(":msys"))
    implementation(project(":zcm"))
    implementation(project(":doujin"))

    testImplementation(Libs.junit)
    androidTestImplementation(Libs.testJunit)
    androidTestImplementation(Libs.espresso)

    implementation(Libs.kotlinLib)
    implementation(Libs.coreKtx)
    implementation(Libs.constraintLayout)
    implementation(Libs.vectorDrawable)

    /*firebase*/
    implementation(platform(Libs.firebaseBom))
    implementation(Libs.firebaseAnalytics)
    implementation(Libs.firebaseCrashlytics)

    // dependency injection
    implementation(Libs.hiltAndroid)
    kapt(Libs.hiltAndroidCompiler)

    // retrofit
    implementation(Libs.retrofit)

    // Room
    implementation(Libs.roomRuntime)
    kapt(Libs.roomCompiler)

    implementation(Libs.gson)
}
