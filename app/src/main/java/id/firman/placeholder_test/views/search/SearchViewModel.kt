package id.firman.placeholder_test.views.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.firman.placeholder_test.data.model.post.PostResponse
import id.firman.placeholder_test.data.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchViewModel (private val repository: Repository) : ViewModel() {

    val postList: LiveData<List<PostResponse>>
        get() = _postList
    private val _postList = MutableLiveData<List<PostResponse>>()


    fun loadInitialData() {
        fetchPosts(false)
    }

    fun fetchPosts(needRemoteData: Boolean = false) {
        viewModelScope.launch {
            repository.fetchPosts(needRemoteData).collect { posts ->
                _postList.value = posts
            }
        }
    }

    fun fetchLocalPosts() {
        viewModelScope.launch {
            repository.fetchLocalPosts().collect { posts ->
                _postList.value = posts
            }
        }
    }

    fun searchPosts(query: String) {
        viewModelScope.launch {
            repository.searchLocalPosts(query).collect { post ->
                _postList.value = post
            }
        }
    }


    fun updateFavoritePostStatus(id: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.updateFavoritePostStatus(id, isFavorite)
            repository.fetchLocalPosts()
        }
    }
}