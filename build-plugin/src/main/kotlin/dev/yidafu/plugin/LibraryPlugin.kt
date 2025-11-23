package dev.yidafu.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.tasks.GenerateMavenPom
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.gradle.plugin.use.PluginDependency
import org.gradle.plugins.signing.SigningExtension
import org.jetbrains.dokka.gradle.DokkaTask
import java.net.URI
import java.util.Properties

fun Project.getLibPlugin(name: String): PluginDependency {
    val catalogs = extensions.getByType<VersionCatalogsExtension>()
    val plugin = catalogs.find("libs").get().findPlugin(name)
    return plugin.get().get()
}

/**
 * plugin for library
 */
class LibraryPlugin : Plugin<Project> {
    companion object {
        const val PUBLICATION_NAME = "sonatype"
    }

    /**
     * Apply this plugin to the given target object.
     *
     * @param project The target object
     */
    override fun apply(project: Project) {
        with(project.plugins) {
            listOf(
                "jvm",
                "dokka",
                "signing",
                "mavenPublish",
                "ktlint",
                "nmcp"
            )
                .map { project.getLibPlugin(it) }
                .forEach {
                    apply(it.pluginId)
                }
        }
        project.configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
            disabledRules.set(setOf("final-newline", "no-wildcard-imports", "filename"))
        }
        project.extensions.create("publishMan", PublishManExtension::class)

        // Load local.properties
        val localPropertiesFile = project.rootProject.file("local.properties")
        val localProperties = Properties()
        if (localPropertiesFile.exists()) {
            localPropertiesFile.inputStream().use { localProperties.load(it) }
        }

        val dokkaJavadoc = project.tasks.findByName("dokkaJavadoc") as DokkaTask
        val dokkaJavadocJar = project.tasks.register<Jar>("dokkaJavadocJar") {
            dependsOn(dokkaJavadoc.path)
            from(dokkaJavadoc.outputDirectory)
            archiveClassifier.set("javadoc")
        }
        val kotlinSourcesJar = project.tasks.findByName("kotlinSourcesJar") as Jar

        val publishing = project.extensions.getByType<PublishingExtension>()
        // NMCP plugin handles repository configuration

        publishing.publications {
            create<MavenPublication>(PUBLICATION_NAME) {
                from(project.components["java"])
                artifact(kotlinSourcesJar)
                artifact(dokkaJavadocJar)

                versionMapping {
                    usage("java-api") {
                        fromResolutionOf("runtimeClasspath")
                    }
                    usage("java-runtime") {
                        fromResolutionResult()
                    }
                }

                pom {
                    // These will be set by publishMan extension
                    name.set(project.extensions.getByType<PublishManExtension>().name)
                    description.set(project.extensions.getByType<PublishManExtension>().description)
                    url.set("https://github.com/yidafu/swc-binding")

                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }
                    developers {
                        developer {
                            id.set("yidafu")
                            name.set("YidaFu")
                            email.set("yidafu90@qq.com")
                        }
                    }
                    scm {
                        connection.set("scm:git:git://github.com/yidafu/swc-binding.git")
                        developerConnection.set("scm:git:ssh://github.com/yidafu/swc-binding.git")
                        url.set("https://github.com/yidafu/swc-binding")
                    }
                }
            }
        }

        // NMCP Plugin Configuration for Maven Central Publishing
        // Note: The actual configuration is done in build.gradle.kts due to type safety
        // Store credentials in project properties for nmcp plugin to use
        project.afterEvaluate {
            val username = localProperties.getProperty("centralUsername")
                ?: project.findProperty("centralUsername") as String?
                ?: System.getenv("CENTRAL_USERNAME")
            val password = localProperties.getProperty("centralPassword")
                ?: project.findProperty("centralPassword") as String?
                ?: System.getenv("CENTRAL_PASSWORD")
            
            if (username != null) {
                project.extensions.extraProperties.set("nmcp.username", username)
            }
            if (password != null) {
                project.extensions.extraProperties.set("nmcp.password", password)
            }
            project.extensions.extraProperties.set("nmcp.publicationType", "USER_MANAGED")
        }

        // Signing configuration for Maven Central
        project.extensions.configure<SigningExtension>("signing") {
            val signingKey = localProperties.getProperty("signing.key")
                ?: System.getenv("SIGNING_KEY")
            val signingPassword = localProperties.getProperty("signing.password")
                ?: System.getenv("SIGNING_PASSWORD")

            if (signingKey != null && signingPassword != null) {
                useInMemoryPgpKeys(signingKey, signingPassword)
                sign(publishing.publications)
            }
        }
    }
}
