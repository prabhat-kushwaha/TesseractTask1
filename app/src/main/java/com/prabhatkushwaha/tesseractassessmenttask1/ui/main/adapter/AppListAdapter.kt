package com.prabhatkushwaha.tesseractassessmenttask1.ui.main.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import com.prabhatkushwaha.tesseractassessmenttask1.R
import com.prabhatkushwaha.tesseractassessmenttask1.data.model.AppDataModel
import com.prabhatkushwaha.tesseractassessmenttask1.databinding.ItemAppLayoutBinding

class AppListAdapter(
    private var appList: List<AppDataModel>,
    private val onItemClick: (appModel: AppDataModel) -> Unit
) :
    RecyclerView.Adapter<AppListAdapter.ViewHolder>() {

    fun setFilterData(filterList: List<AppDataModel>) {
        appList = filterList
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemAppLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_app_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.appModel = appList[position]
        holder.itemView.setOnClickListener {
            onItemClick.invoke(appList[position])
        }
    }

    override fun getItemCount(): Int {
        return appList.size
    }
}