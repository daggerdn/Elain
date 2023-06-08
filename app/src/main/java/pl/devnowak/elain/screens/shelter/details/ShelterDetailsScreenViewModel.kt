package pl.devnowak.elain.screens.shelter.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import pl.devnowak.elain.model.ShelterEntity

class ShelterDetailsScreenViewModel(useCase: FetchDetailsByIdUseCase, id: Int): ViewModel() {

    val state: MutableStateFlow<ShelterEntity> = MutableStateFlow(ShelterEntity(1, "", ""))

    init {
        viewModelScope.launch(Dispatchers.IO) {
            state.value = useCase.fetch(id)!!
        }
    }

}