package com.paulbaker.cookpad.feature.home.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paulbaker.cookpad.core.utils.Utils
import com.paulbaker.cookpad.data.datasource.local.FoodHomeModel
import com.paulbaker.cookpad.data.datasource.remote.FoodResponse
import com.paulbaker.cookpad.data.datasource.remote.RecipesResponse
import com.paulbaker.cookpad.databinding.ItemFoodBinding
import com.paulbaker.cookpad.databinding.ItemFoodHomeBinding
import com.squareup.picasso.Picasso

class FoodHomeAdapter(
    val context: Context,
    val data: MutableList<FoodHomeModel>,
    val clickItem: SetOnItemClick? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val typeOneByTwo = 0
        const val typeTwoByThree = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            typeOneByTwo -> {
                val binding =
                    ItemFoodHomeBinding.inflate(LayoutInflater.from(context), parent, false)
                OneByTwoWidthViewHolder(binding)
            }
            typeTwoByThree -> {
                val binding =
                    ItemFoodHomeBinding.inflate(LayoutInflater.from(context), parent, false)
                TwoByThreeWidthViewHolder(binding)
            }
            else -> {
                throw IllegalStateException("cannot create viewHolder")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        when (holder.itemViewType) {
            typeTwoByThree -> {
                (holder as? TwoByThreeWidthViewHolder)?.bindData(item)
            }
            typeOneByTwo -> {
                (holder as? OneByTwoWidthViewHolder)?.bindData(item)
            }
            else -> {
                throw java.lang.IllegalStateException("cannot convert item view type")
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (data[position].type == null)
            0
        else data[position].type!!
    }

    inner class OneByTwoWidthViewHolder(val binding: ItemFoodHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(item: FoodHomeModel) {
            val childAdapter =
                ChildFoodHomeAdapter(context, item.item?.toMutableList(), clickItem, item.type)
            binding.rcvMonAnHomePage.adapter = childAdapter
            binding.rcvMonAnHomePage.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.homepageHorizontalTextTitle.text = item.category
        }
    }


    inner class TwoByThreeWidthViewHolder(val binding: ItemFoodHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: FoodHomeModel) {
            val childAdapter =
                ChildFoodHomeAdapter(context, item.item?.toMutableList(), clickItem, item.type)
            binding.rcvMonAnHomePage.adapter = childAdapter
            binding.rcvMonAnHomePage.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.homepageHorizontalTextTitle.text = item.category
        }
    }

    interface SetOnItemClick {
        fun onItemClick(view: View, item: RecipesResponse.Data?)
    }

}

