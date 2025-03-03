plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    id("com.google.dagger.hilt.android") version "2.49" apply false
    id("com.google.devtools.ksp") version "1.9.21-1.0.15" apply false
    alias(libs.plugins.kotlinSerialization) apply false
}
true // Needed to make the Suppress annotation work for the plugins block