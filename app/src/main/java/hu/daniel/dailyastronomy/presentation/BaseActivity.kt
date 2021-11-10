package hu.daniel.dailyastronomy.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import hu.daniel.dailyastronomy.model.PreferencesRepository
import hu.daniel.dailyastronomy.presentation.main.settings.ThemeState
import hu.daniel.dailyastronomy.util.extensions.isTablet
import hu.daniel.dailyastronomy.util.extensions.setTopPaddingForStatusbar
import org.koin.android.ext.android.inject
import timber.log.Timber

abstract class BaseActivity : AppCompatActivity() {

    private val preferences: PreferencesRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.e(isTablet().toString()) //TODO detect if device is tablet
    }

    fun <T : BaseActivity> navigateWithFinish(destination: Class<T>) {
        startActivity(Intent(this, destination))
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    fun configureStatusbar(topView: View, colorId: Int) {
        topView.setTopPaddingForStatusbar()
        window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            val fullScreenFlag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            val flags = if (preferences.current.theme == ThemeState.LIGHT) {
                fullScreenFlag or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                fullScreenFlag
            }
            decorView.systemUiVisibility = flags
            statusBarColor = ContextCompat.getColor(this@BaseActivity, colorId)
        }
    }
}