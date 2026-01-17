package com.rtllabs.testing.viewbased

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rtllabs.testing.R
import com.rtllabs.testing.data.PostRepoImpl
import com.rtllabs.testing.data.RetrofitInstance
import com.rtllabs.testing.domain.GetUseCasePost
import com.rtllabs.testing.domain.PostRepository
import com.rtllabs.testing.presentation.PostViewModel
import com.rtllabs.testing.presentation.PostViewModelFactory
import com.rtllabs.testing.presentation.UiState
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var postAdapter: PostAdapter
    private lateinit var viewModel: PostViewModel
    /*private val factoryLazy: PostViewModelFactory by lazy {
        val repository= PostRepoImpl()
        val getUseCasePost= GetUseCasePost(repository)
        PostViewModelFactory(getUseCasePost)
    }*/

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel= ViewModelProvider.create(this).get(PostViewModel::class.java)
        postAdapter= PostAdapter()

        findViewById<RecyclerView>(R.id.rv_view).apply {
            layoutManager= LinearLayoutManager(this@MainActivity)
            adapter=postAdapter
        }
        /*viewModel.post.observe(this){ state ->
            when(state){
                is UiState.Loading->{
                    Log.e("Testing","Loading")
                }
                is UiState.Success->{
                    postAdapter.setData(state.data)
                }
                is UiState.Error->{
                //error staee
                    Log.e("Testing",state.toString())
                }

            }

        }*/

        lifecycleScope.launch {
            viewModel.post.collect{ state ->
                when(state){
                    is UiState.Loading->{
                        Log.e("Testing","Loading")
                    }
                    is UiState.Success->{
                        postAdapter.setData(state.data)
                    }
                    is UiState.Error->{
                        //error staee
                        Log.e("Testing",state.toString())
                    }

                }

            }
        }



    }
}