package com.example.newsapp.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.newsapp.viewModel.NewsViewModel
import java.util.Locale
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.material3.TextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction


@Composable
fun NewsScreen(navController: NavController, viewModel: NewsViewModel) {

    val newsList by viewModel.newsList.observeAsState(emptyList())
    val error by viewModel.error.observeAsState("")
//    val country by viewModel.country.observeAsState("us")
//    val category by viewModel.category.observeAsState("general")

//    val countryMap = mapOf(
//        "us" to "United States",
//        "gb" to "Great Britain",
//        "fr" to "France",
//        "de" to "Germany",
//        "jp" to "Japan",
//    )
//    val CountryName = countryMap[country] ?: "Unknown"

//    val selectedCountry = "us"
//    val selectedCategory = "sports"

    //val selectedTopic = "trump"
    var searchInput by remember { mutableStateOf("") }
    var selectedTopic by remember { mutableStateOf("everything") }
    var searchTriggered by remember { mutableStateOf(false) }

//          Issue when in news content and pressing the back button
//          it takes you back to everything instead of the chosen topic
//    LaunchedEffect(Unit) {
//        viewModel.setString(selectedTopic)
//    }

    if (searchTriggered) {
        LaunchedEffect(selectedTopic) {
            viewModel.setString(selectedTopic)
            searchInput = ""
            searchTriggered = false
        }
    }

    if (error.isNotEmpty()) {
        Text(text = "Error: $error", color = MaterialTheme.colorScheme.error)
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Gray)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Topic: ${if (selectedTopic == "everything") "All News" 
                                        else selectedTopic.replaceFirstChar { 
                                            if (it.isLowerCase()) it.titlecase(Locale.ROOT) 
                                            else it.toString()
                                        //when back button is pressed it displayed all news 
                                        // instead of the selected topic
                        }}",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    TextField(
                        value = searchInput,
                        onValueChange = { searchInput = it },
                        label = { Text("Enter Topic") },
                        maxLines = 1,
                        textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(20.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                if (searchInput.isNotEmpty()) {
                                    selectedTopic = searchInput
                                    searchTriggered = true
                                }
                            }
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(newsList) { article ->
                    NewsItem(article = article, onClick = {
                        navController.navigate("news_detail/${article.title}")
                    })
                }
            }
        }
    }
}