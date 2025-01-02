package com.unbounded.realizingself

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.unbounded.realizingself.data.repository.UnboundRepository
import com.unbounded.realizingself.model.segment.segmets.timer.TimeSegmentResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class DataTransferViewModel@Inject constructor(val repository: UnboundRepository) : ViewModel() {
   /* private val selectedTimeSegment= MutableLiveData<TimeSegmentResponse?>()
    fun saveUser(user: TimeSegmentResponse?) {
     //   selectedTimeSegment.value = null
        selectedTimeSegment.value = user
    }*/

    private val _selectedTimeSegment = MutableStateFlow<TimeSegmentResponse?>(null)
    val selectedTimeSegment: StateFlow<TimeSegmentResponse?> = _selectedTimeSegment

    fun saveUser(user: TimeSegmentResponse?) {
        Log.d("DataTransferViewModel", "DataTransferViewModel: ${user}")
        _selectedTimeSegment.value = null
        _selectedTimeSegment.value = user
    }
    fun getUser(): TimeSegmentResponse? {
        Log.d("DataTransferViewModel", "DataTransferViewModel:get ${selectedTimeSegment.value}")
        return   selectedTimeSegment.value
    }
}