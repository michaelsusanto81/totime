package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.dao

import androidx.room.*
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM Users WHERE id =:id AND name =:name AND email =:email")
    suspend fun getUser(id: Int, name: String, email: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: User)

    @Query("UPDATE Users SET name =:name, email =:email, prof_pic =:profPic WHERE id =:id")
    suspend fun updateUser(id: Int, name: String, email: String, profPic: String): Int
}