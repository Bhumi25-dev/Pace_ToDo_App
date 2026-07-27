package com.okay.paceto_doapp.data.local

enum class Category {
    FOCUS,
    HEALTH,
    CONNECTIONS,
    ADMIN,
}

val categoryTags = mapOf(
    Category.FOCUS to listOf("Learning", "Professional", "University", "DSA"),
    Category.HEALTH to listOf("Workout", "Mental", "Diet", "Medical"),
    Category.CONNECTIONS to listOf("Family","Friends","Mentoring","Events"),
    Category.ADMIN to listOf("Household", "Finance","Errands")
)