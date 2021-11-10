@file:Suppress("unused")

package hu.daniel.dailyastronomy.util

import android.annotation.SuppressLint
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import coil.api.load
import hu.daniel.dailyastronomy.R
import hu.daniel.dailyastronomy.dto.Article


@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String?) {
    load(url) {
        placeholder(R.drawable.default_article_image)
        fallback(R.drawable.default_article_image)
        error(R.drawable.default_article_image)
    }
}

@BindingAdapter("imageUrlWithoutPlaceholder")
fun ImageView.loadImageWithoutPlaceholder(url: String?) {
    load(url)
}


@BindingAdapter("visibleIf")
fun View.visibleIf(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("imageSource")
fun ImageView.imageSource(liveSource: MutableLiveData<Int>) {
    liveSource.value?.let { id -> setImageResource(id) }
}

@BindingAdapter("textFromHtml")
fun TextView.textFromHtml(htmlText: String?) {
    text = if (htmlText != null) handleHtmlIn(htmlText) else null
}

@BindingAdapter("android:src")
fun ImageView.setImageUri(imageId: Int) {
    setImageResource(imageId)
}

@BindingAdapter("articleContent")
fun TextView.articleContent(liveSource: MutableLiveData<Article>) {
    liveSource.value?.let{ text = handleHtmlIn(it.content ?: it.description ?: "") }
}

@SuppressLint("SetJavaScriptEnabled")
@BindingAdapter("loadUrl")
fun WebView.loadUrlWithConfig(url: String) {
    webViewClient = WebViewClient()
    settings.javaScriptEnabled = true
    settings.javaScriptCanOpenWindowsAutomatically = true
    loadUrl(url)
}