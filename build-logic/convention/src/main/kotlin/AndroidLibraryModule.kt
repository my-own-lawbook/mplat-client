import me.bumiller.mol.androidConfig
import me.bumiller.mol.androidDependencies
import me.bumiller.mol.androidInstrumentedTestDependencies
import me.bumiller.mol.androidLibraryPlugin
import me.bumiller.mol.dependencies
import me.bumiller.mol.kotlinConfig
import me.bumiller.mol.multiplatformPlugin
import me.bumiller.mol.testDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Gradle module that configures common properties for all modules that act as a library and use android dependencies.
 */
class AndroidLibraryModule : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        multiplatformPlugin()
        androidLibraryPlugin()

        kotlinConfig(isAndroid = true)
        androidConfig()

        dependencies()
        testDependencies()
        androidDependencies()
        androidInstrumentedTestDependencies()
    }

}
