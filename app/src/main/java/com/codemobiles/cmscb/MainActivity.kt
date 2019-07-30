package com.codemobiles.cmscb

import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.codemobiles.cmscb.database.AppDatabase
import com.codemobiles.cmscb.database.UserEntity
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // ตัวแปรนี้จะมีค่าหรือไม่มีก็ได้
    var mDatabaseAdapter: AppDatabase? = null

    // มีค่าแน่นอน แต่ไม่ใช่ตอนนี้
    lateinit var mCMWorkerThread: CMWorkerThread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()




        // bind click
        btnLogin.setOnClickListener {
            val username = edtUsername.text.toString()
            Log.d("username: ","username:"+username)
            val password = edtPassword.text.toString()

            // อันนี้คือการรันเธรด คล้ายๆกับ asynctask แต่ง่ายกว่า ใส่ตัวแปร task
            val task = Runnable {
                var result = mDatabaseAdapter!!.userDao().queryUser(username)
                if (result == null) {
                    //insert
                    mDatabaseAdapter!!.userDao().addUser(UserEntity(null, "scb1", username,password, "admin",22))
                    Toast.makeText(applicationContext, "Insert Successfully!", Toast.LENGTH_LONG).show()
                }else if (result.password == password){
                    //success
                    Toast.makeText(applicationContext, "Login Successfully!", Toast.LENGTH_LONG).show()
                    Prefs.putString(PREFS_KEY_USERNAME, username)
                    Prefs.putString(PREFS_KEY_PASSWORD, password)

                    // โค้ดสำหรับเปิดหน้าอื่น ใช้ class intent โดยรับ params 2 ตัว คือตัวปัจจุบัน
                    // context คือ บริบท (หน้าปัจจุบันที่แสดงอยู่) สามารถดึง library ได้
                    // context => applicationContext, this@xxxx, this
                    val intent = Intent(applicationContext, SuccessActivity::class.java)
                    intent.putExtra(USER_BEAN, result)
                    print("------- result: $result")
                    startActivity(intent)

                    // pass parameter
                    // serializable คือการห่อ obj
//                    intent.putExtra("aaaa",1234)
//                    intent.putExtra("bbbb","aaa")
//                    intent.putExtra("cccc",true)
//                    intent.putExtra("dddd",12.4)
                    // วิธีการ pass obj คือต้องไป implement pacelable ใน userEntity

                    // finish() คือคำสั่งปิด activity พอ login เสร็จหน้านี้จะหายไป
                    finish()

                }else{
                    //update
                    result.password = password
                    mDatabaseAdapter!!.userDao().updateUser(result)
                    Toast.makeText(applicationContext, "Update Successfully!", Toast.LENGTH_LONG).show()
                }
            }

            mCMWorkerThread.postTask(task)
        }

        edtUsername.setText(Prefs.getString(PREFS_KEY_USERNAME,""))
        edtPassword.setText(Prefs.getString(PREFS_KEY_PASSWORD,""))


        setupDatabase()
        setupWorkerThread()
        // context คือ
    }

    // scope function
    private fun setupWorkerThread() {
        mCMWorkerThread = CMWorkerThread("scb_database").also {
            it.start()
        }
    }

    private fun setupDatabase() {
        mDatabaseAdapter = AppDatabase.getInstance(this).also {
            it.openHelper.readableDatabase
        }
    }
}
