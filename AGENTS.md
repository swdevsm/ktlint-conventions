# ktlint-conventions

Precompiled script plugin `ru.swdevsm.ktlint-conventions` (kotlin-dsl) that wraps `org.jlleitschuh.gradle.ktlint` with swdevsm code-style defaults. No tests.

## Build

- Build: `./gradlew build` (from inside this repo)
- Kotlin 2.3.10, JVM 21 toolchain.
- Wraps `org.jlleitschuh.gradle:ktlint-gradle` (`12.3.0`); default ktlint engine `1.5.0`.
- Resolves `ru.swdevsm.publishing-conventions` at `0.0.1` — publish it to `mavenLocal` first if resolution fails.

## Consumer API

- Consumers override via `swdevsmKtlint { enabled; excludes }`.
- ktlint engine version overridable with the `ktlint.version` Gradle property (default `1.5.0`).
- Emits PLAIN + SARIF reports.

## Publishing

- Publishes artifact `ktlint-conventions` via the swdevsm publishing conventions (`githubRepo` = `swdevsm/ktlint-conventions`).
- CI: `.github/workflows/publish.yml` — `./gradlew publishToMavenCentral --no-configuration-cache` on GitHub release.