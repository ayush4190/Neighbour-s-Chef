package com.example.myapplication.ui.fragment.settings

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.util.common.PREFERENCE_THEME

class SettingsViewModel(sharedPreferences: SharedPreferences): ViewModel() {
    private val _theme: MutableLiveData<Int> = MutableLiveData(
        sharedPreferences.getInt(
            PREFERENCE_THEME,
            AppCompatDelegate.MODE_NIGHT_NO
        )
    )

    val theme: MutableLiveData<Int>
        get() = _theme
}

@Suppress("UNCHECKED_CAST")
class SettingsViewModelFactory(private val sharedPreferences: SharedPreferences): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            SettingsViewModel(sharedPreferences) as T
        }
        else {
            throw IllegalArgumentException ("${modelClass.simpleName} is not assignable by ${SettingsViewModel::class.java.simpleName}")
        }
}