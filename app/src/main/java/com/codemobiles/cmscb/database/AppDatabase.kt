package com.codemobiles.cmscb.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.codemobiles.cmscb.DATABASE_NAME


// #step2
@Database(entities = [UserEntity::class], version = 1, exportSchema = true)

// abstract class คือ class ที่ไม่สามารถ new obj ได้ว
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDAO // #step3

    companion object {

        private val TAG: String by lazy { AppDatabase::class.java.simpleName }

        // For Singleton instantiation, visible to other threads.
//        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {

            instance?.let {
                return it
            }

            // โค้ดข้างล่างเหมือนกับโค้ดบรรทัดที่ 30-32 มันเรียกว่า scope function
//            if (instance != null) {
//                return instance!!
//            }

            synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME // #step1
                ).addCallback(object : RoomDatabase.Callback() {
                    // onCreate will be called when the database is created for the first time
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Log.d(TAG, "onCreate")
                    }
                }).build().also {
                    instance = it
                    return instance!!
                }
            }
        }

        fun destroyInstance() {
            instance = null
        }
    }
}


// singleton -> new ครั้งแรก ใช้ได้ตลอดชีวิต
//class  aaa {
//    init {
//
//    }
//}