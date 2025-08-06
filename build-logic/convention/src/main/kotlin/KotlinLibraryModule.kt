import me.bumiller.civoris.SourceSets
import me.bumiller.civoris.baseDependencies
import me.bumiller.civoris.junitConfig
import me.bumiller.civoris.kotlinConfig
import me.bumiller.civoris.multiplatformPlugin
import me.bumiller.civoris.serializationPlugin
import me.bumiller.civoris.testDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Gradle module that configures common properties for all modules that act as a library.
 */
class KotlinLibraryModule : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        multiplatformPlugin()
        serializationPlugin()

        kotlinConfig()
        junitConfig()

        baseDependencies(listOf(SourceSets.Common))
        testDependencies(listOf(SourceSets.Common))
    }

}
