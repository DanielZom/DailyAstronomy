@file:Suppress("unused")

package hu.daniel.dailyastronomy.util.extensions

import android.app.Activity
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.PixelCopy
import android.view.View
import android.view.ViewTreeObserver
import android.view.Window
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.RecyclerView
import java.lang.StrictMath.sqrt
import kotlin.math.pow

@Suppress("DEPRECATION")
fun Activity.isTablet(): Boolean {
    val display = windowManager.defaultDisplay
    val metrics = DisplayMetrics()
    display.getMetrics(metrics)
    val widthInches = metrics.widthPixels / metrics.xdpi
    val heightInches = metrics.heightPixels / metrics.ydpi
    val diagonalInches = sqrt(widthInches.toDouble().pow(2.0) + heightInches.toDouble().pow(2.0))
    return diagonalInches >= 7.0 // minimum tablet size: 7".
}

fun RecyclerView.onScrollReachedBottom(listener: () -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (!recyclerView.canScrollVertically(1)) listener.invoke()
        }
    })
}

@Suppress("DEPRECATION")
val Activity.screenWidth: Int
    get() {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

fun View.onViewRendered(callback: () -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            callback.invoke()
            viewTreeObserver.removeOnGlobalLayoutListener(this)
        }
    })
}

fun View.isVisible() = visibility == View.VISIBLE

@Suppress("DEPRECATION")
fun View.takeScreenshot(window: Window, callback: (Bitmap) -> Unit) {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val locationOfViewInWindow = IntArray(2)
    getLocationInWindow(locationOfViewInWindow)
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            PixelCopy.request(window,
                    Rect(locationOfViewInWindow[0],
                            locationOfViewInWindow[1],
                            locationOfViewInWindow[0] + width,
                            locationOfViewInWindow[1] + height
                    ), bitmap, { copyResult ->
                if (copyResult == PixelCopy.SUCCESS) callback(bitmap)
            }, Handler(Looper.getMainLooper()))
        } else {
            isDrawingCacheEnabled = true
            val screenshot = Bitmap.createBitmap(drawingCache)
            isDrawingCacheEnabled = false
            callback(screenshot)
        }
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    }
}

fun View.setTopPaddingForStatusbar() {
    fitsSystemWindows = true
    updatePadding(top = resources.getStatusbarHeight())
}

fun Resources.getStatusbarHeight(): Int {
    var height = 0
    val resourceId = getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) height = getDimensionPixelSize(resourceId)
    return height
}