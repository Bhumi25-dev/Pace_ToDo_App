package com.example.paceto_doapp.data.local

import androidx.room.TypeConverter
import com.example.paceto_doapp.data.local.Category
import com.example.paceto_doapp.data.local.Priority
import java.time.LocalDateTime

class TaskConvertors {

    // LocalDateTime
    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime?): String? {
        return dateTime?.toString()
    }

    @TypeConverter
    fun toLocalDateTime(dateTimeString: String?): LocalDateTime? {
        return dateTimeString?.let { LocalDateTime.parse(it) }
    }

    // Category enum
    @TypeConverter
    fun fromCategory(category: Category?): String? {
        return category?.name
    }

    @TypeConverter
    fun toCategory(categoryString: String?): Category? {
        return categoryString?.let { Category.valueOf(it) }
    }

    // Priority enum
    @TypeConverter
    fun fromPriority(priority: Priority?): String? {
        return priority?.name
    }

    @TypeConverter
    fun toPriority(priorityString: String?): Priority? {
        return priorityString?.let { Priority.valueOf(it) }
    }

    // List<String> tags
    @TypeConverter
    fun fromTagsList(tags: List<String>?): String? {
        return tags?.joinToString(separator = ",")
    }

    @TypeConverter
    fun toTagsList(tagsString: String?): List<String>? {
        return if (tagsString.isNullOrEmpty()) emptyList()
        else tagsString.split(",").map { it.trim() }
    }
}