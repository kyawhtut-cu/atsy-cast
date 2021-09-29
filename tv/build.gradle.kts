import java.util.*

plugins {
    androidGitVersion()
    androidApp()
    dagger()
    kotlinAndroid()
    kotlinKapt()
    kotlinExtension()
    googleService()
    firebaseCrashlytics()
}

val apkName = hashMapOf(
    "localDebug" to "atsy-cast-debug-local.apk",
    "prodDebug" to "atsy-cast-debug.apk",
    "localRelease" to "atsy-cast-local.apk",
    "prodRelease" to "atsy-cast.apk",
    "localHome" to "atsy-cast-home-local.apk",
    "prodHome" to "atsy-cast-home.apk"
)

val releaseProperties = Properties()
releaseProperties.load(file("${rootDir}/local.properties").inputStream())
val DEBUG_ALIAS: String = releaseProperties.getProperty("DEBUG_ALIAS", "")
val DEBUG_ALIAS_PASSWORD: String = releaseProperties.getProperty("DEBUG_ALIAS_PASSWORD", "")
val DEBUG_KEYSTORE_PASSWORD: String = releaseProperties.getProperty("DEBUG_KEYSTORE_PASSWORD", "")
val DEBUG_KEYSTORE_NAME: String = releaseProperties.getProperty("DEBUG_KEYSTORE_NAME", "")

val RELEASE_ALIAS: String = releaseProperties.getProperty("RELEASE_ALIAS", "")
val RELEASE_ALIAS_PASSWORD: String = releaseProperties.getProperty("RELEASE_ALIAS_PASSWORD", "")
val RELEASE_KEYSTORE_PASSWORD: String = releaseProperties.getProperty("RELEASE_KEYSTORE_PASSWORD", "")
val RELEASE_KEYSTORE_NAME: String = releaseProperties.getProperty("RELEASE_KEYSTORE_NAME", "")

val HOME_UPDATE_URL: String = releaseProperties.getProperty("HOME_UPDATE_URL", "")
val RELEASE_UPDATE_URL: String = releaseProperties.getProperty("RELEASE_UPDATE_URL", "")

android {
    compileSdkVersion(Versions.compileSdkVersion)
    buildToolsVersion(Versions.buildToolsVersion)

    defaultConfig {

        applicationId = "com.kyawhut.atsycast"

        minSdkVersion(Versions.tvMinSdkVersion)
        targetSdkVersion(Versions.tvTargetSdkVersion)

        versionCode = androidGitVersion.code()
        versionName = androidGitVersion.name()

        multiDexEnabled = true

        vectorDrawables {
            useSupportLibrary = true
        }

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

    signingConfigs {

        getByName("debug") {
            storeFile = File(rootDir, DEBUG_KEYSTORE_NAME)
            storePassword = DEBUG_KEYSTORE_PASSWORD
            keyAlias = DEBUG_ALIAS
            keyPassword = DEBUG_ALIAS_PASSWORD
        }

        register("release") {
            storeFile = File(rootDir, RELEASE_KEYSTORE_NAME)
            storePassword = RELEASE_KEYSTORE_PASSWORD
            keyAlias = RELEASE_ALIAS
            keyPassword = RELEASE_ALIAS_PASSWORD
        }
    }

    buildTypes {

        getByName("debug") {
            debuggable(true)
            jniDebuggable(true)
            renderscriptDebuggable(true)

            minifyEnabled(false)
            isShrinkResources = false

            applicationIdSuffix = ".debug"

            signingConfig = signingConfigs.getByName("debug")

            buildConfigString("UPDATE_URL", "")

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("release") {
            debuggable(false)
            jniDebuggable(false)
            renderscriptDebuggable(false)

            minifyEnabled(true)
            isShrinkResources = true

            applicationIdSuffix = ""

            signingConfig = signingConfigs.getByName("release")

            buildConfigString("UPDATE_URL", RELEASE_UPDATE_URL)

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        create("home") {
            initWith(getByName("release"))

            applicationIdSuffix = ".home"

            buildConfigString("UPDATE_URL", HOME_UPDATE_URL)

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

    android.applicationVariants.all {
        val variant = this
        variant.outputs.map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
            .forEach { output ->
                val buildOutputPath = "../../release/${variant.versionName}/"
                output.outputFileName = String.format(
                    "%s%s",
                    buildOutputPath,
                    apkName[variant.flavorName + variant.buildType.name.capitalize()]
                )
            }
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
    implementation(project(":ets2mm"))
    implementation(project(":2d"))
    implementation(project(":eporner"))
    implementation(project(":gsmovies"))

    testImplementation(Libs.junit)
    androidTestImplementation(Libs.testJunit)
    androidTestImplementation(Libs.espresso)

    implementation(Libs.kotlinLib)
    implementation(Libs.coreKtx)
    implementation(Libs.constraintLayout)
    implementation(Libs.vectorDrawable)
    implementation(Libs.leanbackPreference)

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

    implementation(Libs.lottie)
    implementation(Libs.easyPermission)

    //Memory leak detection
    debugImplementation(Libs.leakCanary)
    releaseImplementation(Libs.leakCanaryNoOP)
    "homeImplementation"(Libs.leakCanaryNoOP)
}
