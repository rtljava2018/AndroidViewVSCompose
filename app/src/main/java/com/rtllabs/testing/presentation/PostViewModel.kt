package com.rtllabs.testing.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rtllabs.testing.domain.GetUseCasePost
import com.rtllabs.testing.domain.Posts
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostViewModel(val getUseCasePost: GetUseCasePost): ViewModel() {
    private val _post= MutableStateFlow<UiState<List<Posts>>>(UiState.Loading)
    val post: StateFlow<UiState<List<Posts>>> =_post.asStateFlow()


    // Define a handler
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _post.value = UiState.Error(throwable.message ?: "Unknown error")
    }

    /*
    *   private val _post= MutableLiveData<UiState<List<Posts>>>()
        val post: LiveData<UiState<List<Posts>>> =_post
    * */
    init {
        fetchPost()
    }

    fun fetchPost(){
        /*viewModelScope.launch {
            _post.value= UiState.Loading
            //Todo coroutine exception
            try {
                val result=getUseCasePost()
                delay(2000)
                _post.value= UiState.Success(result)
            }catch (e: Exception){
                _post.value= UiState.Error(e.message.toString())
            }

        }*/
        viewModelScope.launch(exceptionHandler) {
            _post.value = UiState.Loading
            delay(2000) // simulate network latency
            val result = getUseCasePost()
            _post.value = UiState.Success(result)
        }
    }


}