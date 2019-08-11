package com.thoughtgame.view

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thoughtgame.R
import com.thoughtgame.inflate
import com.thoughtgame.model.Player
import kotlinx.android.synthetic.main.result_layout.view.*


private var ii=1

class ResultsListAdapter(private val arrNewsUpdates: MutableList<Player>, private val listener: (Int) -> Unit) : RecyclerView.Adapter<ResultsListAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(inflate(parent.context, R.layout.result_layout, parent, false))
    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.bind(arrNewsUpdates[position], listener)
    }

    override fun getItemCount(): Int {
        return arrNewsUpdates.size
    }

    fun countq(): Int {
        return arrNewsUpdates.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n", "ResourceAsColor")
        fun bind(pl: Player, listener: (Int) -> Unit) = with(itemView) {
            if(ii==1) back_rate.setCardBackgroundColor(android.graphics.Color.parseColor("#FEE101"))
            if(ii==2) back_rate.setCardBackgroundColor(android.graphics.Color.parseColor("#D7D7D7"))
            if(ii==3) back_rate.setCardBackgroundColor(android.graphics.Color.parseColor("#A77044"))
            if(ii>3)  back_rate.setCardBackgroundColor(android.graphics.Color.parseColor("#fefefe"))

            place.text = ii.toString()
            name.text = pl.name
            android.util.Log.e("N11",pl.name)
            score.text = "${pl.stars} / ${pl.cards}"
            val plsize =  com.thoughtgame.storage.SharedPrefManager.getInstance(context).getPlayers.size
            if(ii== plsize) ii=1
            else            ii++






            itemView.setOnClickListener { listener(adapterPosition) }
            itemView.setOnLongClickListener {
                android.util.Log.e("digital",((adapterPosition+1)*-1).toString())
                listener((adapterPosition+1)*-1)
                return@setOnLongClickListener true
            }
        }
    }




}

