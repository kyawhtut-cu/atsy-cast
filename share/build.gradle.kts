import java.util.*

plugins {
    library()
    kotlinAndroid()
    kotlinKapt()
    dagger()
}

val releaseProperties = Properties()
releaseProperties.load(file("${rootDir}/local.properties").inputStream())
val SHEET_BASE_URL: String = releaseProperties.getProperty("SHEET_BASE_URL", "")
val RELEASE_SCRIPT_ID: String = releaseProperties.getProperty("RELEASE_SCRIPT_ID", "")
val DEBUG_SCRIPT_ID: String = releaseProperties.getProperty("DEBUG_SCRIPT_ID", "")
val TELEGRAM_BOT_URL: String = releaseProperties.getProperty("TELEGRAM_BOT_URL", "")
val TELEGRAM_BOT_ID: String = releaseProperties.getProperty("TELEGRAM_BOT_ID", "")

android {
    compileSdkVersion(Versions.compileSdkVersion)
    buildToolsVersion(Versions.buildToolsVersion)

    defaultConfig {
        minSdkVersion(Versions.tvMinSdkVersion)
        targetSdkVersion(Versions.tvMinSdkVersion)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigString(
            "SHEET_BASE_URL",
            SHEET_BASE_URL
        )
        buildConfigString(
            "SCRIPT_ID",
            DEBUG_SCRIPT_ID
        )
        buildConfigString(
            "TELEGRAM_BASE_URL",
            TELEGRAM_BOT_URL
        )
        buildConfigString(
            "TELEGRAM_BOT_ID",
            TELEGRAM_BOT_ID
        )

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$rootDir/schemas"
            }
        }
    }

    buildTypes {
        getByName("release") {
            buildConfigString(
                "SCRIPT_ID",
                RELEASE_SCRIPT_ID
            )

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

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

dependencies {

    api(Libs.leanback)
    implementation(Libs.kotlinLib)
    implementation(Libs.coreKtx)
    api(Libs.preferenceKtx)
    implementation(Libs.constraintLayout)

    // dependency injection
    implementation(Libs.hiltAndroid)
    kapt(Libs.hiltAndroidCompiler)

    // ViewModel and LiveData
    api(Libs.lifeCycleExt)
    api(Libs.fragmentKtx)

    implementation(Libs.retrofit)
    implementation(Libs.okhttp)
    implementation(Libs.retrofitGson)
    implementation(Libs.loggingInterceptor)
    implementation(Libs.coroutineKotlin)

    //HTTP interceptor
    debugImplementation(Libs.chuck)
    releaseImplementation(Libs.chuckRelease)

    api(Libs.jsoup)

    implementation(Libs.glide)
    implementation(Libs.glideOkHttp)
    kapt(Libs.glideCompiler)

    //Timber(Logging)
    api(Libs.timber)

    implementation(Libs.exoPlayer)
    implementation(Libs.exoPlayerCore)
    implementation(Libs.exoPlayerDash)
    implementation(Libs.exoPlayerUI)
    implementation(Libs.exoPlayerHLS)
    implementation(Libs.exoPlayerSmoothStreaming)
    implementation(Libs.exoPlayerRTMP)
    implementation(Libs.exoPlayerOkHttp)
    implementation(Libs.exoPlayerMediaSession)

    // Room
    implementation(Libs.roomKtx)
    implementation(Libs.roomRuntime)
    kapt(Libs.roomCompiler)
    // RxJava support for Room
    implementation(Libs.roomRxJava)

    implementation(Libs.lottie)
}
