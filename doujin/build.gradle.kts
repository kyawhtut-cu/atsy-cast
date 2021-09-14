import java.util.*

plugins {
    library()
    kotlinAndroid()
    kotlinKapt()
    kotlinExtension()
    dagger()
}

val releaseProperties = Properties()
releaseProperties.load(file("${rootDir}/local.properties").inputStream())
val BASE_URL: String = releaseProperties.getProperty("DOUJIN_BASE_URL", "")

android {
    compileSdkVersion(Versions.compileSdkVersion)
    buildToolsVersion(Versions.buildToolsVersion)

    defaultConfig {
        minSdkVersion(Versions.tvMinSdkVersion)
        targetSdkVersion(Versions.tvTargetSdkVersion)
        versionCode = 1
        versionName = "1.0"

        multiDexEnabled = true

        vectorDrawables {
            useSupportLibrary = true
        }

        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
        consumerProguardFiles("consumer-rules.pro")

        buildConfigString("BASE_URL", BASE_URL)
        buildConfigString("IMAGE_BASE_URL", "https://media.hentaiser.com")
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

    implementation(project(":share"))

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

    // retrofit
    implementation(Libs.retrofit)

    implementation(Libs.gson)
}
