package hu.daniel.dailyastronomy

import android.app.Application
import com.facebook.stetho.Stetho
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import hu.daniel.dailyastronomy.model.PreferencesRepository
import hu.daniel.dailyastronomy.util.NetworkObserver
import hu.daniel.dailyastronomy.util.extensions.initTheme
import hu.daniel.dailyastronomy.util.onDebug
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class DailyAstronomyApplication : Application() {

    private val preferences: PreferencesRepository by inject()
    private val networkObserver: NetworkObserver by inject()

    override fun onCreate() {
        super.onCreate()
        setupKoin()
        Stetho.initializeWithDefaults(this)
        onDebug { Timber.plant(Timber.DebugTree()) }
        initTheme(preferences, this)
        networkObserver.observeNetworkChange()
        AppCenter.start(this, BuildConfig.APPCENTER_KEY, Analytics::class.java, Crashes::class.java)
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@DailyAstronomyApplication)
            onDebug { androidLogger(org.koin.core.logger.Level.ERROR) }
            modules(KoinModule)
        }
    }
}