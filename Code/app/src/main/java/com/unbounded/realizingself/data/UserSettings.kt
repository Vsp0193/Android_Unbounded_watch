package com.unbounded.realizingself.data

data class UserSettings(
    val connectToAppleHealthKit: Boolean,
    val emailNotifications: Boolean,
    val isPrivate: Boolean,
    val pushNotifications: Boolean,
    val shareMilestones: Boolean,
    val shareStats: Boolean,
    val timelapseTimer: Boolean
)