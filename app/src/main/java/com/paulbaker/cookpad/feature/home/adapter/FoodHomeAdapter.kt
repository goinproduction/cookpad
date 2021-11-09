package com.paulbaker.cookpad.feature.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paulbaker.cookpad.data.datasource.local.FoodHomeModel
import com.paulbaker.cookpad.data.datasource.remote.FoodResponse
import com.paulbaker.cookpad.databinding.ItemFoodHomeBinding

class FoodHomeAdapter(
    val context: Context,
    val data: MutableList<FoodHomeModel>,
    val clickItem: SetOnItemClick? = null
) :
    RecyclerView.Adapter<FoodHomeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFoodHomeBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bindData(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(val binding: ItemFoodHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(item: FoodHomeModel) {
            val childAdapter = ChildFoodHomeAdapter(context, item.listItem, clickItem)
            binding.rcvMonAnHomePage.adapter = childAdapter
            binding.rcvMonAnHomePage.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.homepageHorizontalTextTitle.text = item.category
        }
    }

    interface SetOnItemClick {
        fun onItemClick(view: View, item: FoodResponse?)
    }
}