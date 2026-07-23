import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
  id("org.jlleitschuh.gradle.ktlint")
}

// Per-module DSL surface. Consumers write, e.g.:
//   swdevsmKtlint {
//       enabled = false
//       excludes.add("**/legacy/**")
//   }
// Modelled as an interface so Gradle instantiates it via ObjectFactory —
// the abstract Property/ListProperty accessors are wired automatically.
interface SwdevsmKtlintExtension {
  val enabled: Property<Boolean>
  val excludes: ListProperty<String>
}

val swdevsmKtlint = extensions.create<SwdevsmKtlintExtension>("swdevsmKtlint").apply {
  enabled.convention(true)
  excludes.convention(emptyList())
}

// ktlint engine version is read from a Gradle property so consumer repos
// can override it in their own gradle.properties without editing this plugin.
val ktlintVersion: String = providers.gradleProperty("ktlint.version").getOrElse("1.5.0")

configure<KtlintExtension> {
  version.set(ktlintVersion)
  android.set(false)
  ignoreFailures.set(false)
  verbose.set(true)
  coloredOutput.set(true)
  // Keep the default (standard) rule set; experimental rules are opt-in per repo.
  enableExperimentalRules.set(false)
  // PLAIN for humans and CI logs; SARIF so GitHub Code Scanning can ingest the report.
  reporters {
    reporter(ReporterType.PLAIN)
    reporter(ReporterType.SARIF)
  }
  // Baseline excludes. Additional patterns come from the `swdevsmKtlint { excludes = [...] }` DSL.
  filter {
    exclude("**/generated/**")
    exclude("**/build/**")
  }
  // swdevsm-wide ktlint rule defaults. Injected into ktlint's editorconfig
  // resolution without a physical file — consumers' own .editorconfig still wins.
  // Add IDE-visible layout keys (indent_size, imports layout, etc.) to the
  // consumer's .editorconfig instead, so IntelliJ sees them too.
  additionalEditorconfig.set(
    mapOf(
      "ktlint_standard_filename" to "disabled",
    ),
  )
}

// Apply user-facing overrides after the consumer's build.gradle.kts has finished
// configuring the `swdevsmKtlint { }` extension.
afterEvaluate {
  if (!swdevsmKtlint.enabled.get()) {
    tasks.matching { it.name.startsWith("ktlint") }.configureEach {
      enabled = false
    }
  }
  val userExcludes = swdevsmKtlint.excludes.get()
  if (userExcludes.isNotEmpty()) {
    the<KtlintExtension>().filter {
      userExcludes.forEach { pattern -> exclude(pattern) }
    }
  }
}
