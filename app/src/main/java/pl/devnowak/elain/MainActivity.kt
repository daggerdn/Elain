package pl.devnowak.elain

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import pl.devnowak.elain.di.moduleA
import pl.devnowak.elain.ui.ShelterList
import pl.devnowak.elain.ui.theme.ElainTheme

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    startKoin {
      // Log Koin into Android logger
      androidLogger()
      // Reference Android context
      androidContext(this@MainActivity)
      // Load modules
      modules(moduleA)
    }

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

