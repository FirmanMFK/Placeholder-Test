package id.firman.placeholder_test.data.remote

import id.firman.placeholder_test.data.model.post.PostResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/posts")
    fun fetchPosts(
    ): Deferred<List<PostResponse>>

}