package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.splash

import android.content.Context
import androidx.lifecycle.ViewModel
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.User
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.repository.UserRepository
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.service.SessionManager
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.util.Constants.VALID_EMAIL_ADDRESS_REGEX
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.util.ValidationResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SplashViewModel(private val context: Context): ViewModel() {

    private val pref: SessionManager = SessionManager(context)
    private val repository: UserRepository = UserRepository(context)

    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    fun validateInput(name: String, email: String): ValidationResponse {
        if(name.isEmpty() || email.isEmpty()) {
            return ValidationResponse(true, "Field can't be empty.")
        } else if(name.length > 50) {
            return ValidationResponse(true, "Name should at most 50 characters.")
        } else if(email.length > 70) {
            return ValidationResponse(true, "Email should at most 70 characters.")
        } else if(!VALID_EMAIL_ADDRESS_REGEX.matcher(email).find()) {
            return ValidationResponse(true, "Email address is not valid.")
        }
        return ValidationResponse(false, "Successfully saved!")
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