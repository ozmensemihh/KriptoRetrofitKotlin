package com.semihozmen.kriptoretrofitkotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.semihozmen.kriptoretrofitkotlin.R
import com.semihozmen.kriptoretrofitkotlin.adapter.CryptoAdapter
import com.semihozmen.kriptoretrofitkotlin.databinding.ActivityMainBinding
import com.semihozmen.kriptoretrofitkotlin.model.CryptoModel
import com.semihozmen.kriptoretrofitkotlin.service.CryptoAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {

    private val BASE_URL="https://raw.githubusercontent.com/"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadData()


    }


    private fun loadData(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(CryptoAPI::class.java)
        val call = service.gatAll()

        call.enqueue(object:Callback<List<CryptoModel>>{
            override fun onResponse(call: Call<List<CryptoModel>>, response: Response<List<CryptoModel>>) {

                if(response.isSuccessful){
                    response.body()?.let {
                        binding.rv.layoutManager = LinearLayoutManager(this@MainActivity)
                        val adapter = CryptoAdapter(ArrayList(it))
                        binding.rv.adapter = adapter
                        binding.progressBar2.visibility = View.INVISIBLE
                    }
                }

            }

            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}