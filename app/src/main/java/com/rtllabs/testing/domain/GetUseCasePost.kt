package com.rtllabs.testing.domain

class GetUseCasePost(val repository: PostRepository) {
    suspend operator fun invoke(): List<Posts> {
        return repository.getPosts()
    }
}