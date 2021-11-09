package com.paulbaker.cookpad.feature.home.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.paulbaker.cookpad.R
import com.paulbaker.cookpad.data.datasource.remote.FoodResponse
import com.paulbaker.cookpad.databinding.ItemFoodBinding
import com.squareup.picasso.Picasso

class ChildFoodHomeAdapter(
    val context: Context,
    val data: MutableList<FoodResponse>?,
    val clickItem: FoodHomeAdapter.SetOnItemClick? = null
) :
    RecyclerView.Adapter<ChildFoodHomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data?.get(position)
        holder.bindData(item)
    }

    override fun getItemCount(): Int {
        return if (data.isNullOrEmpty()) 0 else data.size
    }

    inner class ViewHolder(val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        init {
            binding.itemFoodAnhDaiDienMonAn.setOnClickListener(this)
            binding.itemFoodButtonLike.setOnClickListener(this)
            binding.itemFoodButtonDisLike.setOnClickListener(this)
        }

        fun bindData(item: FoodResponse?) {
            Picasso.get().load(item?.urlImageUser).into(binding.imageUserPost)
            binding.itemFoodTextNguoiDang.text = item?.nameUser
            Picasso.get().load(item?.urlImageFood).into(binding.itemFoodAnhDaiDienMonAn)
            binding.itemFoodTenMonAn.text = item?.nameFood
            binding.itemFoodTextSoLuongLike.text = item?.like
            binding.itemFoodTextSoLuongDisLike.text = item?.dislike
        }

        override fun onClick(v: View?) {
            when (v?.id) {
                R.id.item_food_AnhDaiDienMonAn -> {
                    clickItem?.onItemClick(v, data?.get(adapterPosition))
                }
                R.id.item_food_button_Like -> {
                    Toast.makeText(context, "Mày vừa nhấn vào like", Toast.LENGTH_SHORT).show()
                    Log.d("TAG", "Like button pressed")
                }
                R.id.item_food_button_DisLike -> {
                    Toast.makeText(context, "Mày vừa nhấn vào dislike", Toast.LENGTH_SHORT).show()
                    Log.d("TAG", "Dislike button pressed")
                }
            }
        }
    }
}