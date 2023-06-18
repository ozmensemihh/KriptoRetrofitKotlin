package com.semihozmen.kriptoretrofitkotlin.model

import com.google.gson.annotations.SerializedName

data class CryptoModel (

   // @SerializedName("currency") Değişken adıyla aynı ise yazılmasına gerek yok.
    val currency:String,

   // @SerializedName("price") Değişken adıyla aynı ise yazılmasına gerek yok.
    val price:String)