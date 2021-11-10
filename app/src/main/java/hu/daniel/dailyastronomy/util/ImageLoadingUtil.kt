package hu.daniel.dailyastronomy.util

import android.graphics.Bitmap
import android.widget.ImageView
import coil.api.load
import coil.decode.DataSource
import coil.request.LoadRequestBuilder
import coil.request.Request
import coil.transform.BlurTransformation

fun ImageView.blurBitmap(bitmap: Bitmap, endListener: () -> Unit) {
    load(bitmap) {
        onDownloadSuccess { endListener.invoke() }
        transformations(BlurTransformation(context, 20F))
    }
}

fun LoadRequestBuilder.onDownloadSuccess(successListener: () -> Unit) {
    listener(object: Request.Listener {
        override fun onSuccess(data: Any, source: DataSource) {
            successListener.invoke()
            super.onSuccess(data, source)
        }
    })
}

fun LoadRequestBuilder.onDownload(startListener: () -> Unit, completeListener: () -> Unit) {
    listener(object: Request.Listener {
        override fun onStart(data: Any) {
            startListener.invoke()
            super.onStart(data)
        }

        override fun onSuccess(data: Any, source: DataSource) {
            completeListener.invoke()
            super.onSuccess(data, source)
        }

        override fun onCancel(data: Any?) {
            completeListener.invoke()
            super.onCancel(data)
        }

        override fun onError(data: Any?, throwable: Throwable) {
            completeListener.invoke()
            super.onError(data, throwable)
        }
    })
}