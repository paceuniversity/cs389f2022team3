package com.team3.indexify.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team3.indexify.network.core.ColabApi
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

    init {
        resetView()
    }

    fun setStation(desiredStation: String) {
        _selectedStation.value = desiredStation
        refreshView()
    }

    fun resetView() {
        _selectedStation.value = "Ada"
    }

    fun refreshView() {
        viewModelScope.launch {
            _status.value = ColabApiStatus.LOADING
            try {
                Log.v("refreshView", _selectedStation.value.toString())
                _sensorData.value = ColabApi.retrofitService.getSensorData(_selectedStation.value.toString())
                Log.v("refreshView", _sensorData.value!!.deployment_id.toString())
                _status.value = ColabApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ColabApiStatus.ERROR
                Log.v("refreshView", e.toString())
            }
        }
    }
}