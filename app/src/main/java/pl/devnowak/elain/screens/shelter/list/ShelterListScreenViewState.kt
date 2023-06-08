package pl.devnowak.elain.screens.shelter.list

import pl.devnowak.elain.model.ShelterEntity

sealed interface ShelterListScreenViewState {
    object Loading : ShelterListScreenViewState

    data class Error(val message: String) : ShelterListScreenViewState
    data class Completed(val data: List<ShelterEntity>) : ShelterListScreenViewState
}