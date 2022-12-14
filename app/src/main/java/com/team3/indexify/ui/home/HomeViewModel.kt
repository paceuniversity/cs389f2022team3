package com.team3.indexify.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team3.indexify.network.core.ColabApi
import com.team3.indexify.network.models.SensorDataModel
import com.team3.indexify.network.models.SensorModel
import kotlinx.coroutines.launch

enum class ColabApiStatus { LOADING, ERROR, DONE }
/**
 * The [ViewModel] that is attached to the [HomeFragment].
 */
class HomeViewModel : ViewModel() {

    private val _status = MutableLiveData<ColabApiStatus>()
    val status: LiveData<ColabApiStatus> = _status

    private val _sensorDataModel = MutableLiveData<SensorDataModel>()
    val sensorDataModel: LiveData<SensorDataModel> = _sensorDataModel

    private val _sensorModel = MutableLiveData<SensorModel>()
    val sensorModel: LiveData<SensorModel> = _sensorModel

    private val _selectedStation = MutableLiveData<String>()
    val selectedStation: LiveData<String> = _selectedStation

    init {
        resetView()
    }

    fun setStation(desiredStation: String) {
        _selectedStation.value = desiredStation
    }

    fun resetView() {
        _selectedStation.value = "Ada"
        refreshView()
    }

    fun refreshView() {
        viewModelScope.launch {
            _status.value = ColabApiStatus.LOADING
            try {
                val resp = ColabApi.retrofitService.getSensorData(_selectedStation.value.toString())
                _sensorDataModel.value = resp
                _sensorModel.value = resp.sensors

                _status.value = ColabApiStatus.DONE
            } catch (e: Exception) {

                _status.value = ColabApiStatus.ERROR
            }
        }
    }
}