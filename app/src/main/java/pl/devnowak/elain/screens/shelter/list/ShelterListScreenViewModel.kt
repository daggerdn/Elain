package pl.devnowak.elain.screens.shelter.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import pl.devnowak.elain.model.ShelterEntity
import timber.log.Timber

class ShelterListScreenViewModel(val useCase: FetchShelterUseCase) : ViewModel() {

    var data: List<ShelterEntity> = emptyList()

    val state: MutableStateFlow<ShelterListScreenViewState> = MutableStateFlow(
        ShelterListScreenViewState.Loading
    )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                data = useCase.fetch(10)
                Timber.d("FETCHING 10")
                state.value = ShelterListScreenViewState.Completed(data)
            } catch (e: Exception) {
                Timber.e(e,"Exception")
                state.value = ShelterListScreenViewState.Error(e.message.orEmpty())
            }
        }
    }

}