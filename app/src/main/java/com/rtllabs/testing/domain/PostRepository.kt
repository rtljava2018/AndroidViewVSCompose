package com.rtllabs.testing.domain

interface PostRepository {
    suspend fun getPosts(): List<Posts>
}