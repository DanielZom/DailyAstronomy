package hu.daniel.dailyastronomy.dto

data class SolarSystemObject(
        val name: String,
        val imageId: Int,
        val astronomicalSymbolId: Int,
        val wikiUrl: String,
        val characteristics: Map<String, String>
)