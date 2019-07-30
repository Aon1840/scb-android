package com.codemobiles.cmscb.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.codemobiles.cmscb.DATABASE_NAME


// #step2
@Database(entities = [UserEntity::class, CompanyEntity::class], version = 5, exportSchema = true)

// abstract class คือ class ที่ไม่สามารถ new obj ได้ว
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDAO // #step3
    abstract fun companyDao(): CompanyDAO

    companion object {

        private val TAG: String by lazy { AppDatabase::class.java.simpleName }

        // For Singleton instantiation, visible to other threads.
//        @Volatile
        private var instance: AppDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE Company (id INTEGER AUTO_INCREMENT, name TEXT NOT NULL, address TEXT NOT NULL, PRIMARY KEY (id));")
            }


        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE User ADD COLUMN role TEXT NOT NULL DEFAULT 'admin'")
            }
        }

        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE User ADD COLUMN age TEXT")
            }
        }

        val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // SQLite supports a limited operations for ALTER.
                // Changing the type of a column is not directly supported, so this is what we need
                database.execSQL(
                    "CREATE TABLE users_new (id INTEGER AUTO_INCREMENT,"
                            + "user_id TEXT NOT NULL,"
                            + "username TEXT NOT NULL,"
                            + "password TEXT NOT NULL,"
                            + "role TEXT NOT NULL, "
                            + "age INTEGER, "
                            + "PRIMARY KEY(id))"
                )
                database.execSQL(
                    "INSERT INTO users_new (id, user_id, username, password, role, age) " +
                            "SELECT id, user_id, username, password, role, age " +
                            "FROM user"
                )
                database.execSQL("DROP TABLE user")
                database.execSQL("ALTER TABLE users_new RENAME TO user")
            }
        }

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
                })
                    // สร้างตัว migration ขึ้นมายัดใส่
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5)

                    .build().also {
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