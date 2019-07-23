package com.codemobiles.cmscb.database

import androidx.room.*


// นิยามฟังก์ชันที่ไม่มีการทำงาน
@Dao
interface UserDAO {

    @Query("select * from user")
    fun queryUsers(): UserEntity

    @Query("select * from user where username = :username")
    fun queryUser(username: String): UserEntity?

    @Insert
    fun addUser(userEntity: UserEntity)

    @Update
    fun updateUser(userEntity: UserEntity)

    @Delete
    fun deleteUser(userEntity: UserEntity)
}