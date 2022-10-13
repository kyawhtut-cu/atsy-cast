plugins {
    `kotlin-dsl`
}

val kotlinVersion = "1.7.0"

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("com.android.tools.build:gradle:7.2.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    implementation("com.squareup:javapoet:1.13.0")
}
