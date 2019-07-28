package ru.skillbranch.devintensive.viewmodels

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.repositories.PreferencesRepository
import ru.skillbranch.devintensive.utils.Utils

class ProfileViewModel : ViewModel() {
    private val repository = PreferencesRepository
    private val profileData = MutableLiveData<Profile>()
    private val appTheme = MutableLiveData<Int>()
    private val isRepoError = MutableLiveData<Boolean>()

    init {
        Log.d("M_ProfileViewModel","view_model is initialised")
        profileData.value = repository.getProfile()
        appTheme.value = repository.getAppTheme()
        onAdressRepositoryChanged(repository.getProfile().repository)
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("M_ProfileViewModel","view_model is cleared")
    }

    fun getProfileData():LiveData<Profile> = profileData

    fun saveProfileData(profile: Profile){
        if(isRepoError.value?:false) profile.repository = ""
        isRepoError.value = true
        repository.saveProfile(profile)
        profileData.value = profile
    }

    fun getTheme():LiveData<Int> = appTheme

    fun isRepoError():LiveData<Boolean> = isRepoError

    fun switchTheme() {
        if (appTheme.value == AppCompatDelegate.MODE_NIGHT_YES) {
            appTheme.value = AppCompatDelegate.MODE_NIGHT_NO
        } else {
            appTheme.value = AppCompatDelegate.MODE_NIGHT_YES
        }
        repository.saveAppTheme(appTheme.value!!)
    }

    fun onAdressRepositoryChanged(repoValue: String) {
        isRepoError.value = !(Utils.mathGitHubAccount(repoValue)||repoValue.isEmpty())
    }


}