package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.repository

import android.content.Context
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.dao.UserDao
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.database.UserDatabase
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.User

class UserRepository(context: Context) {

    private lateinit var dao: UserDao
    private val db: UserDatabase? = UserDatabase.getInstance(context)

    init {
        if (db != null) {
            dao = db.userDao()
        }
    }

    suspend fun getUser(user: User): User {
        return dao.getUser(user.id, user.name, user.email)
    }

    suspend fun addUser(user: User) {
        dao.addUser(user)
    }

    suspend fun updateUser(user: User): Int {
        return dao.updateUser(user.id, user.name, user.email, user.profPic)
    }
}