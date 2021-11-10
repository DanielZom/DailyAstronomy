@file:JvmName("StringUtil")
package hu.daniel.dailyastronomy.util

import android.os.Build
import android.text.Html
import android.text.Spanned

@Suppress("DEPRECATION")
fun handleHtmlIn(text: String): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(text)
    }
}