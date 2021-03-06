package com.example.headsupprep

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.headsupprep.apiModel.CelebritiesItem
import com.example.headsupprep.databinding.RowBinding
import com.example.headsupprep.roomModel.CelebritiesRoom

class AdapterMain(private var list: List<CelebritiesRoom>, val activity: Activity):RecyclerView.Adapter<AdapterMain.Holder>() {
    class Holder ( val binding: RowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
       return Holder(RowBinding.inflate(LayoutInflater.from(parent.context),
       parent,
       false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = list[position]
       holder.binding.apply {
           tvName.text = item.name
           tv1.text = item.taboo1
           tv2.text = item.taboo2
           tv3.text = item.taboo3
       }
        holder.binding.llRow.setOnClickListener {
            (activity as MainActivity).toEditDeleteActivity(position)
        }
    }

    override fun getItemCount()= list.size

    fun update(myList:List<CelebritiesRoom>){
        this.list = myList
        notifyDataSetChanged()
    }
}