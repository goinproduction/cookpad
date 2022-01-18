package com.paulbaker.cookpad.feature.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paulbaker.cookpad.data.datasource.local.Data
import com.paulbaker.cookpad.data.datasource.remote.RecipesResponse
import com.paulbaker.cookpad.databinding.ItemViewPostStepBinding

class StepViewPostAdapter(
    val context: Context,
    val data: MutableList<Data.Step> = mutableListOf(),
    val mClickItem: SetOnItemClick? = null
) :
    RecyclerView.Adapter<StepViewPostAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemViewPostStepBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bindData(item, position)
    }

    override fun getItemCount(): Int {
        return if (data.isNullOrEmpty()) 0 else data.size
    }

    inner class ViewHolder(val binding: ItemViewPostStepBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        fun bindData(item: Data.Step?, position: Int) {
            binding.tvCount.text = (position + 1).toString()
            item?.picture.let {
                binding.imgStep.setImageBitmap(
                    com.paulbaker.library.core.extension.Utils.decodeBase64ToBitMap(
                        item?.picture
                    )
                )
            }
            binding.tvStep.text = item?.name
        }

        override fun onClick(v: View?) {
            mClickItem?.onItemClick(v, data[adapterPosition], adapterPosition)
        }
    }

    interface SetOnItemClick {
        fun onItemClick(view: View?, item: Data.Step, position: Int)
    }
}