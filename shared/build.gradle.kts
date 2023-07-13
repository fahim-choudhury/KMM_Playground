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

    listOf(
        iosX64(),
//        iosArm64(),
//        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    // See https://youtrack.jetbrains.com/issue/KT-55751
    val myAttribute = Attribute.of("myOwnAttribute", String::class.java)

    // replace releaseFrameworkIosFat by the name of the first configuration that conflicts
    configurations.named("debugFrameworkIosX64").configure {
        attributes {
            // put a unique attribute
            attribute(myAttribute, "release-all")
        }
    }

    // replace debugFrameworkIosFat by the name of the second configuration that conflicts
    configurations.named("debugFrameworkIosFat").configure {
        attributes {
            attribute(myAttribute, "debug-all")
        }
    }
    // do this for all configurations that conflict
    configurations.named("releaseFrameworkIosX64").configure {
        attributes {
            // put a unique attribute
            attribute(myAttribute, "release-all-2")
        }
    }

    configurations.named("releaseFrameworkIosFat").configure {
        attributes {
            // put a unique attribute
            attribute(myAttribute, "release-all-3")
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
//        val iosArm64Main by getting
//        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
//            iosArm64Main.dependsOn(this)
//            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
//        val iosArm64Test by getting
//        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
//            iosArm64Test.dependsOn(this)
//            iosSimulatorArm64Test.dependsOn(this)
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