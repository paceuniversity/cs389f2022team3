package com.team3.indexify.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team3.indexify.network.BlueApi
import kotlinx.coroutines.launch

enum class BlueApiStatus { LOADING, ERROR, DONE }

/**
 * The [ViewModel] that is attached to the [HomeFragment].
 */
class HomeViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the request status
    val status: LiveData<String> = _status

    private val _cond = MutableLiveData<String>()
    val cond: LiveData<String> = _cond

    init {
        getSensorData()
    }

    private fun getSensorData() {

        viewModelScope.launch {
            try {
                val listResult = BlueApi.retrofitService.getSensorData()
                _status.value = listResult.timestamp
                _cond.value = listResult.sensors.Cond.toString()
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }

}