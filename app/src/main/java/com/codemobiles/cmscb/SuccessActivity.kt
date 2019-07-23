package com.codemobiles.cmscb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.codemobiles.cmscb.database.UserEntity
import com.codemobiles.cmscb.ui.main.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout

class SuccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)


        // step 2 custom tab
        for (i in 0 until tabs.tabCount) {
            val tab: TabLayout.Tab? = tabs.getTabAt(i)
            tab!!.customView = sectionsPagerAdapter.getTabView(i)
        }

        // รับค่าที่ pass มา
        val user: UserEntity = intent.getParcelableExtra(USER_BEAN) as UserEntity
        print("-------user: $user")
        showToast(user.username + user.password)
    }
}