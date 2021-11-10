package hu.daniel.dailyastronomy.dto

data class NewsResponseData(
    val status: String,
    val articles: ArrayList<Article>?
)