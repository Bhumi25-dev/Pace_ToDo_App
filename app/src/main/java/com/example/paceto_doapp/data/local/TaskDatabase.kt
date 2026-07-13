package com.example.paceto_doapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Task::class, FocusRecords::class, FocusTask::class],
    version = 3
)
@TypeConverters(TaskConvertors::class)
abstract class TaskDatabase: RoomDatabase() {

    abstract val taskDao: TaskDao
    abstract val focusDao: FocusDao

    //to prevent multiple saves at the same time
    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getInstance(context: Context): TaskDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }

}