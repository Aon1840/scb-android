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
import com.codemobiles.cmscb.models.UserAdvance
import com.codemobiles.cmscb.network.ApiInterface
import kotlinx.android.synthetic.main.custom_post_advance.view.*
import kotlinx.android.synthetic.main.fragment_json.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserAdvanceFragment : Fragment() {

    private var mDataArray: ArrayList<UserAdvance> = ArrayList<UserAdvance>()
    private lateinit var mAdapter: CustomAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val _view = inflater.inflate(R.layout.fragment_post_advance, container, false)

        mAdapter = CustomAdapter()

        _view.recycleView.let {
            it.adapter = mAdapter
            it.layoutManager = LinearLayoutManager(activity)
        }

        feedData()

        return _view
    }

    private fun feedData(){
        val call = ApiInterface.getAllUser().getUsers()
        Log.d("----- Test api", call.request().url().toString())

        call.enqueue(object : Callback<List<UserAdvance>> {
            override fun onFailure(call: Call<List<UserAdvance>>, t: Throwable) {
                Log.d("JSONPLACEHOLDER_AV_FAIL", "------ Error: "+t.message.toString())
            }

            override fun onResponse(call: Call<List<UserAdvance>>, response: Response<List<UserAdvance>>) {
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
                    R.layout.custom_post_advance,
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

            holder.usernameTextView.text = "Username: "+item.username
            holder.emailTextView.text = "Email: "+item.email
            holder.streetTextView.text = "Street: "+item.address.street
            holder.catchPhraseTextView.text = "CatchPhrease: "+item.company.catchPhrase
        }

    }

    class CustomHolder(view: View) : RecyclerView.ViewHolder(view) {
        val usernameTextView: TextView = view.usernameTextView
        val emailTextView: TextView = view.emailTextView
        val streetTextView: TextView = view.streetTextView
        val catchPhraseTextView: TextView = view.catchPhraseTextView
    }


}
