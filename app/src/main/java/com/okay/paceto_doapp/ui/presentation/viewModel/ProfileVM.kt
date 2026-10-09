package com.okay.paceto_doapp.ui.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okay.paceto_doapp.data.repo.ProfileDataStoreManager
import com.okay.paceto_doapp.ui.presentation.events.ProfileEvent
import com.okay.paceto_doapp.ui.presentation.events.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileVM @Inject constructor(
    private val dataStoreManager: ProfileDataStoreManager
): ViewModel() {

    private val _uiState: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState())
    val uiState = _uiState.asStateFlow()

    init {
        loadExistingProfileData()
    }

    private fun loadExistingProfileData()
    {
        viewModelScope.launch {
            val profile = dataStoreManager.profileDataFlow.first()
            _uiState.update {
                it.copy(
                    name = profile.name,
                    age = profile.age,
                    profession = profile.profession,
                    email = profile.email
                )
            }
        }
    }

    fun onEvent(event: ProfileEvent)
    {
        when(event)
        {
            is ProfileEvent.NameChanged -> _uiState.update { it.copy(name = event.name) }
            is ProfileEvent.AgeChanged -> _uiState.update { it.copy(age = event.age) }
            is ProfileEvent.ProfessionChanged -> _uiState.update { it.copy(profession = event.profession) }
            is ProfileEvent.EmailChanged -> _uiState.update { it.copy(email = event.email) }
            is ProfileEvent.SaveProfile -> saveChanges(event.onSuccessfulSave)
        }
    }

    fun saveChanges(onSaveSuccess: () -> Unit) {
        viewModelScope.launch {
            val profile = uiState.value

            dataStoreManager.saveUserName(profile.name)
            dataStoreManager.saveUserAge(profile.age)
            dataStoreManager.saveUserProfession(profile.profession)
            dataStoreManager.saveUserEmail(profile.email)

            onSaveSuccess()
        }
    }
}