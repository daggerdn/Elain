package pl.devnowak.elain.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.devnowak.elain.db.RoomDatabase
import pl.devnowak.elain.model.KtorNetworkClient
import pl.devnowak.elain.model.NetworkClient
import pl.devnowak.elain.screens.kennel.list.FetchKennelUseCase
import pl.devnowak.elain.screens.shelter.details.ShelterDetailsScreenViewModel
import pl.devnowak.elain.screens.shelter.details.FetchDetailsByIdUseCase
import pl.devnowak.elain.screens.shelter.list.FetchShelterUseCase

val moduleA = module {

    // Other
    single<NetworkClient> { KtorNetworkClient() }

    // Shelter
    single { RoomDatabase(androidContext()).getShelterDao()}

    single { FetchShelterUseCase(get(), get()) }
    single { FetchDetailsByIdUseCase(get()) }
    viewModel { params -> ShelterDetailsScreenViewModel(useCase = get(), id = params.get()) }


    // Kennel
    single { RoomDatabase(androidContext()).getKennelDao()}
    single { FetchKennelUseCase(get(), get()) }
}