package com.unbounded.realizingself.model.segment.segmets.timer

data class UserTimeSegment(
    val audio: String?,
    val createdAt: String?,
    val description: String?,
    val duration: Int?,
    val id: Int?,
    val order: Int?,
    val presetId: Int?,
    val timeSegmentType: Int?,
    val updatedAt: String?,
    val userId: Int?,
    var segmentStatus: Boolean? = false // Set to false by default
)