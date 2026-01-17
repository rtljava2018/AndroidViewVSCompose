package com.rtllabs.testing.composebased

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import com.rtllabs.testing.composebased.ui.theme.TestingTheme
import com.rtllabs.testing.data.PostRepoImpl
import com.rtllabs.testing.domain.GetUseCasePost
import com.rtllabs.testing.domain.Posts
import com.rtllabs.testing.presentation.PostViewModel
import com.rtllabs.testing.presentation.UiState
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class ComposeMainActivity : ComponentActivity() {
   /* private val factoryLazy: PostViewModelFactory by lazy {
        val repository= PostRepoImpl()
        val getUseCasePost= GetUseCasePost(repository)
        PostViewModelFactory(getUseCasePost)
    }*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

       // val viewmodel= ViewModelProvider.create(this,factoryLazy).get(PostViewModel::class.java)
      // val viewmodel:PostViewModel = hiltViewModel()

       setContent {
            TestingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, viewmodel: PostViewModel= hiltViewModel()) {
    PostScreen(modifier,viewmodel)
}

@Composable
fun PostScreen(modifier: Modifier, viewModel: PostViewModel) {
    val state by viewModel.post.collectAsState()
    when (state){
        is UiState.Error -> {

            val message = (state as UiState.Error).message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error: $message", color = Color.Red)
            }
        }
        UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

        }
        is UiState.Success<*> -> {
            val posts = (state as UiState.Success<List<Posts>>).data
            PostList(modifier,posts = posts)
        }
    }
}

@Composable
fun PostList(modifier: Modifier, posts: List<Posts>) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp)
    ) {
        items(posts.size){ pos->
                    PostItem(posts[pos])
                    HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
        }

    }
}

@Composable
fun PostItem(post: Posts) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ){
        Text(
            text = post.title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = post.body,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
   /* TestingTheme {
        Greeting("Android", viewmodel = viewmodel)
    }*/
}