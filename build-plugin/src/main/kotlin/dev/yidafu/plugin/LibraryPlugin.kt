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
    }
}
