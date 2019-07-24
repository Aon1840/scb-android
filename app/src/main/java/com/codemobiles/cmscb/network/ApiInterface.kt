package com.codemobiles.cmscb.network;

import com.codemobiles.cmscb.models.User
import com.codemobiles.cmscb.models.UserAdvance
import com.codemobiles.cmscb.models.YoutubeResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("adhoc/youtubes/index_new.php")
    fun getYoutubes(@Query("username") username: String,
                    @Query("password") password: String,
                    @Query("type") type: String): Call<YoutubeResponse>

    // [...] json array
    @GET("posts")
    fun getPosts(): Call<List<User>>


    @GET("users")
    fun getUsers(): Call<List<UserAdvance>>

    // component obj คือ
    companion object Factory {
        private val BASE_URL = "http://codemobiles.com/"
        private val BASE_URL2 = "https://jsonplaceholder.typicode.com/"

        private var retrofit: Retrofit? = null
        private var retrofit2: Retrofit? = null
        private var retrofit3: Retrofit? = null

        fun getClient(): ApiInterface {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!.create(ApiInterface::class.java)
        }

        fun getAllPost(): ApiInterface {
            if (retrofit2 == null) {
                retrofit2 = Retrofit.Builder()
                    .baseUrl(BASE_URL2)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit2!!.create(ApiInterface::class.java)
        }

        fun getAllUser(): ApiInterface {
            if (retrofit3 == null) {
                retrofit3 = Retrofit.Builder()
                    .baseUrl(BASE_URL2)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit2!!.create(ApiInterface::class.java)
        }
    }
}



// static เข้าได้จากทุกที่
//val e = Attapon.age
//class Attapon {
//    // คือ static ใน java
//    companion object {
//        val name: String = "Attapon"
//        val age: Int = 18
//    }
//}