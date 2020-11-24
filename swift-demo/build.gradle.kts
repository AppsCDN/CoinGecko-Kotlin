import com.chromaticnoise.multiplatformswiftpackage.SwiftPackageExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("com.chromaticnoise.multiplatform-swiftpackage") version SWIFT_PACKAGE
}

configure<SwiftPackageExtension> {
    swiftToolsVersion("5.3")
    targetPlatforms {
        watchOS { v("6") }
        iOS { v("10") }
        tvOS { v("9") }
        macOS { v("10.0") }
    }
}

kotlin {
    val targets = mutableListOf<KotlinNativeTarget>()
    ios { targets.add(this) }
    tvos { targets.add(this) }
    watchos { targets.add(this) }
    macosX64("macos") { targets.add(this) }

    targets.forEach { target ->
        target.binaries {
            framework(listOf(DEBUG, RELEASE)) {
                baseName = "coingecko"
                export(rootProject)
            }
        }
    }

    sourceSets {
        all { dependencies { api(rootProject) } }
        val iosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:$KTOR_VERSION")
            }
        }
        val tvosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:$KTOR_VERSION")
            }
        }
        val watchosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:$KTOR_VERSION")
            }
        }
        val macosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-curl:$KTOR_VERSION")
            }
        }
    }
}