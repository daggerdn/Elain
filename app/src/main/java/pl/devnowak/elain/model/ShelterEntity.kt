package pl.devnowak.elain.model

import kotlinx.serialization.Serializable

@Serializable
data class ShelterEntity(
    val id: Int,
    val name: String,
    val description: String
)
