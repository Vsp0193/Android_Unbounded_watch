package com.unbounded.realizingself.model.segment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MeditationRoutineSegmentResponse(
    @SerialName("userPresets") val userPresets: List<UserPreset>
)