package com.paulbaker.cookpad.feature.home.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.paulbaker.cookpad.R
import com.paulbaker.cookpad.core.utils.Utils
import com.paulbaker.cookpad.data.datasource.remote.FoodResponse
import com.paulbaker.cookpad.databinding.ItemFoodBinding
import com.paulbaker.cookpad.feature.home.adapter.FoodHomeAdapter.Companion.typeOneByTwo
import com.paulbaker.cookpad.feature.home.adapter.FoodHomeAdapter.Companion.typeTwoByThree
import com.squareup.picasso.Picasso
import java.lang.IllegalStateException

class ChildFoodHomeAdapter(
    val context: Context,
    val data: MutableList<FoodResponse>?,
    val clickItem: FoodHomeAdapter.SetOnItemClick? = null,
    val type: Int? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            typeOneByTwo -> {
                val binding = ItemFoodBinding.inflate(LayoutInflater.from(context), parent, false)
                ViewHolder(binding)
            }
            typeTwoByThree -> {
                val binding = ItemFoodBinding.inflate(LayoutInflater.from(context), parent, false)
                TwoByThreeWidthViewHolder(binding)
            }
            else -> {
                throw IllegalStateException("cannot create viewHolder")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data?.get(position)
        when (holder.itemViewType) {
            typeTwoByThree -> {
                (holder as? TwoByThreeWidthViewHolder)?.bindData(item)
            }
            typeOneByTwo -> {
                (holder as? ViewHolder)?.bindData(item)
            }
            else -> {
                throw IllegalStateException("cannot convert item view type")
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return type ?: 0
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
            binding.root.layoutParams.width = (Utils.getDeviceWidth(context) / 2)
            binding.root.layoutParams.height= (Utils.getDeviceWidth(context)/2+Utils.getDeviceWidth(context)/5)
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

    inner class TwoByThreeWidthViewHolder(val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        init {
            binding.itemFoodAnhDaiDienMonAn.setOnClickListener(this)
            binding.itemFoodButtonLike.setOnClickListener(this)
            binding.itemFoodButtonDisLike.setOnClickListener(this)
        }

        fun bindData(item: FoodResponse?) {
            binding.root.layoutParams.width = (Utils.getDeviceWidth(context) * (2f / 3f)).toInt()
            binding.root.layoutParams.height =
                ((Utils.getDeviceWidth(context) * (2f / 3f)) + Utils.getDeviceWidth(context) / 5).toInt()
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