package com.rtllabs.testing.data

import retrofit2.http.GET

interface ApiService {

    @GET("posts")
    suspend fun getPost():List<PostsDto>
}
