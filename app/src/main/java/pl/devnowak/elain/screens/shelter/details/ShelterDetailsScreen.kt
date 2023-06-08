package pl.devnowak.elain.app.details

import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.koin.core.parameter.parametersOf
import pl.devnowak.elain.screens.shared.SharedViewModel
import pl.devnowak.elain.screens.shelter.details.ShelterDetailsScreenViewModel
import timber.log.Timber

@Composable
fun ShelterDetailsScreen(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    id: Int
) {

//    val viewModel by KoinJavaComponent.inject<ShelterDetailsScreenViewModel>(
//        shelterDetailsScreenViewModel::class.java
//    ) { parametersOf(id) }

    val viewModel: ShelterDetailsScreenViewModel = koinViewModel(
        parameters = { parametersOf(id) }
    )

    val state = viewModel.state.collectAsState()

    val value = state.value

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
            text = "${value.id}",
            style = TextStyle(
                fontSize = MaterialTheme.typography.h3.fontSize,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = "${value.name}",
            style = TextStyle(
                fontSize = MaterialTheme.typography.h3.fontSize,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = "${value.description}",
            style = TextStyle(
                fontSize = MaterialTheme.typography.h3.fontSize,
                fontWeight = FontWeight.Bold
            )
        )
    }
}