package com.paulbaker.cookpad.feature.cart.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paulbaker.cookpad.R
import com.paulbaker.cookpad.core.extensions.Strings
import com.paulbaker.cookpad.data.datasource.remote.CartRecipesResponse
import com.paulbaker.cookpad.data.datasource.remote.Data
import com.paulbaker.cookpad.databinding.ItemSearchBinding

class CartRecipesAdapter(
    val context: Context,
    val data: MutableList<CartRecipesResponse.Data.Recipe> = mutableListOf(),
    val mClickItem: SetOnItemClick? = null
) :
RecyclerView.Adapter<CartRecipesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bindData(item)
    }

    override fun getItemCount(): Int {
        return if (data.isNullOrEmpty()) 0 else data.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data : List<CartRecipesResponse.Data.Recipe>){
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }
        fun bindData(item: CartRecipesResponse.Data.Recipe?){
            binding.tvTitleSearchFood.text = item?.title
//            if (item?.author?.avatar?.isNotNull() == true && item.author.avatar.isValidValue()) {
//                binding.imageAuthor.setImageBitmap(
//                    com.paulbaker.library.core.extension.Utils.decodeBase64ToBitMap(
//                        item.author.avatar
//                    )
//                )
//            }
            item?.image?.let {
                binding.imageFood.setImageBitmap(
                    com.paulbaker.library.core.extension.Utils.decodeBase64ToBitMap(
                        item.image
                    )
                )
            }
            if (item?.steps?.isNotEmpty() == true) {
                when {
                    item.steps.size >= 2 -> {
                        binding.tvDetail.text = context.getString(
                            R.string.format_detail, item.steps[0]?.name.toString(),
                            item.steps[1]?.name.toString()
                        )
                    }
                    item.steps.isNotEmpty() -> {
                        binding.tvDetail.text = context.getString(
                            R.string.format_detail, item.steps[0]?.name.toString(),
                            ""
                        )
                    }
                }
            }

            //binding.tvAuthor.text = item?.author?.name
        }

        override fun onClick(v: View?) {
            val item = data[adapterPosition]
            mClickItem?.onItemClick(
                v, Data(
                    author = Data.Author(
                        avatar = Strings.EMPTY,
                        id = item.author,
                        name = null
                    ),
                    category = item.category,
                    claps = item.claps,
                    cookTime = item.cookTime,
                    dateCreate = item.dateCreate,
                    description = item.description,
                    hearts = item.hearts,
                    id = item.id,
                    image = item.image,
                    ingredients = item.ingredients,
                    likes = item.likes,
                    origin = item.origin,
                    serves = item.serves,
                    steps = item.steps,
                    title = item.title,
                    v = item.v
                ), adapterPosition
            )
        }
    }

    interface SetOnItemClick {
        fun onItemClick(view: View?, item: Data, position: Int)
    }
}