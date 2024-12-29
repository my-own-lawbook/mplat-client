import me.bumiller.mol.dependencies
import me.bumiller.mol.kotlinConfig
import me.bumiller.mol.multiplatformPlugin
import me.bumiller.mol.testDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Gradle module that configures common properties for all modules that act as a library.
 */
class KotlinLibraryModule : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        multiplatformPlugin()

        kotlinConfig()

        dependencies()
        testDependencies()
    }

}
