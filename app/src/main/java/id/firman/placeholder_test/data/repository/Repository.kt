package id.firman.placeholder_test.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import id.firman.placeholder_test.data.local.dao.PostDao
import id.firman.placeholder_test.data.model.post.PostResponse
import id.firman.placeholder_test.data.remote.ApiService
import id.firman.placeholder_test.utils.haveConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class Repository(
    private val remoteData: ApiService,
    private val localData: PostDao,
    private val context: Context
) {


    fun fetchPosts(needRemoteData: Boolean = false) = flow<List<PostResponse>> {
        try {
            if (context.haveConnection()) {
                fetchLocalPosts().collect { posts ->
                    if (!needRemoteData) {
                        emit(posts)
                    } else {
                        val response = remoteData.fetchPosts().await()
                        localData.insertPosts(response)
                        fetchLocalPosts().collect {
                            emit(it)
                        }
                    }
                }
            } else {
                fetchLocalPosts().collect {
                    emit(it)
                }
            }
        } catch (t: Throwable) {
            fetchLocalPosts().collect {
                emit(it)
            }
        }
    }.flowOn(Dispatchers.IO)


    fun fetchLocalPosts() = flow {
        emit(localData.fetchPosts())
    }.flowOn(Dispatchers.IO)


    suspend fun updateFavoritePostStatus(id: Int, isFavorite: Boolean) {
        withContext(Dispatchers.IO) {
            localData.updateFavoritePost(id, isFavorite)
        }
    }

    fun fetchLocalPost(id: Int) = flow {
        emit(localData.fetchPost(id))
    }.flowOn(Dispatchers.IO)

    suspend fun deletePost(id: Int) {
        withContext(Dispatchers.IO) {
            localData.deletePost(id)
        }
    }

//    fun searchLocalPosts(query: String) = flow {
//        emit(localData.getSearchPosts(query))
//    }.flowOn(Dispatchers.IO)

    fun searchLocalPosts(query: String) = flow<List<PostResponse>> {
        emit(localData.getSearchPosts("%$query%"))
    }.flowOn(Dispatchers.IO)

    fun deletePosts() = flow<List<PostResponse>> {
        localData.deletePosts()
        emit(emptyList())
    }.flowOn(Dispatchers.IO)

    suspend fun takenPost(id: Int) {
        withContext(Dispatchers.IO) {
            localData.takenPost(id)
        }
    }


}