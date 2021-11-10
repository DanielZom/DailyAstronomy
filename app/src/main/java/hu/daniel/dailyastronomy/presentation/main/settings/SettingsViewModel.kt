package hu.daniel.dailyastronomy.presentation.main.settings

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hu.daniel.dailyastronomy.BuildConfig
import hu.daniel.dailyastronomy.R
import hu.daniel.dailyastronomy.model.PreferencesRepository
import hu.daniel.dailyastronomy.util.extensions.switchAndPersistTheme

class SettingsViewModel(
    private val context: Context,
    private val preferences: PreferencesRepository
) : ViewModel() {

    val themeState: MutableLiveData<ThemeState> = MutableLiveData<ThemeState>().apply {
        value = preferences.current.theme ?: ThemeState.DARK
    }
    val formattedAppVersion: String by lazy {
        "${context.getString(R.string.version_title)} ${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
    }
    val themeChanged: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    fun changeThemeTo(state: ThemeState) {
        if(state == themeState.value) return
        themeState.value = state
        themeChanged.value = true
        switchAndPersistTheme(preferences, state)
    }

    fun themeSwitchClicked(state: ThemeState) {
        changeThemeTo(state)
    }
}