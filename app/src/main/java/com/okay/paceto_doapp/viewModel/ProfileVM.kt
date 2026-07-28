package com.okay.paceto_doapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okay.paceto_doapp.data.local.Profile
import com.okay.paceto_doapp.data.repo.ProfileDataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileVM @Inject constructor(
    private val dataStoreManager: ProfileDataStoreManager
): ViewModel() {

    val uiState: StateFlow<Profile> = dataStoreManager.profileDataFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Profile()
        )

    fun saveChanges(profile: Profile) {
        viewModelScope.launch {
            dataStoreManager.saveUserName(profile.name)
            dataStoreManager.saveUserAge(profile.age)
            dataStoreManager.saveUserProfession(profile.profession)
            dataStoreManager.saveUserEmail(profile.email)
        }
    }
}