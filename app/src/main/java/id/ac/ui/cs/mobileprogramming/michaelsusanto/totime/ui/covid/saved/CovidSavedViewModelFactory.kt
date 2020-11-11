package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.covid.saved

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CovidSavedViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CovidSavedViewModel::class.java)) {
            return CovidSavedViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}