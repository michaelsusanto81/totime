package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.covid.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CovidHomeViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CovidHomeViewModel::class.java)) {
            return CovidHomeViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}