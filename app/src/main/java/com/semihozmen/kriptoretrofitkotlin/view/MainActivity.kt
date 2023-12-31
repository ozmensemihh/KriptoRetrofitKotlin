package com.semihozmen.kriptoretrofitkotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.semihozmen.kriptoretrofitkotlin.R
import com.semihozmen.kriptoretrofitkotlin.adapter.CryptoAdapter
import com.semihozmen.kriptoretrofitkotlin.databinding.ActivityMainBinding
import com.semihozmen.kriptoretrofitkotlin.model.CryptoModel
import com.semihozmen.kriptoretrofitkotlin.service.CryptoAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {

    private val BASE_URL="https://raw.githubusercontent.com/"
    private lateinit var binding: ActivityMainBinding
    private var compositeDisposable : CompositeDisposable? = null
    private var job :Job? = null
    val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println(throwable.localizedMessage)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        compositeDisposable = CompositeDisposable()
        loadData()

        lifecycleScope.launch (handler) {
            supervisorScope {               // iç içe launcler da birinde hata olduğunda
                launch {                    // diğerlerinin de çalışmaya devam etmesi için
                    throw Exception("Error")
                }
                launch {
                    delay(5000)
                    println("executed")
                }
            }
        }


    }


    private fun loadData(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(CryptoAPI::class.java)

        job = CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getAll()
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    response.body()?.let {
                        binding.rv.layoutManager = LinearLayoutManager(this@MainActivity)
                        val adapter = CryptoAdapter(ArrayList(it))
                        binding.rv.adapter = adapter
                        binding.progressBar2.visibility = View.INVISIBLE
                    }
                }
            }
        }


        /*
        compositeDisposable?.add(retrofit.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse))

         */

        /*
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

         */
    }

    private fun handleResponse(list :List<CryptoModel>){
        list?.let {
            binding.rv.layoutManager = LinearLayoutManager(this@MainActivity)
            val adapter = CryptoAdapter(ArrayList(it))
            binding.rv.adapter = adapter
            binding.progressBar2.visibility = View.INVISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
       // compositeDisposable!!.clear()
        job?.cancel()
    }
}