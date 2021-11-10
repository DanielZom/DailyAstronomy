package hu.daniel.dailyastronomy.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hu.daniel.dailyastronomy.util.NetworkObserver

class MainViewModel(private val networkObserver: NetworkObserver) : ViewModel() {
    var actualScreenId: Int? = null

    val hasNetwork: MutableLiveData<Boolean>
        get() = networkObserver.hasNetwork
}