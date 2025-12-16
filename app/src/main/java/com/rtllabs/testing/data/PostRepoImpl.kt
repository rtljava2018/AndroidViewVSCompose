package com.rtllabs.testing.data

import com.rtllabs.testing.domain.PostRepository
import com.rtllabs.testing.domain.Posts

class PostRepoImpl: PostRepository {
    override suspend fun getPosts(): List<Posts> {
        return RetrofitInstance.api.getPost().map {  it.toMapper()}
    }
}

private fun PostsDto.toMapper(): Posts {
    return Posts(id,userId,title,body)
}
