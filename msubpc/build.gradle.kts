import java.util.*

plugins {
    androidGitVersion()
    library()
    kotlinAndroid()
    kotlinKapt()
    kotlinExtension()
    dagger()
}

val releaseProperties = Properties()
releaseProperties.load(file("${rootDir}/config.properties").inputStream())
val BASE_URL: String = releaseProperties.getProperty("MSUB_PC_BASE_URL", "")
val ENCRYPT_KEY: String = releaseProperties.getProperty("MSUB_PC_ENCRYPT_KEY", "")

android {
    compileSdkVersion(Versions.compileSdkVersion)
    buildToolsVersion(Versions.buildToolsVersion)

    defaultConfig {

        minSdkVersion(Versions.tvMinSdkVersion)
        targetSdkVersion(Versions.tvTargetSdkVersion)

        multiDexEnabled = true

        vectorDrawables {
            useSupportLibrary = true
        }

        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
        consumerProguardFiles("consumer-rules.pro")

        buildConfigString("BASE_URL", BASE_URL)
        buildConfigString("ENCRYPT_KEY", ENCRYPT_KEY)
    }

    flavorDimensions("env")
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
            debuggable(true)
            jniDebuggable(true)
            renderscriptDebuggable(true)

            minifyEnabled(false)
            isShrinkResources = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("release") {
            debuggable(false)
            jniDebuggable(false)
            renderscriptDebuggable(false)

            minifyEnabled(false)
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
                val buildOutputPath = "../../release/${androidGitVersion.name()}/"
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

    implementation("commons-codec:commons-codec:1.15")

    // Room
    implementation(Libs.roomKtx)
    implementation(Libs.roomRuntime)
    kapt(Libs.roomCompiler)
    // RxJava support for Room
    implementation(Libs.roomRxJava)
}