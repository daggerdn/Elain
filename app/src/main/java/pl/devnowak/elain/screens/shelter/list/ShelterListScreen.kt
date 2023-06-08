package pl.devnowak.elain.screens.shelter.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.koin.java.KoinJavaComponent
import pl.devnowak.elain.model.ShelterEntity
import pl.devnowak.elain.screens.shared.SharedViewModel
import pl.devnowak.elain.ui.SearchBarEx
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShelterListScreen(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    navigateToDetails: (Int) -> Unit

) {

    val useCase: FetchShelterUseCase by KoinJavaComponent.inject(FetchShelterUseCase::class.java)

    val viewModel: ShelterListScreenViewModel = remember {
        ShelterListScreenViewModel(useCase = useCase)
    }

    val state = viewModel.state.collectAsState()

    val value = state.value

    when (value) {
        ShelterListScreenViewState.Loading -> {
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
        is ShelterListScreenViewState.Error -> {
            Text(
                text ="Error",
                fontSize = 32.sp,
                modifier = Modifier.padding(10.dp)
            )
        }
        is ShelterListScreenViewState.Completed -> {
            SearchBarEx()
            Spacer(modifier = Modifier.size(10.dp))
            LazyColumn {
                items(value.data) {
                    Card(
                        shape = MaterialTheme.shapes.medium,
                        backgroundColor = Color.Cyan,
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        elevation = 20.dp,
                        onClick = {
                            Timber.d("ONCLICK")
                            val entity = ShelterEntity(
                                id = it.id,
                                name = it.name,
                                description = it.description
                            )
                            sharedViewModel.addShelterEntity(newEntity = entity)
//                            navController.currentBackStackEntry?.savedStateHandle?.set(
//                                key = "entity",
//                                value = entity
//                            )
                            navigateToDetails(entity.id)
                        }
                    ) {
                        Text(
                            text = it.name,
                            fontSize = 32.sp,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }
        }
    }
}