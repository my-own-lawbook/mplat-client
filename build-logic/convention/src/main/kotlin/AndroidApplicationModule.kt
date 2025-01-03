import me.bumiller.mol.androidApplicationConfig
import me.bumiller.mol.androidPlugin
import me.bumiller.mol.applyPlugin
import me.bumiller.mol.baseDependencies
import me.bumiller.mol.kotlinConfig
import me.bumiller.mol.testDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Gradle module that configures common properties for all modules that act as a android application.
 */
class AndroidApplicationModule : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        applyPlugin("me.bumiller.mol.kotlin.library")

        androidPlugin(application = true)

        kotlinConfig(isAndroid = true)
        androidApplicationConfig()

        baseDependencies()
        testDependencies()
    }

}
