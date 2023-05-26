package pl.devnowak.elain.di

import org.koin.dsl.module
import pl.devnowak.elain.model.KtorNetworkClient
import pl.devnowak.elain.model.NetworkClient
import pl.devnowak.elain.usecases.FetchShelterUseCase

val moduleA = module {
    single<NetworkClient> { KtorNetworkClient() }
    single { FetchShelterUseCase(get()) }
}