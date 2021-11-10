package hu.daniel.dailyastronomy.model

import android.content.Context
import hu.daniel.dailyastronomy.R
import hu.daniel.dailyastronomy.dto.SolarSystemObject

interface SolarSystemRepository {
    val solarSystem: List<SolarSystemObject>
}

class SolarSystemRepositoryImpl(val context: Context): SolarSystemRepository {
    override val solarSystem = createSolarSystem()

    private fun createSolarSystem(): List<SolarSystemObject> {
        val system = arrayListOf<SolarSystemObject>()
        system.add(SolarSystemObject(
                context.getString(R.string.sun),
                R.drawable.sun,
                R.drawable.symbol_sun,
                "https://en.wikipedia.org/wiki/sun",
                mapOf(
                        context.getString(R.string.sun_fact_1_name) to context.getString(R.string.sun_fact_1_value),
                        context.getString(R.string.sun_fact_2_name) to context.getString(R.string.sun_fact_2_value),
                        context.getString(R.string.sun_fact_3_name) to context.getString(R.string.sun_fact_3_value),
                        context.getString(R.string.sun_fact_4_name) to context.getString(R.string.sun_fact_4_value),
                        context.getString(R.string.sun_fact_5_name) to context.getString(R.string.sun_fact_5_value))
        ))
        system.add(SolarSystemObject(
                context.getString(R.string.mercury),
                R.drawable.mercury,
                R.drawable.symbol_mercury,
                "https://en.wikipedia.org/wiki/Mercury_(planet)",
                mapOf(
                        context.getString(R.string.mercury_fact_1_name) to context.getString(R.string.mercury_fact_1_value),
                        context.getString(R.string.mercury_fact_2_name) to context.getString(R.string.mercury_fact_2_value),
                        context.getString(R.string.mercury_fact_3_name) to context.getString(R.string.mercury_fact_3_value),
                        context.getString(R.string.mercury_fact_4_name) to context.getString(R.string.mercury_fact_4_value),
                        context.getString(R.string.mercury_fact_5_name) to context.getString(R.string.mercury_fact_5_value))
        ))
        system.add(SolarSystemObject(
                context.getString(R.string.venus),
                R.drawable.venus,
                R.drawable.symbol_venus,
                "https://en.wikipedia.org/wiki/Venus",
                mapOf(
                        context.getString(R.string.venus_fact_1_name) to context.getString(R.string.venus_fact_1_value),
                        context.getString(R.string.venus_fact_2_name) to context.getString(R.string.venus_fact_2_value),
                        context.getString(R.string.venus_fact_3_name) to context.getString(R.string.venus_fact_3_value),
                        context.getString(R.string.venus_fact_4_name) to context.getString(R.string.venus_fact_4_value),
                        context.getString(R.string.venus_fact_5_name) to context.getString(R.string.venus_fact_5_value))
        ))
        system.add(SolarSystemObject(
                context.getString(R.string.earth),
                R.drawable.earth,
                R.drawable.symbol_earth,
                "https://en.wikipedia.org/wiki/Earth",
                mapOf(
                        context.getString(R.string.earth_fact_1_name) to context.getString(R.string.earth_fact_1_value),
                        context.getString(R.string.earth_fact_2_name) to context.getString(R.string.earth_fact_2_value),
                        context.getString(R.string.earth_fact_3_name) to context.getString(R.string.earth_fact_3_value),
                        context.getString(R.string.earth_fact_4_name) to context.getString(R.string.earth_fact_4_value),
                        context.getString(R.string.earth_fact_5_name) to context.getString(R.string.earth_fact_5_value))
        ))
        system.add(SolarSystemObject(
                context.getString(R.string.moon),
                R.drawable.moon,
                R.drawable.symbol_moon,
                "https://en.wikipedia.org/wiki/Moon",
                mapOf(
                        context.getString(R.string.moon_fact_1_name) to context.getString(R.string.moon_fact_1_value),
                        context.getString(R.string.moon_fact_2_name) to context.getString(R.string.moon_fact_2_value),
                        context.getString(R.string.moon_fact_3_name) to context.getString(R.string.moon_fact_3_value),
                        context.getString(R.string.moon_fact_4_name) to context.getString(R.string.moon_fact_4_value),
                        context.getString(R.string.moon_fact_5_name) to context.getString(R.string.moon_fact_5_value))
        ))
        system.add(SolarSystemObject(
                context.getString(R.string.mars),
                R.drawable.mars,
                R.drawable.symbol_mars,
                "https://en.wikipedia.org/wiki/Mars",
                mapOf(
                        context.getString(R.string.mars_fact_1_name) to context.getString(R.string.mars_fact_1_value),
                        context.getString(R.string.mars_fact_2_name) to context.getString(R.string.mars_fact_2_value),
                        context.getString(R.string.mars_fact_3_name) to context.getString(R.string.mars_fact_3_value),
                        context.getString(R.string.mars_fact_4_name) to context.getString(R.string.mars_fact_4_value),
                        context.getString(R.string.mars_fact_5_name) to context.getString(R.string.mars_fact_5_value))
        ))
        system.add(SolarSystemObject(
                context.getString(R.string.ceres),
                R.drawable.ceres,
                R.drawable.symbol_ceres,
                "https://en.wikipedia.org/wiki/Ceres_(dwarf_planet)",
                mapOf(
                        context.getString(R.string.ceres_fact_1_name) to context.getString(R.string.ceres_fact_1_value),
                        context.getString(R.string.ceres_fact_2_name) to context.getString(R.string.ceres_fact_2_value),
                        context.getString(R.string.ceres_fact_3_name) to context.getString(R.string.ceres_fact_3_value),
                        context.getString(R.string.ceres_fact_4_name) to context.getString(R.string.ceres_fact_4_value),
                        context.getString(R.string.ceres_fact_5_name) to context.getString(R.string.ceres_fact_5_value))
        ))
        system.add(SolarSystemObject(
                context.getString(R.string.jupiter),
                R.drawable.jupiter,
                R.drawable.symbol_jupiter,
                "https://en.wikipedia.org/wiki/Jupiter",
                mapOf(
                        context.getString(R.string.jupiter_fact_1_name) to context.getString(R.string.jupiter_fact_1_value),
                        context.getString(R.string.jupiter_fact_2_name) to context.getString(R.string.jupiter_fact_2_value),
                        context.getString(R.string.jupiter_fact_3_name) to context.getString(R.string.jupiter_fact_3_value),
                        context.getString(R.string.jupiter_fact_4_name) to context.getString(R.string.jupiter_fact_4_value),
                        context.getString(R.string.jupiter_fact_5_name) to context.getString(R.string.jupiter_fact_5_value))
        ))
        system.add(SolarSystemObject(
                context.getString(R.string.saturn),
                R.drawable.saturn,
                R.drawable.symbol_saturn,
                "https://en.wikipedia.org/wiki/Saturn",
                mapOf(
                        context.getString(R.string.saturn_fact_1_name) to context.getString(R.string.saturn_fact_1_value),
                        context.getString(R.string.saturn_fact_2_name) to context.getString(R.string.saturn_fact_2_value),
                        context.getString(R.string.saturn_fact_3_name) to context.getString(R.string.saturn_fact_3_value),
                        context.getString(R.string.saturn_fact_4_name) to context.getString(R.string.saturn_fact_4_value),
                        context.getString(R.string.saturn_fact_5_name) to context.getString(R.string.saturn_fact_5_value))
        ))
        system.add(SolarSystemObject(
                context.getString(R.string.uranus),
                R.drawable.uranus,
                R.drawable.symbol_uranus,
                "https://en.wikipedia.org/wiki/Uranus",
                mapOf(
                        context.getString(R.string.uranus_fact_1_name) to context.getString(R.string.uranus_fact_1_value),
                        context.getString(R.string.uranus_fact_2_name) to context.getString(R.string.uranus_fact_2_value),
                        context.getString(R.string.uranus_fact_3_name) to context.getString(R.string.uranus_fact_3_value),
                        context.getString(R.string.uranus_fact_4_name) to context.getString(R.string.uranus_fact_4_value),
                        context.getString(R.string.uranus_fact_5_name) to context.getString(R.string.uranus_fact_5_value))
        ))
        system.add(SolarSystemObject(
                context.getString(R.string.neptune),
                R.drawable.neptune,
                R.drawable.symbol_neptune,
                "https://en.wikipedia.org/wiki/Neptune",
                mapOf(
                        context.getString(R.string.neptune_fact_1_name) to context.getString(R.string.neptune_fact_1_value),
                        context.getString(R.string.neptune_fact_2_name) to context.getString(R.string.neptune_fact_2_value),
                        context.getString(R.string.neptune_fact_3_name) to context.getString(R.string.neptune_fact_3_value),
                        context.getString(R.string.neptune_fact_4_name) to context.getString(R.string.neptune_fact_4_value),
                        context.getString(R.string.neptune_fact_5_name) to context.getString(R.string.neptune_fact_5_value))
        ))
        system.add(SolarSystemObject(
                context.getString(R.string.pluto),
                R.drawable.pluto,
                R.drawable.symbol_pluto,
                "https://en.wikipedia.org/wiki/Pluto_(dwarf_planet)",
                mapOf(
                        context.getString(R.string.pluto_fact_1_name) to context.getString(R.string.pluto_fact_1_value),
                        context.getString(R.string.pluto_fact_2_name) to context.getString(R.string.pluto_fact_2_value),
                        context.getString(R.string.pluto_fact_3_name) to context.getString(R.string.pluto_fact_3_value),
                        context.getString(R.string.pluto_fact_4_name) to context.getString(R.string.pluto_fact_4_value),
                        context.getString(R.string.pluto_fact_5_name) to context.getString(R.string.pluto_fact_5_value))
        ))
        system.add(SolarSystemObject(
                context.getString(R.string.eris),
                R.drawable.eris,
                R.drawable.symbol_eris,
                "https://en.wikipedia.org/wiki/Eris_(dwarf_planet)",
                mapOf(
                        context.getString(R.string.eris_fact_1_name) to context.getString(R.string.eris_fact_1_value),
                        context.getString(R.string.eris_fact_2_name) to context.getString(R.string.eris_fact_2_value),
                        context.getString(R.string.eris_fact_3_name) to context.getString(R.string.eris_fact_3_value),
                        context.getString(R.string.eris_fact_4_name) to context.getString(R.string.eris_fact_4_value),
                        context.getString(R.string.eris_fact_5_name) to context.getString(R.string.eris_fact_5_value))
        ))
        return system
    }
}