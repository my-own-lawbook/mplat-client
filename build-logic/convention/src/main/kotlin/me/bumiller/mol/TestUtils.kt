package me.bumiller.mol

import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.withType

/**
 * Configures JUnit testing options
 */
fun Project.junitConfig() {
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}