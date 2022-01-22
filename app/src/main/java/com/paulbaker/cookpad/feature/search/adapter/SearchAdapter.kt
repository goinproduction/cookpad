package com.paulbaker.cookpad.feature.search.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paulbaker.cookpad.R
import com.paulbaker.cookpad.data.datasource.remote.Data
import com.paulbaker.cookpad.databinding.ItemSearchBinding
import com.paulbaker.library.core.extension.isNotNull
import com.paulbaker.library.core.extension.isValidValue

class SearchAdapter(
    val context: Context,
    val data: MutableList<Data> = mutableListOf(),
    val mClickItem: SetOnItemClick? = null
 ) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
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
    fun setData(data : List<Data>){
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }
        fun bindData(item: Data?){
            binding.tvTitleSearchFood.text = item?.title
            if (item?.author?.avatar?.isNotNull() == true && item.author.avatar.isValidValue()) {
                binding.imageAuthor.setImageBitmap(
                    com.paulbaker.library.core.extension.Utils.decodeBase64ToBitMap(
                        item.author.avatar
                    )
                )
            }
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
                            R.string.format_detail, item.steps[0].name.toString(),
                            item.steps[1].name.toString()
                        )
                    }
                    item.steps.isNotEmpty() -> {
                        binding.tvDetail.text = context.getString(
                            R.string.format_detail, item.steps[0].name.toString(),
                            ""
                        )
                    }
                }
            }

            binding.tvAuthor.text = item?.author?.name
        }

        override fun onClick(v: View?) {
            mClickItem?.onItemClick(v,data[adapterPosition],adapterPosition)
        }
    }

    interface SetOnItemClick {
        fun onItemClick(view:View?, item: Data, position: Int)
    }
}