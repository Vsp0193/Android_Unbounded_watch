package com.unbounded.realizingself

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peak.unbounded.features.data.local.DataViewModel
import com.unbounded.realizingself.data.remote.retrofitapi.Response
import com.unbounded.realizingself.data.repository.UnboundRepository
import com.unbounded.realizingself.model.segment.MeditationRoutineSegmentResponse
import com.unbounded.realizingself.model.segment.segmets.timer.TimeSegmentResponse
import com.unbounded.realizingself.utils.ConvertJsonToMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class MeditationRoutineViewModel @Inject constructor(val repository: UnboundRepository) : ViewModel() {

    val responseData = mutableStateOf<MeditationRoutineSegmentResponse?>(value = null)
    val timeSegmentResponseData = mutableStateOf<TimeSegmentResponse?>(value = null)
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var dataMessage = mutableStateOf("")

    fun getMeditationRoutineList(
        token: String,
        userId: Int,
    ) {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                val response =
                    repository.getMeditationRoutineList(token,userId)
                when (response) {
                    is Response.Error -> {
                        isLoading.value = false
                        loadError.value = response.errorMessage
                    }

                    is Response.Loading -> {
                        isLoading.value = true
                        dataMessage.value = ""
                    }

                    is Response.Success -> {
                        isLoading.value = false
                        responseData.value = response.data
                        Log.d("addMeditationNote", "addMeditationNote: ${response.data}")
                    }
                    else -> {}
                }
            } catch (e: Exception) {
                isLoading.value = false
                loadError.value = "Ui Error"
            }

        }
    }

    fun activeMeditationRoutineList(
        dataStoreViewModel: DataViewModel,
        token: String,
        presetId: Int,
    ) {
        val request_data = JSONObject()

        request_data.put("presetId", presetId)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response =
                        repository.activeMeditationRoutineList(token,ConvertJsonToMap().jsonToMapKotlin(json = request_data) )
                    when (response) {
                        is Response.Error -> {
                            isLoading.value = false
                            loadError.value = response.errorMessage
                        }

                        is Response.Loading -> {
                            isLoading.value = true
                            dataMessage.value = ""
                        }

                        is Response.Success -> {
                            isLoading.value = false
                            getMeditationSegmentList(dataStoreViewModel,dataStoreViewModel.getToken()!!,dataStoreViewModel.getUserId()!!)
                            Log.d("addMeditationNote", "addMeditationNote: ${response.data}")
                        }
                        else -> {}
                    }
                } catch (e: Exception) {
                    isLoading.value = false
                    loadError.value = "Ui Error"
                }
            }
        }
    }
    fun getMeditationSegmentList(
        dataStoreViewModel: DataViewModel,
        token: String,
        userId: Int,
    ) {
        Log.d("delete", "deleteMeditat ")
        viewModelScope.launch (Dispatchers.IO) {

            try {
                val response =
                    repository.getMeditationSegmentList(token,userId)
                when (response) {
                    is Response.Error -> {
                        isLoading.value = false
                        loadError.value = response.errorMessage
                    }

                    is Response.Loading -> {
                        isLoading.value = true
                        dataMessage.value = ""
                    }

                    is Response.Success -> {
                        isLoading.value = false
                        timeSegmentResponseData.value = response.data
                        Log.d("dwhdvwdjqdjqjdqjd", "deleteMeditat Response.Success")
                        Log.d("addMeditationNote", "addMeditationNote: ${response.data}")
                    }
                    else -> {}
                }
            } catch (e: Exception) {
                isLoading.value = false
                loadError.value = "Ui Error"
            }

        }
    }
 /*   fun getMeditationSegmentList(
        dataStoreViewModel: DataViewModel,
        token: String,
        userId: Int,
    ) {
        Log.d("delete", "deleteMeditat ")
        viewModelScope.launch (Dispatchers.IO) {

            try {
                val response =
                    repository.getMeditationSegmentList(token,userId)
                when (response) {
                    is Response.Error -> {
                        isLoading.value = false
                        loadError.value = response.errorMessage
                    }

                    is Response.Loading -> {
                        isLoading.value = true
                        dataMessage.value = ""
                    }

                    is Response.Success -> {
                        isLoading.value = false
                        timeSegmentResponseData.value = response.data
                        Log.d("dwhdvwdjqdjqjdqjd", "deleteMeditat Response.Success")
                        Log.d("addMeditationNote", "addMeditationNote: ${response.data}")
                    }
                    else -> {}
                }
            } catch (e: Exception) {
                isLoading.value = false
                loadError.value = "Ui Error"
            }

        }
    }*/
    /*fun reOrderSegment(
        dataStoreViewModel: DataViewModel,
        token: String,
        request : ReOrderRequest,
    ) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                val response =
                    repository.reOrderSegment(token,request)
                when (response) {
                    is Response.Error -> {
                        isLoading.value = false
                        loadError.value = response.errorMessage
                    }

                    is Response.Loading -> {
                        isLoading.value = true
                        dataMessage.value = ""
                    }

                    is Response.Success -> {
                        isLoading.value = false
                        Log.d("addMeditationNote", "addMeditationNote: ${response.data}")

                        getMeditationSegmentList(
                            dataStoreViewModel,
                            token,
                            userId = dataStoreViewModel.getUserId()!!,
                        )
                    }
                    else -> {}
                }
            } catch (e: Exception) {
                isLoading.value = false
                loadError.value = "Ui Error"
            }

        }
    }


    fun deleteMeditationSegmentList(
        dataStoreViewModel: DataViewModel,
        token: String,
        segmentId: Int,
    ) {

        Log.d("delete", "deleteMeditat ")
        val request_data = JSONObject()

        request_data.put("segmentId", segmentId)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response =
                        repository.deleteMeditationSegmentList(token,ConvertJsonToMap().jsonToMapKotlin(json = request_data) )
                    when (response) {
                        is Response.Error -> {
                            isLoading.value = false
                            loadError.value = response.errorMessage
                        }

                        is Response.Loading -> {
                            isLoading.value = true
                            dataMessage.value = ""
                        }

                        is Response.Success -> {
                            isLoading.value = false
                            getMeditationSegmentList(
                                dataStoreViewModel,
                                token,
                                userId = dataStoreViewModel.getUserId()!!,
                            )
                            Log.d("addMeditationNote", "addMeditationNote: ${response.data}")
                        }
                        else -> {}
                    }
                } catch (e: Exception) {
                    isLoading.value = false
                    loadError.value = "Ui Error"
                }
            }
        }
    }

    fun deleteMeditationRoutineList(
        dataStoreViewModel: DataViewModel,
        token: String,
        presetId: Int,
    ) {
        val request_data = JSONObject()

        request_data.put("presetId", presetId)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response =
                        repository.deleteMeditationRoutineList(token,ConvertJsonToMap().jsonToMapKotlin(json = request_data) )
                    when (response) {
                        is Response.Error -> {
                            isLoading.value = false
                            loadError.value = response.errorMessage
                        }

                        is Response.Loading -> {
                            isLoading.value = true
                            dataMessage.value = ""
                        }

                        is Response.Success -> {
                            isLoading.value = false
                            getMeditationRoutineList(dataStoreViewModel,dataStoreViewModel.getToken()!!,dataStoreViewModel.getUserId()!!)
                            Log.d("addMeditationNote", "addMeditationNote: ${response.data}")
                        }
                        else -> {}
                    }
                } catch (e: Exception) {
                    isLoading.value = false
                    loadError.value = "Ui Error"
                }
            }
        }
    }
    fun activeMeditationRoutineList(
        dataStoreViewModel: DataViewModel,
        token: String,
        presetId: Int,
    ) {
        val request_data = JSONObject()

        request_data.put("presetId", presetId)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response =
                        repository.activeMeditationRoutineList(token,ConvertJsonToMap().jsonToMapKotlin(json = request_data) )
                    when (response) {
                        is Response.Error -> {
                            isLoading.value = false
                            loadError.value = response.errorMessage
                        }

                        is Response.Loading -> {
                            isLoading.value = true
                            dataMessage.value = ""
                        }

                        is Response.Success -> {
                            isLoading.value = false
                            getMeditationRoutineList(dataStoreViewModel,dataStoreViewModel.getToken()!!,dataStoreViewModel.getUserId()!!)
                            Log.d("addMeditationNote", "addMeditationNote: ${response.data}")
                        }
                        else -> {}
                    }
                } catch (e: Exception) {
                    isLoading.value = false
                    loadError.value = "Ui Error"
                }
            }
        }
    }
    fun addTimeSegment(
        dataStoreViewModel: DataViewModel,
        token: String,
        description: String,
        audio: String,
        userId: Int,
        duration: Int,
    ) {
        val request_data = JSONObject()

        request_data.put("audio", audio)
        request_data.put("duration", duration)
        request_data.put("userId", userId)
        request_data.put("description", description)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response =
                        repository.addTimeSegment(token,ConvertJsonToMap().jsonToMapKotlin(json = request_data) )
                    when (response) {
                        is Response.Error -> {
                            isLoading.value = false
                            loadError.value = response.errorMessage
                        }

                        is Response.Loading -> {
                            isLoading.value = true
                            dataMessage.value = ""
                        }

                        is Response.Success -> {
                            isLoading.value = false
                            dataMessage.value = response.data.message?:""
                            getMeditationSegmentList(dataStoreViewModel,dataStoreViewModel.getToken()!!,dataStoreViewModel.getUserId()!!)
                            Log.d("addMeditationNote", "addMeditationNote: ${response.data}")
                        }
                        else -> {}
                    }
                } catch (e: Exception) {
                    isLoading.value = false
                    loadError.value = "Ui Error"
                }
            }
        }
    }
    fun editTimeSegment(
        dataStoreViewModel: DataViewModel,
        token: String,
        description: String,
        audio: String,
        duration: Int,
        id: Int,
    ) {
        val request_data = JSONObject()

        request_data.put("audio", audio)
        request_data.put("duration", duration)
        request_data.put("id", id)
        request_data.put("description", description)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response =
                        repository.editTimeSegment(token,ConvertJsonToMap().jsonToMapKotlin(json = request_data) )
                    when (response) {
                        is Response.Error -> {
                            isLoading.value = false
                            loadError.value = response.errorMessage
                        }

                        is Response.Loading -> {
                            isLoading.value = true
                            dataMessage.value = ""
                        }

                        is Response.Success -> {
                            isLoading.value = false
                            dataMessage.value = response.data.message?:""
                            getMeditationSegmentList(dataStoreViewModel,dataStoreViewModel.getToken()!!,dataStoreViewModel.getUserId()!!)
                            Log.d("addMeditationNote", "addMeditationNote: ${response.data}")
                        }
                        else -> {}
                    }
                } catch (e: Exception) {
                    isLoading.value = false
                    loadError.value = "Ui Error"
                }
            }
        }
    }
    @SuppressLint("SuspiciousIndentation")
    fun addMeditationRoutineList(
        dataStoreViewModel: DataViewModel,
        token: String,
        userTimeSegments: List<UserTimeSegment>?,
    ) {
        val requestData = JSONObject()
        val swwa = SaveSegmentData("New Routine",userTimeSegments)
        try {
            // Add description to the JSONObject
            requestData.put("description", "New Routine")

            // Convert the list of UserTimeSegment objects to JSONArray
            val jsonArray = JSONArray()
            for (userTimeSegment in userTimeSegments!!) {
                val timeSegmentJson = JSONObject()
                timeSegmentJson.put("audio", userTimeSegment.audio)
                timeSegmentJson.put("createdAt", userTimeSegment.createdAt)
                timeSegmentJson.put("description", userTimeSegment.description)
                timeSegmentJson.put("duration", userTimeSegment.duration)
                timeSegmentJson.put("id", userTimeSegment.id)
                timeSegmentJson.put("order", userTimeSegment.order)
                timeSegmentJson.put("presetId", userTimeSegment.presetId)
                timeSegmentJson.put("timeSegmentType", userTimeSegment.timeSegmentType)
                timeSegmentJson.put("updatedAt", userTimeSegment.updatedAt)
                timeSegmentJson.put("userId", userTimeSegment.userId)
                jsonArray.put(timeSegmentJson)
            }

            // Add the JSONArray to the JSONObject
            requestData.put("timeSegments", jsonArray)

            // Print the JSONObject
            println(requestData.toString())
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        viewModelScope.launch(Dispatchers.IO) {

            try {
                Log.d( "jsonofarray","${ requestData }")
                Log.d( "jsonofarray","${ConvertJsonToMap().jsonToMapKotlin(json = requestData) }")
                val response =
                    repository.addMeditationRoutineListWithRetry(token, swwa )

                when (response) {
                    is Response.Error -> {
                        //   Log.d("addMeditationNote", "errorMessage: ${response.errorMessage}")
                        getMeditationSegmentList(dataStoreViewModel,dataStoreViewModel.getToken()!!,dataStoreViewModel.getUserId()!!)

                        isLoading.value = false
                        loadError.value = response.errorMessage
                    }

                    is Response.Loading -> {

                        isLoading.value = true
                        dataMessage.value = ""
                    }

                    is Response.Success -> {
                        isLoading.value = false
                        getMeditationRoutineList(dataStoreViewModel,dataStoreViewModel.getToken()!!,dataStoreViewModel.getUserId()!!)

                    }
                    else -> {}
                }

            } catch (e: Exception) {
                Log.d("addMeditationNote", "getMeditationerrorException: ${e.message}")
                Log.d("addMeditationNote", "getMeditationerrorException: ${e.cause}")
                isLoading.value = false
                loadError.value = "Ui Error"

            }
        }
    }

    fun updateRoutineName(
        dataStoreViewModel: DataViewModel,
        token: String,
        userTimeSegments: RoutineNameRequest,
    ) {


        viewModelScope.launch(Dispatchers.IO) {

            try {
                val response =
                    repository.updateRoutineName(token, userTimeSegments )

                when (response) {
                    is Response.Error -> {
                        isLoading.value = false
                        loadError.value = response.errorMessage
                    }

                    is Response.Loading -> {

                        isLoading.value = true
                        dataMessage.value = ""
                    }

                    is Response.Success -> {
                        isLoading.value = false
                        getMeditationRoutineList(dataStoreViewModel,dataStoreViewModel.getToken()!!,dataStoreViewModel.getUserId()!!)

                    }
                    else -> {}
                }

            } catch (e: Exception) {
                Log.d("addMeditationNote", "getMeditationerrorException: ${e.message}")
                Log.d("addMeditationNote", "getMeditationerrorException: ${e.cause}")
                isLoading.value = false
                loadError.value = "Ui Error"

            }
        }
    }*/

}






fun replaceHyphenWithUnderscore(input: String): String {
    return input.replace('_', '-')
}

fun replaceUnderscoreWithHyphen(input: String): String {
    return input.split('_').joinToString("-") { it.capitalize() }
}

fun String.capitalize(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase() else it.toString()
    }
}
fun replaceUnderscoreWithParentheses(input: String): String {
    // Use regular expression to find the pattern fire_<number>
    val regex = Regex("(\\w+)_(\\d+)")
    return input.replace(regex) {
        // Replace with the desired format fire(number)
        "${it.groupValues[1]}(${it.groupValues[2]})"
    }
}

