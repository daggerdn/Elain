package pl.devnowak.elain.screens.kennel.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import pl.devnowak.elain.model.KennelEntity
import timber.log.Timber

class KennelListScreenViewModel(val useCase: FetchKennelUseCase) : ViewModel() {

    var data: List<KennelEntity> = emptyList()

    val state: MutableStateFlow<KennelListScreenViewState> = MutableStateFlow(
        KennelListScreenViewState.Loading
    )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                data = useCase.fetch(10)
                Timber.d("FETCHING 10")
                state.value = KennelListScreenViewState.Completed(data)
            } catch (e: Exception) {
                Timber.e(e,"Exception")
                state.value = KennelListScreenViewState.Error(e.message.orEmpty())
            }
        }
    }
}