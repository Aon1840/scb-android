package com.codemobiles.cmscb.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.codemobiles.cmscb.ChartFragment
import com.codemobiles.cmscb.JSONFragment
import com.codemobiles.cmscb.R
import kotlinx.android.synthetic.main.tab_layout.view.*


private val TAB_TITLES = arrayOf<String>("JSON", "CHART")
private val TAB_ICONS = arrayOf<Int>(R.drawable.ic_tab_json, R.drawable.ic_tab_chart)


class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> JSONFragment()
            else -> ChartFragment()
        }
    }

    // ดึงชื่อ title มาแสดง
//    override fun getPageTitle(position: Int): CharSequence? {
//        return context.resources.getString(TAB_TITLES[position])
//    }


    override fun getCount(): Int {
        // Show 2 total pages.
        return TAB_TITLES.size
    }

    // สร้าง custom tab ที่มีรูปภาพมาด้วย
    // Custom Tabs (step1)
    fun getTabView(position: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.tab_layout, null).apply {
            title.text = TAB_TITLES[position]
            icon.setImageResource(TAB_ICONS[position])
        }
    }
    // step 2 เขียน programmatic ที่ success activity
}