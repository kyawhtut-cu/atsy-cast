plugins {
    library()
}

android {
    compileSdkVersion(Versions.compileSdkVersion)
    buildToolsVersion(Versions.buildToolsVersion)

    sourceSets {
        getByName("main") {
            jniLibs.srcDirs("src/main/libs")
            jni.setSrcDirs(listOf<String>())
        }
    }

    defaultConfig {
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.targetSdkVersion)
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

    lintOptions {
        disable("InvalidPackage")
    }
}

dependencies {
    implementation("com.android.support:support-annotations:28.0.0")
}
