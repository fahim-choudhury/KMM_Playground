plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    ios {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }
    sourceSets {
        val commonMain by getting
        val iosMain by getting
    }
}

android {
    namespace = "com.example.kmmplayground"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
    }
}