buildscript {
    dependencies {
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.45")
    }
}
plugins {
    id("com.android.application") version "8.1.1" apply false
    id ("com.android.library") version "8.0.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
}