package com.unbounded.realizingself.model.segment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserPreset(
    @SerialName("createdAt") val createdAt: String,
    @SerialName("description") val description: String,
    @SerialName("id") val id: Int,
    @SerialName("isActive") val isActive: Boolean,
    @SerialName("presetSegmentDescription") val presetSegmentDescription: String,
    @SerialName("updatedAt") val updatedAt: String,
    @SerialName("userId") val userId: Int
)