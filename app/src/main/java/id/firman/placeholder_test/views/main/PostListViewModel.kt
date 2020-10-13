package id.firman.placeholder_test.views.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.firman.placeholder_test.data.model.post.PostResponse
import id.firman.placeholder_test.data.repository.Repository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PostListViewModel(private val repository: Repository) : ViewModel() {

    val postList : LiveData<List<PostResponse>>
        get()= _postList
    private val _postList = MutableLiveData<List<PostResponse>>()


    fun loadInitialData(){
        fetchPosts(false)
    }

    fun fetchPosts(needRemoteData: Boolean = false){
        viewModelScope.launch {
            repository.fetchPosts(needRemoteData).collect { posts ->
                _postList.value = posts
            }
        }
    }

    fun fetchLocalPosts(){
        viewModelScope.launch {
            repository.fetchLocalPosts().collect { posts ->
                _postList.value = posts
            }
        }
    }

    fun deleteItem(id: Int){
        viewModelScope.launch {
            repository.deletePost(id)
        }
    }

    fun deletePosts(){
        viewModelScope.launch {
            repository.deletePosts().collect {
                _postList.value = it
            }
        }
    }

    fun updateFavoritePostStatus(id:Int, isFavorite:Boolean){
        viewModelScope.launch {
            repository.updateFavoritePostStatus(id, isFavorite)
            repository.fetchLocalPosts()
        }
    }
}