import me.bumiller.civoris.androidApplicationConfig
import me.bumiller.civoris.androidPlugin
import me.bumiller.civoris.applyPlugin
import me.bumiller.civoris.baseDependencies
import me.bumiller.civoris.kotlinConfig
import me.bumiller.civoris.testDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Gradle module that configures common properties for all modules that act as a android application.
 */
class AndroidApplicationModule : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        applyPlugin("me.bumiller.civoris.kotlin.library")

        androidPlugin(application = true)

        kotlinConfig(isAndroid = true)
        androidApplicationConfig()

        baseDependencies()
        testDependencies()
    }

}
