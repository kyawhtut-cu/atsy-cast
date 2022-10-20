import java.util.*

plugins {
    library()
    kotlinAndroid()
    kotlinKapt()
    androidGitVersion()
    dagger()
}

val releaseProperties = Properties()
releaseProperties.load(file("${rootDir}/local.properties").inputStream())
val TELEGRAM_APP_ID: Int = releaseProperties.getProperty("TELEGRAM_APP_ID", "0").toInt()
val TELEGRAM_APP_HASH: String = releaseProperties.getProperty("TELEGRAM_APP_HASH", "")

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

        buildConfigInt("TELEGRAM_APP_ID", TELEGRAM_APP_ID)
        buildConfigString("TELEGRAM_APP_HASH", TELEGRAM_APP_HASH)
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

dependencies {
    implementation(project(":share"))
    implementation(project(":previewimagcol"))
    implementation(project(":tdlib"))

    testImplementation(Libs.junit)
    androidTestImplementation(Libs.testJunit)
    androidTestImplementation(Libs.espresso)

    implementation(Libs.kotlinLib)
    implementation(Libs.coreKtx)
    implementation(Libs.constraintLayout)
    implementation(Libs.vectorDrawable)

    implementation(Libs.leanback)

    // dependency injection
    implementation(Libs.hiltAndroid)
    kapt(Libs.hiltAndroidCompiler)

    implementation(Libs.gson)

    implementation(Libs.coroutineKotlin)
    implementation(Libs.googleZxing)

    // Сustom QR generator for Android - https://github.com/alexzhirkevich/custom-qr-generator
    implementation("com.github.alexzhirkevich:custom-qr-generator:1.5.0")
}
