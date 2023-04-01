package com.shi.shigo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.shi.shigo.R
import com.shi.shigo.databinding.RowGetMoneyBinding
import com.shi.shigo.model.GetMoneyRow
import com.shi.shigo.view.BasketFragment

class GetMoneyAdapter(private val context: Context, private var moneyList: List<GetMoneyRow>, private val basketFragment: BasketFragment): RecyclerView.Adapter<GetMoneyAdapter.GetMoneyViewHolder>(){

    class GetMoneyViewHolder(val binding: RowGetMoneyBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetMoneyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return GetMoneyViewHolder(
            DataBindingUtil.inflate<RowGetMoneyBinding>(inflater, R.layout.row_get_money,parent,false)
        )
    }

    override fun onBindViewHolder(holder: GetMoneyAdapter.GetMoneyViewHolder, position: Int) {
        val data = moneyList[position]
        holder.binding.value = data

        holder.binding.btnGetShi.setOnClickListener {
            basketFragment.launchBilling(position)
        }
    }

    override fun getItemCount(): Int {
        return moneyList.size
    }

}