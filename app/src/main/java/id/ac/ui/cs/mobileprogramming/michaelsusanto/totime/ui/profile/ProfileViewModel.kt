package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.profile

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.User
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProfileViewModel(private val context: Context): ViewModel() {

    private val repository: UserRepository = UserRepository(context)
    val liveData = MutableLiveData<User>()

    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    fun validateInput(name: String, email: String): Boolean {
        return name.isNotEmpty() && email.isNotEmpty()
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