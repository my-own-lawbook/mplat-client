import me.bumiller.mol.applyPlugin
import me.bumiller.mol.composeDependencies
import me.bumiller.mol.composePlugins
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Gradle module that configures common properties for all modules that act as an android library and uses compose dependencies.
 */
class ComposeApplicationModule : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        applyPlugin("me.bumiller.mol.android.application")

        composePlugins()

        composeDependencies()
    }

}