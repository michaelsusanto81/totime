package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.profile

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.User
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.repository.UserRepository
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.util.Constants
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.util.ValidationResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProfileViewModel(private val context: Context): ViewModel() {

    private val repository: UserRepository = UserRepository(context)
    val liveData = MutableLiveData<User>()

    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    fun validateInput(name: String, email: String): ValidationResponse {
        if(name.isEmpty() || email.isEmpty()) {
            return ValidationResponse(true, "Field can't be empty.")
        } else if(name.length > 50) {
            return ValidationResponse(true, "Name should at most 50 characters.")
        } else if(email.length > 70) {
            return ValidationResponse(true, "Email should at most 70 characters.")
        } else if(!Constants.VALID_EMAIL_ADDRESS_REGEX.matcher(email).find()) {
            return ValidationResponse(true, "Email address is not valid.")
        }
        return ValidationResponse(false, "Successfully saved!")
    }

    fun getData() {
        coroutineScope.launch {
            val user = repository.getUsers()[0]
            liveData.value = user
        }
    }

    fun updateData(user: User) {
        coroutineScope.launch {
            repository.updateUser(user)
        }
    }
}