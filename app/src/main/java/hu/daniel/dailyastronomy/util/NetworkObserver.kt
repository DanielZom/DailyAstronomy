package hu.daniel.dailyastronomy.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.observers.DisposableObserver
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NetworkObserver(private val context: Context) : ConnectivityManager.NetworkCallback() {

    private var isNeedToTrackNetworkChange = false
    val hasNetwork = MutableLiveData(true)

    fun observeNetworkChange() {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(NetworkRequest.Builder().build(), this)
    }

    fun <R>createRequestObserver(onNext: (R) -> Unit, onError: (HttpException) -> Unit, onComplete: () -> Unit): DisposableObserver<R> {
        return object: DisposableObserver<R>() {
            override fun onNext(response: R) {
                checkNetwork()
                onNext.invoke(response)
            }

            override fun onError(e: Throwable?) {
                checkNetwork(e)
                e?.let { error -> onError.invoke(error as HttpException) }
            }

            override fun onComplete() {
                onComplete.invoke()
            }
        }
    }

    private fun checkNetwork(error: Throwable? = null) {
        val hasNetwork = error == null || (error !is UnknownHostException && error !is SocketTimeoutException)
        if (this.hasNetwork.value != hasNetwork) {
            this.hasNetwork.value = hasNetwork
        }
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        hasNetwork.postValue(false)
        isNeedToTrackNetworkChange = true
    }

    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities)
        if (isNeedToTrackNetworkChange) {
            hasNetwork.postValue(true)
            isNeedToTrackNetworkChange = false
        }
    }
}