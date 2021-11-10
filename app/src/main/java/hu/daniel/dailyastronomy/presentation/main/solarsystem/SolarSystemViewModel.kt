package hu.daniel.dailyastronomy.presentation.main.solarsystem

import androidx.lifecycle.ViewModel
import hu.daniel.dailyastronomy.dto.SolarSystemObject
import hu.daniel.dailyastronomy.model.SolarSystemRepository

class SolarSystemViewModel(
        solarSystemRepository: SolarSystemRepository
) : ViewModel() {
    val solarSystemObjects: List<SolarSystemObject> = solarSystemRepository.solarSystem
}