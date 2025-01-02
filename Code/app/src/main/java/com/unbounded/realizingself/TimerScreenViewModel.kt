package com.unbounded.realizingself

import android.annotation.SuppressLint
import android.content.Context
import android.icu.util.TimeZone
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unbounded.realizingself.data.remote.retrofitapi.Response
import com.unbounded.realizingself.data.repository.UnboundRepository
import com.unbounded.realizingself.model.segment.segmets.timer.TimeSegmentResponse
import com.unbounded.realizingself.model.segment.segmets.timer.UserTimeSegment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject
import kotlin.math.abs


@HiltViewModel
class TimerScreenViewModel @Inject constructor(private val repository: UnboundRepository) : ViewModel()
{
    val segmentDataApiCall = mutableStateOf<TimeSegmentResponse?>(value = null)


    private val _timeRemaining = MutableLiveData(0)
    val timeRemaining: LiveData<Int> get() = _timeRemaining


    var isTimerPaused = false

    private var timerJob: Job? = null
    var elapsedTime = mutableStateOf(0)
    var actualMeditationTime = mutableStateOf(0)
    var totalDuration = mutableStateOf(1) // Initialized with 1 to avoid division by zero
    var isRunning = mutableStateOf(false)


    var segments = mutableStateListOf<Segment>()
    /*  val _segments: LiveData<List<Segment>> get() = segments
      private val segments = MutableLiveData<List<Segment>>(emptyList())
  */

    lateinit var mcontext: Context
    var currentIndex by mutableStateOf(0)
    val isReverseTimer: LiveData<Boolean> get() = _isReverseTimer
    val _isReverseTimer = MutableLiveData(false)
    var timerList = mutableListOf<Int>()
    val segmentList: List<Float> =
        if (!segmentDataApiCall.value?.userTimeSegments.isNullOrEmpty()){
            segmentDataApiCall.value?.userTimeSegments!!.map { segment ->
                segment.duration!! / 60f // Convert seconds to minutes
            }
        }else{
            emptyList()
        }


    fun initContext(context: Context) {
        mcontext = context
    }
    fun uploadData(data: MutableList<UserTimeSegment>) {
        // Create a TimeSegmentResponse (or modify as per your logic)
        Log.d("uploadData", "uploadData: ${data.size}")
        val response = TimeSegmentResponse(userTimeSegments = data)

        // Update the state
        segmentDataApiCall.value = response
    }


    var userTimeSegments: List<UserTimeSegment> = emptyList()
    private val _isFloatingTextVisible = MutableLiveData(true)
    val isFloatingTextVisible: LiveData<Boolean> = _isFloatingTextVisible

    fun setIsFloatingTextVisible(visible: Boolean) {
        _isFloatingTextVisible.value = visible
    }


    private val _userTimeSegments = MutableLiveData<List<UserTimeSegment>?>(null)
    val userTimeSegmentsUpdated: LiveData<List<UserTimeSegment>?> get() = _userTimeSegments


    private val _animationProgress = MutableLiveData(0f)
    val animationProgress: LiveData<Float> get() = _animationProgress

    private val _isProgressStarted = MutableLiveData(false)
    val isProgressStarted: LiveData<Boolean> get() = _isProgressStarted

    private val _isProgressPaused = MutableLiveData(false)
    val isProgressPaused: LiveData<Boolean> get() = _isProgressPaused


    val _isProgressCompleted = MutableLiveData(false)
    val isProgressCompleted: LiveData<Boolean> get() = _isProgressCompleted




    private val _isSuccess = MutableLiveData(false)
    val isSuccess: LiveData<Boolean> get() = _isSuccess

    private val _segmentinfoLivedata = MutableLiveData<Response<TimeSegmentResponse>>()
    val segmentInfoLivedata: LiveData<Response<TimeSegmentResponse>> get() = _segmentinfoLivedata

    private var isSegmentComplete = false
    private var isResetCall = false


    private val _currentSegmentName = MutableLiveData<String>()
    val currentSegmentName: LiveData<String> get() = _currentSegmentName


    private val _nextSegmentName = MutableLiveData<String>()
    val nextSegmentName: LiveData<String> get() = _nextSegmentName


    val _isFreshView = MutableLiveData(true)
    val isFreshView: LiveData<Boolean> get() = _isFreshView
    @SuppressLint("SuspiciousIndentation")
    fun startTimer() {
        if (!isRunning.value) {

            isRunning.value = true
            Log.d("djsds", "startTimer:${segments.size} ")
            timerJob = viewModelScope.launch {
                while (elapsedTime.value < totalDuration.value && isActive) {
                    delay(1000) // Delay by 1 second
                    elapsedTime.value++

                    actualMeditationTime.value++
                    updateTimeRemainingForCurrentSegment()
                    checkSegmentCompletion()

                    if (isSegmentComplete) {
                        currentIndex++
                        if (currentIndex < segments.size) {
                            isSegmentComplete = false
                        } else {
                            isRunning.value = false
                            break
                        }
                    }



                }
                isRunning.value = false
            }
        }
    }

    fun nextSegment() {
        pauseTimer()
        val currentDuration = segments.take(currentIndex).sumOf { it.duration }
        val nextSegmentStart = segments.getOrNull(currentIndex)?.duration ?: totalDuration.value
        elapsedTime.value = currentDuration + nextSegmentStart


        if ( elapsedTime.value ===totalDuration.value){
            _timeRemaining.value = 0
            _isProgressCompleted.value= true
            _isProgressStarted.value = false
            _isFreshView.value =true
        }
        startTimer()
    }

