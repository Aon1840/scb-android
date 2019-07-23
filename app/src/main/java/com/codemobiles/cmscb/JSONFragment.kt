package com.codemobiles.cmscb


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codemobiles.cmscb.models.Youtube
import com.codemobiles.cmscb.models.YoutubeResponse
import com.codemobiles.cmscb.network.ApiInterface
import kotlinx.android.synthetic.main.custom_list.view.*
import kotlinx.android.synthetic.main.fragment_json.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class JSONFragment : Fragment() {

    private var mDataArray: ArrayList<Youtube> = ArrayList<Youtube>()
    private lateinit var mAdapter:CustomAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val _view = inflater.inflate(R.layout.fragment_json, container, false)

        mAdapter = CustomAdapter()
        _view.recycleView.let {
            it.adapter = mAdapter

            // Important ***
            it.layoutManager = LinearLayoutManager(activity)
            // it.layoutManager = LinearLayoutManager(activity, LinearLayout.HORIZONTAL, false)
            // _view.recycleView.layoutManager = GridLayoutManager(activity,2)
        }

        feedData()

        return _view
    }

    private fun feedData() {
        // template ติดต่อกับ network
        val call = ApiInterface.getClient().getYoutubes("admin","password","songs")

        // check request ด้วย
        Log.d("SCB_NETWORK", call.request().url().toString())

        call.enqueue(object : Callback<YoutubeResponse> {
            override fun onFailure(call: Call<YoutubeResponse>, t: Throwable) {
                Log.d("SCB_NETWORK", t.message.toString())
            }

            override fun onResponse(call: Call<YoutubeResponse>, response: Response<YoutubeResponse>) {
                Log.d("SCB_NETWORK", response.body().toString())

                if (response.isSuccessful) {
                    mDataArray.clear()
                    mDataArray.addAll(response.body()!!.youtubes)

                    //important
                    mAdapter.notifyDataSetChanged()
                }
            }

        })

    }

    // ควบคุม recycle view เหมือน delegate (adapter หรือตัวควมคุม)
    inner class CustomAdapter : RecyclerView.Adapter<CustomHolder>() {
        //layout item คืออะไร ใช้ไฟล์อะไร
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
            return CustomHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.custom_list,
                    parent,
                    false
                )
            )
        }

        // มีกี่ตัว
        override fun getItemCount(): Int {
            return mDataArray.size
        }

        // รายละเอียดข้างใน ตัวข้างล่างจะ relative กับตัว class CustomHolder
        override fun onBindViewHolder(holder: CustomHolder, position: Int) {
//            holder.titleTextView.text = "Attapon"
            val item = mDataArray[position]

            holder.titleTextView.text = item.title
            holder.subTitleTextView.text = item.subtitle

            Glide.with(this@JSONFragment)
                .load(item.avatar_image)
                .into(holder.avatarImage)

            Glide.with(this@JSONFragment)
                .load(item.youtube_image)
                .into(holder.youtubeImageView)
        }

    }


    class CustomHolder(view: View) : RecyclerView.ViewHolder(view) {
        // เอา id ทุกตัวมาไว้ในนี้
        val avatarImage: ImageView = view.avatarImageView
        val titleTextView: TextView = view.titleTextView
        val subTitleTextView: TextView = view.subtitleTextView
        val youtubeImageView: ImageView = view.youtubeImageView
    }


}

// template
//inner class CustomAdapter() :
//    RecyclerView.Adapter<CustomHolder>() {
//
//    override fun onBindViewHolder(holder: CustomHolder, position: Int) {
//
//    }
//
//    override fun getItemCount(): Int =  10
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
//        return CustomHolder(
//            LayoutInflater.from(parent.context).inflate(
//                R.layout.custom_list,
//                parent,
//                false
//            )
//        )
//    }
//}
//
//inner class CustomHolder(view: View) : RecyclerView.ViewHolder(view) {
//
//    val title: TextView = view.titleTextView
//    val subtitle: TextView = view.subTitleTextView
//    val youtubeImage: ImageView = view.youtubeImageView
//    val avatarImage: ImageView = view.avatarImageView
//
//
//}
