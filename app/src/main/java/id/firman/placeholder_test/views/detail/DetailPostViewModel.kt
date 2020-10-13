package id.firman.placeholder_test.views.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.firman.placeholder_test.data.model.post.PostResponse
import id.firman.placeholder_test.data.repository.Repository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailPostViewModel(private val repository: Repository): ViewModel(){

    val postDetail : LiveData<PostResponse>
        get()= _postDetail
    private val _postDetail = MutableLiveData<PostResponse>()



    fun fetchPost(id:Int){
        viewModelScope.launch {
            repository.fetchLocalPost(id).collect {
                _postDetail.value = it
                repository.takenPost(id)
            }
        }
    }

    fun updateFavoritePostStatus(){
        viewModelScope.launch {
            repository.updateFavoritePostStatus(_postDetail.value!!.id, !_postDetail.value!!.favorite)
            fetchPost(_postDetail.value!!.id)
        }
    }

}