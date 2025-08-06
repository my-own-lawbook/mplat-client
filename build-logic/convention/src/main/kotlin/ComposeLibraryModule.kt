import me.bumiller.civoris.applyPlugin
import me.bumiller.civoris.composeConfig
import me.bumiller.civoris.composeDependencies
import me.bumiller.civoris.composePlugins
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Gradle module that configures common properties for all modules that act as a library and use compose dependencies.
 */
class ComposeLibraryModule : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        applyPlugin("me.bumiller.civoris.android.library")

        composePlugins()
        composeConfig()

        composeDependencies()
    }

}