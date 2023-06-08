package pl.devnowak.elain.screens.shelter.details

import pl.devnowak.elain.db.ShelterDao
import pl.devnowak.elain.model.ShelterEntity

class FetchDetailsByIdUseCase(private val dao: ShelterDao) {

     fun fetch(id: Int): ShelterEntity? {
        return dao.loadAllById(id)
    }

}