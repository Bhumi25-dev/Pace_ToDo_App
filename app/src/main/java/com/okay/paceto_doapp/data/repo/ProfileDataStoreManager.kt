package com.okay.paceto_doapp.data.repo

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.okay.paceto_doapp.data.local.Profile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("profile_data")

class ProfileDataStoreManager(context: Context)
{
    private val dataStore = context.dataStore

    companion object{
        val NAME = stringPreferencesKey("name")
        val AGE = intPreferencesKey("age")
        val PROFESSION = stringPreferencesKey("profession")
        val EMAIL = stringPreferencesKey("email")
    }

    val profileDataFlow: Flow<Profile> = dataStore.data.map { preferences ->
        Profile(
            name = preferences[NAME]?: "ALex",
            age = preferences[AGE]?: 21,
            profession = preferences[PROFESSION]?: "Android Developer",
            email = preferences[EMAIL]?: "okay@example.com"
        )
    }

    suspend fun saveUserName(name: String)
    {
        dataStore.edit { preferences ->
            preferences[NAME] = name
        }
    }

    suspend fun saveUserAge(age: Int)
    {
        dataStore.edit { preferences ->
            preferences[AGE] = age
        }
    }

    suspend fun saveUserProfession(profession: String)
    {
        dataStore.edit { preferences ->
            preferences[PROFESSION] = profession
        }
    }

    suspend fun saveUserEmail(email: String)
    {
        dataStore.edit { preferences ->
            preferences[EMAIL] = email
        }
    }

}