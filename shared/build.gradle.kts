import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

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

    val osName = System.getProperty("os.name")
    val osArch = System.getProperty("os.arch")
    println("OS Name = $osName")
    println("OS Name = $osArch")

    val isAppleSilicon = osName == "Mac OS X" && osArch == "aarch64"
    println("Is Apple Silicon: $isAppleSilicon")

    val targets = mutableListOf<KotlinNativeTarget>(
        iosX64(),
    )
    if (isAppleSilicon) {
        targets.add(iosArm64())
        targets.add(iosSimulatorArm64())
    }
    targets.forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting
        val androidUnitTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.example.kmmplayground"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
    }
}