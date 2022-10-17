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
val BASE_URL: String = releaseProperties.getProperty("DOUJIN_BASE_URL", "")
val MEDIA_BASE_URL: String = releaseProperties.getProperty("DOUJIN_MEDIA_BASE_URL", "")

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

        buildConfigString("BASE_URL", BASE_URL)
        buildConfigString("MEDIA_BASE_URL", MEDIA_BASE_URL)
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
