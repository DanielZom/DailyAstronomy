package hu.daniel.dailyastronomy.model

import android.content.SharedPreferences
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import hu.daniel.dailyastronomy.dto.PreferencesModel
import hu.daniel.dailyastronomy.presentation.main.settings.ThemeState
import hu.daniel.dailyastronomy.util.extensions.THEME_PERSIST_KEY
import timber.log.Timber

class PreferencesRepository(private val preferences: SharedPreferences) {

    val current = PreferencesModel()

    private val theme = MutableLiveData<ThemeState?>().apply {
        val stringValue = preferences.getString(THEME_PERSIST_KEY, null)
        val theme = if (stringValue != null) ThemeState.valueOf(stringValue) else null
        value = theme
    }

    @Suppress("unused")
    val currentSettingsMediator = MediatorLiveData<PreferencesModel>().apply {
        addSource(theme) { themeState ->  current.theme = themeState }
        observeForever { Timber.i("Preferences updated: $current") }
    }

    fun saveTheme(value: ThemeState) = preferences.edit().run {
        putString(THEME_PERSIST_KEY, value.name)
        apply()
        theme.postValue(value)
    }
}