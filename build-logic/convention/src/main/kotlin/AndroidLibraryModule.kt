import me.bumiller.civoris.androidConfig
import me.bumiller.civoris.androidPlugin
import me.bumiller.civoris.applyPlugin
import me.bumiller.civoris.baseDependencies
import me.bumiller.civoris.kotlinConfig
import me.bumiller.civoris.testDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Gradle module that configures common properties for all modules that act as a library and use android dependencies.
 */
class AndroidLibraryModule : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        applyPlugin("me.bumiller.civoris.kotlin.library")

        androidPlugin()

        kotlinConfig(isAndroid = true)
        androidConfig()

        baseDependencies()
        testDependencies()
    }

}
