package com.semihozmen.kriptoretrofitkotlin.service

import com.semihozmen.kriptoretrofitkotlin.model.CryptoModel
import io.reactivex.Observable
import io.reactivex.Observer
import retrofit2.Call
import retrofit2.http.GET

interface CryptoAPI {

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")

   fun getAll(): Observable<List<CryptoModel>>
   // fun gatAll():Call<List<CryptoModel>>


}