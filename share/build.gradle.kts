import java.util.*

plugins {
    library()
    kotlinAndroid()
    kotlinKapt()
    androidGitVersion()
    dagger()
    xdimen()
}

val releaseProperties = Properties()
releaseProperties.load(file("${rootDir}/local.properties").inputStream())
val SHEET_BASE_URL: String = releaseProperties.getProperty("SHEET_BASE_URL", "")
val RELEASE_SCRIPT_ID: String = releaseProperties.getProperty("RELEASE_SCRIPT_ID", "")
val DEBUG_SCRIPT_ID: String = releaseProperties.getProperty("DEBUG_SCRIPT_ID", "")
val TELEGRAM_BOT_URL: String = releaseProperties.getProperty("TELEGRAM_BOT_URL", "")
val TELEGRAM_BOT_ID: String = releaseProperties.getProperty("TELEGRAM_BOT_ID", "")
val TELEGRAM_DEV_ID: String = releaseProperties.getProperty("TELEGRAM_DEV_ID", "")

android {
    compileSdk = Versions.compileSdkVersion

    defaultConfig {
        minSdk = Versions.tvMinSdkVersion
        targetSdk = Versions.tvTargetSdkVersion

        multiDexEnabled = true

        vectorDrawables {
            useSupportLibrary = true
        }

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
        buildConfigString(
            "TELEGRAM_DEV_ID",
            TELEGRAM_DEV_ID
        )

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$rootDir/schemas"
            }
        }
    }

    flavorDimensions += listOf("env")
    productFlavors {
        create("local") {
            dimension = "env"
        }

        create("prod") {
            dimension = "env"
        }
    }

    buildTypes {

        getByName("debug") {
            isJniDebuggable = true
            isRenderscriptDebuggable = true

            isMinifyEnabled = false
            isShrinkResources = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("release") {

            buildConfigString(
                "SCRIPT_ID",
                RELEASE_SCRIPT_ID
            )

            isJniDebuggable = false
            isRenderscriptDebuggable = false

            isMinifyEnabled = false
            isShrinkResources = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        create("home") {
            initWith(getByName("release"))

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

    android.libraryVariants.all {
        val variant = this
        variant.outputs.map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
            .forEach { output ->
                val buildOutputPath = "../../../../../modules/${androidGitVersion.name()}/"
                output.outputFileName = String.format(
                    "%s%s",
                    buildOutputPath,
                    output.outputFileName
                )
            }
    }
}

xdimen {
    deleteOldXdimen.set(true)
    designWidth.set(1024)
    designDpi.set(mdpi())
    targetDevicesWidth.set(devicesInLandscape)
    dimensRange {
        minDimen.set(-56)
        maxDimen.set(500)
        step.set(0.5)
    }
    fontsRange {
        minDimen.set(4)
        maxDimen.set(240)
        step.set(1.0)
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
    "homeImplementation"(Libs.chuckRelease)

    api(Libs.jsoup)

    implementation(Libs.glide)
    implementation(Libs.glideOkHttp)
    kapt(Libs.glideCompiler)

    //Timber(Logging)
    api(Libs.timber)

    // Toasty(Toast)
    api(Libs.toasty)

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

    implementation(Libs.googleZxing)

    // realTimeBlur - https://github.com/mmin18/RealtimeBlurView
    implementation(Libs.realTimeBlur)
}
