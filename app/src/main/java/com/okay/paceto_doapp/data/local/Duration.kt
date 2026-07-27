package com.okay.paceto_doapp.data.local

data class Duration(
    val name: String,
    val durationMin: Int,
)

val durations = listOf<Duration>(
    Duration("Quick Sync",15),
    Duration("Technical Reading",30),
    Duration("Deep Work",50),
    Duration("Creative Flow",90)
)
