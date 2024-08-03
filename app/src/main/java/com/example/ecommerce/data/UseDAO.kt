package com.example.ecommerce.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDAO {
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM user WHERE username = :username AND password= :password")
    suspend fun login(username: String, password: String): User?

}