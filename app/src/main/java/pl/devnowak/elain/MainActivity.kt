package pl.devnowak.elain

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import pl.devnowak.elain.ui.theme.ElainTheme

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      ElainTheme {
        // A surface container using the 'background' color from the theme
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colors.background
        ) {
          ShelterList()
        }
      }
    }
  }
}

@Composable
fun ShelterList() {

  val viewModel: ShelterListViewModel = remember {
    ShelterListViewModel()
  }

  val state = viewModel.state.collectAsState()

  val value = state.value

  // 1. ostylować listę i loading
  // 2. przenieść logikę pobierania do osobnej klasy
  // 3. wstrzyknąć FetchUseCase JAKOŚ – ręczne tworzenie, koin, dagger

  when (value) {
    ShelterListViewState.Loading -> {
      CircularProgressIndicator()
    }

    is ShelterListViewState.Completed -> {
      LazyColumn {
        items(value.data) {
          Text(text = it.name)
        }
      }
    }
  }
}

sealed interface ShelterListViewState {
  object Loading : ShelterListViewState
  data class Completed(val data: List<ShelterEntity>) : ShelterListViewState
}

class ShelterListViewModel : ViewModel() {

  val data: List<ShelterEntity> = List(15) {
    ShelterEntity(
      id = it,
      name = "Name $it",
      description = "Description $it"
    )
  }

  val state: MutableStateFlow<ShelterListViewState> = MutableStateFlow(
    ShelterListViewState.Loading
  )

  init {
    viewModelScope.launch {
      delay(5000)
      state.value = ShelterListViewState.Completed(data)
    }
  }

}

class FetchShelterUseCase() {

  val data: List<ShelterEntity> = List(15) {
    ShelterEntity(
      id = it,
      name = "Name $it",
      description = "Description $it"
    )
  }

  suspend fun fetch(count: Int): List<ShelterEntity> {
    // count - ile schronisk pobrać?

    // zaczekać 5 sekund

    // zwrócić listę
    TODO()
  }
}

data class ShelterEntity(val id: Int, val name: String, val description: String)

@Composable
fun Greeting(name: String) {
  Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  ElainTheme {
    Greeting("Android")
  }
}