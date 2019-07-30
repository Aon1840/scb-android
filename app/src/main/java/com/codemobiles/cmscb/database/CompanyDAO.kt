package com.codemobiles.cmscb.database

import androidx.room.Dao
import androidx.room.Query


// นิยามฟังก์ชันที่ไม่มีการทำงาน
@Dao
interface CompanyDAO {

    @Query("select * from company")
    fun queryCompany(): CompanyEntity
}