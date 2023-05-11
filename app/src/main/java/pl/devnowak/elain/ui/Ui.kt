package pl.devnowak.elain.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.java.KoinJavaComponent.inject
import pl.devnowak.elain.ShelterListViewModel
import pl.devnowak.elain.ShelterListViewState
import pl.devnowak.elain.usecases.FetchShelterUseCase

@Composable
fun ShelterList() {

    val useCase: FetchShelterUseCase by inject(FetchShelterUseCase::class.java)

    val viewModel: ShelterListViewModel = remember {
        ShelterListViewModel(useCase = useCase)
    }

    val state = viewModel.state.collectAsState()

    val value = state.value

    // 1. ostylować listę i loading
    // 2. przenieść logikę pobierania do osobnej klasy
    // 3. wstrzyknąć FetchUseCase JAKOŚ – ręczne tworzenie, koin, dagger

    when (value) {
        ShelterListViewState.Loading -> {
            val strokeWidth = 8.dp

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(128.dp),
                    strokeWidth = strokeWidth
                )
            }
        }

        is ShelterListViewState.Completed -> {
            LazyColumn {
                items(value.data) {
                    Card(
                        shape = MaterialTheme.shapes.medium,
                        backgroundColor = Color.Cyan,
                        modifier = Modifier.padding(10.dp).fillMaxWidth(),
                        elevation = 20.dp
                    ) {
                        Text(
                            text = it.name,
                            fontSize = 32.sp,
                            modifier = Modifier.padding(10.dp).clickable {

                            },
                        )
                    }
                }
            }
        }
    }
}