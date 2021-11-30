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
import com.paulbaker.cookpad.data.datasource.remote.RecipesResponse
import com.paulbaker.cookpad.databinding.ItemFoodBinding
import com.paulbaker.cookpad.feature.home.adapter.FoodHomeAdapter.Companion.typeOneByTwo
import com.paulbaker.cookpad.feature.home.adapter.FoodHomeAdapter.Companion.typeTwoByThree
import com.paulbaker.library.core.extension.isNotNull
import com.paulbaker.library.core.extension.isValidValue
import com.squareup.picasso.Picasso
import java.lang.IllegalStateException

class ChildFoodHomeAdapter(
    val context: Context,
    val data: MutableList<RecipesResponse.Data?>?,
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
            binding.containerLike.setOnClickListener(this)
            binding.containerFavorite.setOnClickListener(this)
            binding.containerEmotion.setOnClickListener(this)
        }

        fun bindData(item: RecipesResponse.Data?) {
//            binding.root.layoutParams.width = (Utils.getDeviceWidth(context) / 2)
//            binding.root.layoutParams.height= (Utils.getDeviceWidth(context)/2+Utils.getDeviceWidth(context)/5)

            item?.author?.avatar?.let {
                binding.imageUserPost.setImageBitmap(
                    com.paulbaker.library.core.extension.Utils.decodeBase64ToBitMap(
                        item.author.avatar
                    )
                )
            }
            binding.itemFoodTextNguoiDang.text = item?.author?.name

            item?.image?.let {
                binding.itemFoodAnhDaiDienMonAn.setImageBitmap(
                    com.paulbaker.library.core.extension.Utils.decodeBase64ToBitMap(
                        item.image
                    )
                )
            }
            binding.itemFoodTenMonAn.text = item?.title
            if (item?.likes != 0) {
                binding.containerLike.visibility = View.VISIBLE
                binding.tvLikeCount.text = item?.likes.toString()
            }
            if (item?.hearts != 0) {
                binding.containerFavorite.visibility = View.VISIBLE
                binding.tvFavorite.text = item?.hearts.toString()
            }
            if (item?.claps != 0) {
                binding.containerEmotion.visibility = View.VISIBLE
                binding.tvEmotion.text = item?.claps.toString()
            }
        }

        override fun onClick(v: View?) {
            when (v?.id) {
                R.id.item_food_AnhDaiDienMonAn -> {
                    clickItem?.onItemClick(v, data?.get(adapterPosition))
                }
                R.id.containerLike -> {
                    Log.d("TAG", "Like button pressed")
                }
                R.id.containerEmotion -> {
                    Log.d("TAG", "Emotion button pressed")
                }
                R.id.containerFavorite -> {
                    Log.d("TAG", "Favorite button pressed")
                }
            }
        }
    }

    inner class TwoByThreeWidthViewHolder(val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        init {
            binding.itemFoodAnhDaiDienMonAn.setOnClickListener(this)
            binding.containerLike.setOnClickListener(this)
            binding.containerFavorite.setOnClickListener(this)
            binding.containerEmotion.setOnClickListener(this)
        }

        fun bindData(item: RecipesResponse.Data?) {
            binding.itemFoodAnhDaiDienMonAn.layoutParams.width = (Utils.getDeviceWidth(context) * (2f / 3f)).toInt()

            binding.itemFoodAnhDaiDienMonAn.layoutParams.height =
                ((Utils.getDeviceWidth(context) * (0.6f))).toInt()

            if (item?.author?.avatar?.isNotNull() == true && item.author.avatar.isValidValue()) {
                binding.imageUserPost.setImageBitmap(
                    com.paulbaker.library.core.extension.Utils.decodeBase64ToBitMap(
                        item.author.avatar
                    )
                )
            }
            binding.itemFoodTextNguoiDang.text = item?.author?.name

            if (item?.image?.isNotNull() == true && item.image.isValidValue()) {
                binding.itemFoodAnhDaiDienMonAn.setImageBitmap(
                    com.paulbaker.library.core.extension.Utils.decodeBase64ToBitMap(
                        item.image
                    )
                )
            }
            binding.itemFoodTenMonAn.text = item?.title
            if (item?.likes != 0) {
                binding.containerLike.visibility = View.VISIBLE
                binding.tvLikeCount.text = item?.likes.toString()
            }
            if (item?.hearts != 0) {
                binding.containerFavorite.visibility = View.VISIBLE
                binding.tvFavorite.text = item?.hearts.toString()
            }
            if (item?.claps != 0) {
                binding.containerEmotion.visibility = View.VISIBLE
                binding.tvEmotion.text = item?.claps.toString()
            }
        }

        override fun onClick(v: View?) {
            when (v?.id) {
                R.id.item_food_AnhDaiDienMonAn -> {
                    clickItem?.onItemClick(v, data?.get(adapterPosition))
                }
                R.id.containerLike -> {
                    Log.d("TAG", "Like button pressed")
                }
                R.id.containerEmotion -> {
                    Log.d("TAG", "Emotion button pressed")
                }
                R.id.containerFavorite -> {
                    Log.d("TAG", "Favorite button pressed")
                }
            }
        }
    }
}