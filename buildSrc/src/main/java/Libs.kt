object Libs {

    const val gradleAndroid = "com.android.tools.build:gradle:${Versions.gradleAndroidVersion}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
    const val googleService = "com.google.gms:google-services:${Versions.googleServiceVersion}"
    const val firebaseCrashlyticsService =
        "com.google.firebase:firebase-crashlytics-gradle:${Versions.firebaseCrashlyticsVersion}"
    const val dagger = "com.google.dagger:hilt-android-gradle-plugin:${Versions.daggerVersion}"
    const val navigationSafeArgs =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigationSafeArgsVersion}"
    const val xdimen =
        "io.github.islamkhsh.xdimen:io.github.islamkhsh.xdimen.gradle.plugin:${Versions.xdimenVersion}"

    const val junit = "junit:junit:${Versions.junitVersion}"
    const val testJunit = "androidx.test.ext:junit:${Versions.testJunitVersion}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoVersion}"

    const val kotlinLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinVersion}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtxVersion}"
    const val appCompact = "androidx.appcompat:appcompat:${Versions.appCompactVersion}"
    const val material = "com.google.android.material:material:${Versions.materialVersion}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
    const val preferenceKtx = "androidx.preference:preference-ktx:${Versions.preferenceKtxVersion}"
    const val vectorDrawable =
        "androidx.vectordrawable:vectordrawable:${Versions.vectorDrawableVersion}"

    const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebaseBomVersion}"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx"
    const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics-ktx"
    const val googleAnalytics =
        "com.google.android.gms:play-services-analytics:${Versions.googleAnalyticsVersion}"
    const val firebaseCore = "com.google.firebase:firebase-core:${Versions.firebaseCoreVersion}"
    const val firebaseMessaging =
        "com.google.firebase:firebase-messaging:${Versions.firebaseMessagingVersion}"

    const val zxing = "me.dm7.barcodescanner:zxing:${Versions.zxingVersion}"
    const val florent = "com.github.florent37:runtime-permission-kotlin:${Versions.florentVersion}"

    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hiltVersion}"
    const val hiltAndroidCompiler =
        "com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}"
    const val hiltViewModel =
        "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltAndroidXVersion}"
    const val hiltCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltAndroidXVersion}"

    const val roomRuntime = "androidx.room:room-runtime:${Versions.roomVersion}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"
    const val roomRxJava = "androidx.room:room-rxjava2:${Versions.roomVersion}"
    const val roomKtx =
        "androidx.lifecycle:lifecycle-reactivestreams-ktx:${Versions.roomKtxVersion}"

    const val lifeCycleExt =
        "androidx.lifecycle:lifecycle-extensions:${Versions.lifeCycleExtVersion}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtxVersion}"

    const val gson = "com.google.code.gson:gson:${Versions.gsonVersion}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    const val retrofitGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}"
    const val retrofitRxJava =
        "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofitRxJavaVersion}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttpVersion}"
    const val loggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptorVersion}"
    const val coroutineKotlin =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutineKotlin}"

    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJavaVersion}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroidVersion}"
    const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlinVersion}"

    const val chuck = "com.readystatesoftware.chuck:library:${Versions.chuckVersion}"
    const val chuckRelease = "com.readystatesoftware.chuck:library-no-op:${Versions.chuckVersion}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timberVersion}"
    const val roundableLayout =
        "com.github.zladnrms:RoundableLayout:${Versions.roundableLayoutVersion}"
    const val aviLoading = "com.wang.avi:library:${Versions.aviLoadingVersion}"

    const val leanback = "androidx.leanback:leanback:${Versions.leanbackVersion}"
    const val leanbackPreference = "androidx.leanback:leanback-preference:${Versions.leanbackPreferenceVersion}"

    const val coil = "io.coil-kt:coil:${Versions.coilVersion}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glideVersion}"
    const val glideOkHttp =
        "com.github.bumptech.glide:okhttp3-integration:${Versions.glideOkHttpVersion}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glideVersion}"

    const val exoPlayer = "com.google.android.exoplayer:exoplayer:${Versions.exoPlayerVersion}"
    const val exoPlayerCore =
        "com.google.android.exoplayer:exoplayer-core:${Versions.exoPlayerVersion}"
    const val exoPlayerDash =
        "com.google.android.exoplayer:exoplayer-dash:${Versions.exoPlayerVersion}"
    const val exoPlayerUI = "com.google.android.exoplayer:exoplayer-ui:${Versions.exoPlayerVersion}"
    const val exoPlayerHLS =
        "com.google.android.exoplayer:exoplayer-hls:${Versions.exoPlayerVersion}"
    const val exoPlayerSmoothStreaming =
        "com.google.android.exoplayer:exoplayer-smoothstreaming:${Versions.exoPlayerVersion}"
    const val exoPlayerRTMP =
        "com.google.android.exoplayer:extension-rtmp:${Versions.exoPlayerVersion}"
    const val exoPlayerOkHttp =
        "com.google.android.exoplayer:extension-okhttp:${Versions.exoPlayerVersion}"
    const val exoPlayerMediaSession =
        "com.google.android.exoplayer:extension-mediasession:${Versions.exoPlayerVersion}"

    const val jsoup = "org.jsoup:jsoup:${Versions.jsoupVersion}"

    const val lottie = "com.airbnb.android:lottie:${Versions.lottieVersion}"
    const val toasty = "com.github.GrenderG:Toasty:${Versions.toastyVersion}"
    const val easyPermission = "pub.devrel:easypermissions:${Versions.easyPermissionVersion}"
    const val googleZxing = "com.google.zxing:core:${Versions.googleZxingVersion}"

    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanaryVersion}"
    const val leakCanaryNoOP = "com.squareup.leakcanary:leakcanary-android-no-op:${Versions.leakCanaryVersion}"

    const val realTimeBlur = "com.github.mmin18:realtimeblurview:${Versions.realTimeBlurVersion}"
}
