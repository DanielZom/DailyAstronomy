package hu.daniel.dailyastronomy.dto

import java.util.*

data class Article(
    val source: ArticleSource,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: Date,
    val content: String?,
) {
    val formattedSource: String
        get() = "${if (author != null) "by $author -" else "by"} ${source.name}"
}

data class ArticleSource(val name: String)