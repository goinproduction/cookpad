package com.paulbaker.cookpad.feature.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paulbaker.cookpad.databinding.ItemViewPostMaterialBinding

class MaterialViewPostAdapter(
    val context: Context,
    val data: MutableList<String> = mutableListOf(),
    val mClickItem: SetOnItemClick? = null
) :
    RecyclerView.Adapter<MaterialViewPostAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemViewPostMaterialBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bindData(item)
    }

    override fun getItemCount(): Int {
        return if (data.isNullOrEmpty()) 0 else data.size
    }

    inner class ViewHolder(val binding: ItemViewPostMaterialBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        fun bindData(item: String?) {
            binding.tvMaterial.text = item
        }

        override fun onClick(v: View?) {
            mClickItem?.onItemClick(v, data?.get(adapterPosition), adapterPosition)
        }
    }

    interface SetOnItemClick {
        fun onItemClick(view: View?, item: String?, position: Int)
    }
}