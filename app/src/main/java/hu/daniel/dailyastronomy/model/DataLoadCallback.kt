package hu.daniel.dailyastronomy.model


interface DataLoadCallback {
    fun onStart()
    fun <R>onSuccess(response: R)
    fun onError(error: Throwable)
    fun onComplete()
}