package com.artem.mi.spacexautenticom.ui.launchpad

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artem.mi.spacexautenticom.repository.LaunchpadRepo
import kotlinx.coroutines.launch

class LaunchpadViewModel : ViewModel() {

    private val _launchpadsData: MutableLiveData<LaunchpadViewModelState> = MutableLiveData()
    val launchpadsData: LiveData<LaunchpadViewModelState> = _launchpadsData

    init {
        viewModelScope.launch {
            runCatching {
                LaunchpadRepo.fetchLaunchpads()
            }.onSuccess { launchpads ->
                _launchpadsData.postValue(LaunchpadViewModelState(launchpads = launchpads))
            }.onFailure {
                _launchpadsData.postValue(LaunchpadViewModelState(error = it.localizedMessage))
            }
        }
    }
}

