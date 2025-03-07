package me.bumiller.mol

import android.app.Application
import android.content.Context
import me.bumiller.mol.di.initKoin
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.component.KoinComponent
import org.koin.dsl.binds
import org.koin.dsl.module

class MolApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()

        initKoin {

            modules(
                module {
                    single { this@MolApplication } binds arrayOf(Context::class, Application::class)
                }
            )

            workManagerFactory()
        }
    }

}