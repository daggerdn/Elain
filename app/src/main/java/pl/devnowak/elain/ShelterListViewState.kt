package pl.devnowak.elain

import pl.devnowak.elain.model.ShelterEntity

sealed interface ShelterListViewState {
    object Loading : ShelterListViewState
    data class Completed(val data: List<ShelterEntity>) : ShelterListViewState
}