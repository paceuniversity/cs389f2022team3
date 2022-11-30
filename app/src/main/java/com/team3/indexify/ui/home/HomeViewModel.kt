package com.team3.indexify.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team3.indexify.network.models.SensorDataModel
import kotlinx.coroutines.launch

enum class ColabApiStatus { LOADING, ERROR, DONE }
/**
 * The [ViewModel] that is attached to the [HomeFragment].
 */
class HomeViewModel : ViewModel() {

    private val _status = MutableLiveData<ColabApiStatus>()
    val status: LiveData<ColabApiStatus> = _status

    private val _sensorData = MutableLiveData<SensorDataModel>()
    val sensorData: LiveData<SensorDataModel> = _sensorData

    private val _selectedStation = MutableLiveData<String>()
    val selectedStation: LiveData<String> = _selectedStation

}