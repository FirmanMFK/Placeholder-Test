package id.firman.placeholder_test.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class SharedViewModel:ViewModel() {

    val clearPosts : LiveData<Boolean>
        get() = _clearPosts
    private val _clearPosts = MutableLiveData(false)

    val needRemoteData : LiveData<Boolean>
        get() = _needRemoteData
    private val _needRemoteData = MutableLiveData(true)


    fun clearPosts(clearStatus:Boolean){
        _clearPosts.value = clearStatus
    }

    fun needRemoteData(remoteStatus:Boolean){
        _needRemoteData.value = remoteStatus
    }

}