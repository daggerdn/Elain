package pl.devnowak.elain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import pl.devnowak.elain.model.ShelterEntity
import pl.devnowak.elain.usecases.FetchShelterUseCase

class ShelterListViewModel(val useCase: FetchShelterUseCase) : ViewModel() {

    var data: List<ShelterEntity> = emptyList()

    val state: MutableStateFlow<ShelterListViewState> = MutableStateFlow(
        ShelterListViewState.Loading
    )

    init {
        viewModelScope.launch {
            println("BEFORE FETCH")
            data = useCase.fetch(8)
            state.value = ShelterListViewState.Completed(data)
            println("AFTER FETCH")
        }
    }

}