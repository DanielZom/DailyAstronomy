package hu.daniel.dailyastronomy.presentation.main.live

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LiveViewModel : ViewModel() {
    val isStatusbarDark = MutableLiveData(false)

    fun darkenStatusbar() {
        isStatusbarDark.value = true
    }

    fun resetStatusbar() {
        isStatusbarDark.value = false
    }
}