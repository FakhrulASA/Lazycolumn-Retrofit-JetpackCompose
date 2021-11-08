package com.fakhrulasa.jetpackcomposebasics.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fakhrulasa.jetpackcomposebasics.network.PostData
import com.fakhrulasa.jetpackcomposebasics.network.Retrofitlient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {
    var listData: MutableLiveData<List<PostData>> = MutableLiveData()
    init {
        CoroutineScope(Dispatchers.IO).launch {
            listData.postValue(Retrofitlient.api.getPost())
        }
    }
}