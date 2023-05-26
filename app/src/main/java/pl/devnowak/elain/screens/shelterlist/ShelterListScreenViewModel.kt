package pl.devnowak.elain.screens.shelterlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import pl.devnowak.elain.model.ShelterEntity
import pl.devnowak.elain.usecases.FetchShelterUseCase

class ShelterListScreenViewModel(val useCase: FetchShelterUseCase) : ViewModel() {

    var data: List<ShelterEntity> = emptyList()

    val state: MutableStateFlow<ShelterListScreenViewState> = MutableStateFlow(
        ShelterListScreenViewState.Loading
    )

    init {
        viewModelScope.launch {
            try {
                data = useCase.fetch(10)
                state.value = ShelterListScreenViewState.Completed(data)
            } catch (e: Exception) {
                state.value = ShelterListScreenViewState.Error(e.message.orEmpty())
            }
        }
    }

}