    private fun checkSegmentCompletion() {
        var accumulatedTime = 0




        for (i in segments.indices) {
            accumulatedTime += segments[i].duration
            _currentSegmentName.value = segments[i].description
            if (elapsedTime.value < accumulatedTime) {
                currentIndex = i

                if (i + 1 < segments.size) {
                    _nextSegmentName.value = "Begin ${segments[i + 1].description}"
                } else {
                    _nextSegmentName.value = "Finish"
                }
                break
            }
            if (!segments[i].isComplete) {
                segments[i].isComplete = true
                playAudioForSegment(segments[i].audio)

            }


        }
        isSegmentComplete = elapsedTime.value >= accumulatedTime
    }

    private fun updateTimeRemainingForCurrentSegment() {
        if (currentIndex < segments.size) {
            val currentSegmentElapsed = elapsedTime.value - segments.take(currentIndex).sumOf { it.duration }
            val remainingTime = segments[currentIndex].duration - currentSegmentElapsed

            if (isReverseTimer.value == true) {
                // Run in ascending order
                _timeRemaining.value = if (currentSegmentElapsed < 0) 0 else currentSegmentElapsed
            } else {
                // Run in descending order
                _timeRemaining.value = if (remainingTime < 0) 0 else remainingTime
            }
            if ( elapsedTime.value ===totalDuration.value){
                _timeRemaining.value = 0
                _isProgressCompleted.value= true
                _isProgressStarted.value = false
                _isFreshView.value =true
            }
        }else {
            _timeRemaining.value = 0
            _isProgressCompleted.value =true
            _isProgressStarted.value = false
        }
        if ( elapsedTime.value ===totalDuration.value){
            _timeRemaining.value = 0
            _isProgressCompleted.value= true
            _isProgressStarted.value = false
            _isFreshView.value =true



        }
    }

    fun pauseTimer() {
        isRunning.value = false
        timerJob?.cancel()
    }

    fun resetTimer() {
        pauseTimer()
        elapsedTime.value = 0
        segments.forEach { it.isComplete = false }
    }


    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var dataMessage = mutableStateOf("")






    fun updateCompleteSegment(isUpdate: Boolean) {
        isSegmentComplete = isUpdate
    }

    fun resetUpdate(isReset: Boolean) {
        isResetCall = isReset
    }

    fun playAudioForSegment(audioFileName: String) {

        // Replace all hyphens with underscores and convert the entire string to lowercase
        val formattedFileName = audioFileName.replace('-', '_').lowercase()

        // Try to find the resource ID for the formatted file name
        val resId = mcontext.resources.getIdentifier(formattedFileName, "raw", mcontext.packageName)
        if (resId == 0) {
            Log.e("AudioPlayer", "File not found: $formattedFileName")
            return
        }
        val mediaPlayer = MediaPlayer.create(mcontext, resId)
        mediaPlayer.setOnCompletionListener {
            mediaPlayer.release() // Release the MediaPlayer after audio playback completes
        }
        mediaPlayer.start() // Start playing audio
    }


    fun setProgressPaused() {
        _isProgressPaused.value = true
        isTimerPaused = true
    }

    fun setIsProgressStarted() {
        _isProgressCompleted.value = false
        _isProgressStarted.value = true
        isTimerPaused = false
        _isProgressPaused.value = false
    }

    private fun customTween(durationInMillis: Int): AnimationSpec<Float> {
        return tween(
            durationMillis = durationInMillis,
            easing = LinearEasing
        )
    }

    fun updateUserTimeSegments(homeSegmentDataModel: TimeSegmentResponse) {
        userTimeSegments = homeSegmentDataModel.userTimeSegments!!
        _userTimeSegments.value = userTimeSegments
    }

    private    fun updateCurrentSegmentName() {
        if (currentIndex >= 0 && currentIndex < userTimeSegments.size) {
            var segmentName = userTimeSegments[currentIndex].description
            val updatedSegmentName = segmentName?.uppercase(Locale.ROOT)
            _currentSegmentName.value = updatedSegmentName!!
        } else {
            _currentSegmentName.value = ""
        }
    }



    fun resetAll() {
        viewModelScope.launch {
            timerJob?.cancel()  // Cancel the job as part of reset
            // Reset other states
            isResetCall = false
            timerList.clear()
            _userTimeSegments.value = null
            _isFreshView.value = true
            _isProgressStarted.value = false
            _isProgressPaused.value = false
            _isProgressCompleted.value = false
            _animationProgress.value = 0f
            _timeRemaining.value = 0
            currentIndex = 0
            actualMeditationTime.value = 0
            isTimerPaused = false
            isSegmentComplete = false
            userTimeSegments = emptyList()
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()  // Ensure job is cancelled when ViewModel is cleared
    }




}

fun getUTCTimeZoneDifference(timeZone: TimeZone = TimeZone.getDefault()): String {
    val hours = timeZone.rawOffset / 3600000 // Get hours from raw offset
    val minutes = abs(timeZone.rawOffset / 60000 % 60) // Get minutes from raw offset
    return String.format("UTC%+03d:%02d", hours, minutes) // Format the string
}
data class Segment(val duration: Int,
                   val audio: String,
                   var isComplete: Boolean = false,
                   val createdAt: String,
                   val description: String,
                   val id: Int,
                   val order: Int,
                   val presetId: Int,
                   val timeSegmentType: Int,
                   val updatedAt: String,
                   val userId: Int,
                   var segmentStatus: Boolean?



)


