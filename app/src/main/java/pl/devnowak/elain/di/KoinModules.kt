package pl.devnowak.elain.di

import org.koin.dsl.module
import pl.devnowak.elain.usecases.FetchShelterUseCase

val moduleA = module {
    single { FetchShelterUseCase() }
}