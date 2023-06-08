package pl.devnowak.elain.screens.kennel.list

import pl.devnowak.elain.model.KennelEntity

sealed interface KennelListScreenViewState {
    object Loading : KennelListScreenViewState

    data class Error(val message: String) : KennelListScreenViewState
    data class Completed(val data: List<KennelEntity>) : KennelListScreenViewState
}