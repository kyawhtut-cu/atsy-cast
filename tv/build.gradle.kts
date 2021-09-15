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

android {
    compileSdkVersion(Versions.compileSdkVersion)
    buildToolsVersion(Versions.buildToolsVersion)

    defaultConfig {

        applicationId = "com.kyawhut.atsycast"

        minSdkVersion(Versions.tvMinSdkVersion)
        targetSdkVersion(Versions.tvTargetSdkVersion)

        versionCode = 1
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

    buildTypes {

        getByName("debug") {
            debuggable(true)
            jniDebuggable(true)
            renderscriptDebuggable(true)

            minifyEnabled(false)
            isShrinkResources = false

            applicationIdSuffix = ".debug"

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

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        create("home") {
            initWith(getByName("release"))

            applicationIdSuffix = ".home"

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
