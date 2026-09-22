# ktlint-conventions

[![Publish](https://github.com/swdevsm/ktlint-conventions/actions/workflows/publish.yml/badge.svg)](https://github.com/swdevsm/ktlint-conventions/actions/workflows/publish.yml) [![Maven Central](https://img.shields.io/maven-central/v/ru.swdevsm/ktlint-conventions)](https://central.sonatype.com/artifact/ru.swdevsm/ktlint-conventions)

Precompiled script plugin `ru.swdevsm.ktlint-conventions` (kotlin-dsl) that wraps `org.jlleitschuh.gradle.ktlint` with swdevsm code-style defaults.

## Status

- **Build & push**: `.github/workflows/publish.yml` — runs `./gradlew publishToMavenCentral --no-configuration-cache` on GitHub release (`released`/`prereleased`) or manual `workflow_dispatch`.
- **Maven Central**: artifact `ru.swdevsm:ktlint-conventions` (see badge above for latest published version).

## Usage

```kotlin
plugins {
    id("ru.swdevsm.ktlint-conventions") version "<version>"
}
```

Wraps `org.jlleitschuh.gradle:ktlint-gradle` (`12.3.0`); default ktlint engine `1.5.0`. Consumers override via `swdevsmKtlint { enabled; excludes }`; engine version overridable with the `ktlint.version` Gradle property. Emits PLAIN + SARIF reports.

## Build

- `./gradlew build` (from inside this repo)
- Kotlin 2.3.10, JVM 21 toolchain.
- No tests.
- Resolves `ru.swdevsm.publishing-conventions` at `0.0.1` — publish it to `mavenLocal` first if resolution fails.