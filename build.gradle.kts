plugins {
    `kotlin-dsl`
    alias(libs.plugins.swdevsm.publishing.conventions)
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    mavenLocal()
}

dependencies {
    implementation(libs.ktlint.gradle.plugin)
}

kotlin {
    jvmToolchain(21)
}

private val pluginId = "ru.swdevsm.ktlint-conventions"

gradlePlugin {
    plugins {
        named(pluginId) {
            displayName = "swdevsm ktlint conventions"
            description = "Convention plugin that configures ktlint with swdevsm defaults."
        }
    }
}

swdevsmPublishing {
    artifactId.set("ktlint-conventions")
    name.set("ktlint-conventions")
    description.set(
        "Gradle convention plugin that configures ktlint with swdevsm code style defaults.",
    )
    githubRepo.set("swdevsm/ktlint-conventions")
}
