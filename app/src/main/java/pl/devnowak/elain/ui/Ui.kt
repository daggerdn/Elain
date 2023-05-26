package pl.devnowak.elain.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.koin.java.KoinJavaComponent.inject
import pl.devnowak.elain.screens.SharedViewModel
import pl.devnowak.elain.screens.shelterlist.ShelterListScreenViewModel
import pl.devnowak.elain.screens.shelterlist.ShelterListScreenViewState
import pl.devnowak.elain.model.ShelterEntity
import pl.devnowak.elain.usecases.FetchShelterUseCase
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShelterListScreen(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    navigateToDetails: () -> Unit

) {

    val useCase: FetchShelterUseCase by inject(FetchShelterUseCase::class.java)

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
                            navigateToDetails()
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

@Composable
fun ShelterDetailsScreen(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {

    val entity = sharedViewModel.entity
    LaunchedEffect(key1 = entity) {
        if(entity != null) {
            Timber.tag("DetailsScreen")
            Timber.d("${entity?.id}")
            Timber.tag("DetailsScreen")
            Timber.d("${entity?.name}")
            Timber.tag("DetailsScreen")
            Timber.d("${entity?.description}")
        }
    }

    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable { },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${entity?.id}",
            style = TextStyle(
                fontSize = MaterialTheme.typography.h3.fontSize,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = "${entity?.name}",
            style = TextStyle(
                fontSize = MaterialTheme.typography.h3.fontSize,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = "${entity?.description}",
            style = TextStyle(
                fontSize = MaterialTheme.typography.h3.fontSize,
                fontWeight = FontWeight.Bold
            )
        )
    }
}