package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.splash

import android.content.Context
import androidx.lifecycle.ViewModel
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.User
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.repository.UserRepository
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.service.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SplashViewModel(private val context: Context): ViewModel() {

    private val pref: SessionManager = SessionManager(context)
    private val repository: UserRepository = UserRepository(context)

    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    fun validateInput(name: String, email: String): Boolean {
        return name.isNotEmpty() && email.isNotEmpty()
    }

    fun checkUser(): Boolean {
        val status = pref.fetchData("user")
        if(status != null) {
            return true
        }
        return false
    }

    fun saveData(user: User) {
        coroutineScope.launch {
            repository.addUser(user)
            pref.saveData("user", "registered")
        }
    }
}