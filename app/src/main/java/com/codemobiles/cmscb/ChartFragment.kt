package com.codemobiles.cmscb


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codemobiles.cmscb.models.User
import com.codemobiles.cmscb.network.ApiInterface
import kotlinx.android.synthetic.main.custom_list.view.titleTextView
import kotlinx.android.synthetic.main.custom_post_basic.view.*
import kotlinx.android.synthetic.main.fragment_json.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ChartFragment : Fragment() {

    private var mDataArray: ArrayList<User> = ArrayList<User>()
    private lateinit var mAdapter: CustomAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val _view = inflater.inflate(R.layout.fragment_chart, container, false)

        mAdapter = CustomAdapter()

        _view.recycleView.let {
            it.adapter = mAdapter
            it.layoutManager = LinearLayoutManager(activity)
        }

        feedData()

        return _view
    }

    private fun feedData(){
        val call = ApiInterface.getAllPost().getPosts()
        Log.d("----- Test api", call.request().url().toString())

        call.enqueue(object : Callback<List<User>>{
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.d("JSONPLACEHOLDER_FAIL", "------ Error: "+t.message.toString())
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
//                Log.d("JSONPLACEHOLDER_SUCCESS", response.body().)
                if (response.isSuccessful) {
                    mDataArray.clear()
                    mDataArray.addAll(response.body()!!)

                    mAdapter.notifyDataSetChanged()
                }
            }

        })
    }

    inner class CustomAdapter : RecyclerView.Adapter<CustomHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
            return CustomHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.custom_post_basic,
                    parent,
                    false
                )
            )
        }

        override fun getItemCount(): Int {
            Log.d("TEST-----","------ mDataArray: "+mDataArray.size)
            return mDataArray.size
        }

        override fun onBindViewHolder(holder: CustomHolder, position: Int) {
            val item = mDataArray[position]

            holder.titleTextView.text = item.title
            holder.bodyTextView.text = item.body
        }

    }

    class CustomHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.titleTextView
        val bodyTextView: TextView = view.bodyTextView
    }


}
