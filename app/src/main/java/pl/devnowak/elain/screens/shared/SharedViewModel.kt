package pl.devnowak.elain.screens.shared

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import pl.devnowak.elain.model.ShelterEntity

class SharedViewModel: ViewModel() {

    var entity by mutableStateOf<ShelterEntity?>(null)
        private set

    fun addShelterEntity(newEntity: ShelterEntity) {
        entity = newEntity
    }

}