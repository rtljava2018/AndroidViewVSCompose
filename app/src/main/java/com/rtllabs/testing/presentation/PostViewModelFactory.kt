package com.rtllabs.testing.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rtllabs.testing.domain.GetUseCasePost

class PostViewModelFactory(
    private val getUseCasePost: GetUseCasePost
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)){
            return PostViewModel(getUseCasePost) as T
        }
        throw IllegalArgumentException("Unknown Error")
    }
}
