package hu.daniel.dailyastronomy.util.extensions

import android.content.Context
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatDelegate
import hu.daniel.dailyastronomy.model.PreferencesRepository
import hu.daniel.dailyastronomy.presentation.main.settings.ThemeState

const val THEME_PERSIST_KEY = "THEME_PERSIST_KEY"

fun initTheme(preferences: PreferencesRepository, context: Context) {
    val savedThemeState = preferences.current.theme

    if (savedThemeState == null) {
        switchAndPersistTheme(preferences, if (isOSInDarkMode(context)) ThemeState.DARK else ThemeState.LIGHT)
    } else {
        switchTheme(savedThemeState)
    }
}

fun switchAndPersistTheme(preferences: PreferencesRepository, state: ThemeState) {
    preferences.saveTheme(state)
    switchTheme(state)
}

private fun switchTheme(state: ThemeState) {
    val themeMode = if (state == ThemeState.DARK) {
        AppCompatDelegate.MODE_NIGHT_YES
    } else {
        AppCompatDelegate.MODE_NIGHT_NO
    }
    AppCompatDelegate.setDefaultNightMode(themeMode)
}

private fun isOSInDarkMode(context: Context): Boolean {
    val nightModeFlags: Int =
        context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    return nightModeFlags == UI_MODE_NIGHT_YES
}