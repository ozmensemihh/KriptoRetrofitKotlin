package com.semihozmen.kriptoretrofitkotlin.service

import com.semihozmen.kriptoretrofitkotlin.model.CryptoModel
import retrofit2.Call
import retrofit2.http.GET

interface CryptoAPI {

    // https://raw.githubusercontent.com/
    // atilsamancioglu/K21-JSONDataSet/master/crypto.json

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    fun gatAll():Call<List<CryptoModel>>


}