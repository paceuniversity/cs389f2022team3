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

    private val _dopct = MutableLiveData<String>()
    val dopct: LiveData<String> = _dopct

    private val _sal = MutableLiveData<String>()
    val sal: LiveData<String> = _sal

    private val _temp = MutableLiveData<String>()
    val temp: LiveData<String> = _temp

    private val _turb = MutableLiveData<String>()
    val turb: LiveData<String> = _turb

    private val _pH = MutableLiveData<String>()
    val pH: LiveData<String> = _pH

    init {
        getSensorData()
    }

    private fun getSensorData() {

        viewModelScope.launch {
            try {
                val listResult = BlueApi.retrofitService.getSensorData()
                _cond.value = listResult.sensors.Cond.toString()
                _dopct.value = listResult.sensors.DOpct.toString()
                _sal.value = listResult.sensors.Sal.toString()
                _temp.value = listResult.sensors.Temp.toString()
                _turb.value = listResult.sensors.Turb.toString()
                _pH.value = listResult.sensors.pH.toString()

            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }

}