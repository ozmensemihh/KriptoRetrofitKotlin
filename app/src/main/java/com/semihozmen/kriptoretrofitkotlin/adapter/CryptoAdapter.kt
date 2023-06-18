package com.semihozmen.kriptoretrofitkotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.semihozmen.kriptoretrofitkotlin.databinding.RecRowBinding
import com.semihozmen.kriptoretrofitkotlin.model.CryptoModel

class CryptoAdapter(val cryptoList : ArrayList<CryptoModel>) :RecyclerView.Adapter<CryptoAdapter.CryptoHolder> (){


    class CryptoHolder (val binding:RecRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoHolder {
        val binding = RecRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CryptoHolder(binding)
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }

    override fun onBindViewHolder(holder: CryptoHolder, position: Int) {

        holder.binding.txtCurrency.text = cryptoList.get(position).currency
        holder.binding.txtPrice.text = cryptoList.get(position).price

    }

}