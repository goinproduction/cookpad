package com.paulbaker.cookpad.feature.creation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.paulbaker.cookpad.R
import com.paulbaker.cookpad.data.datasource.local.CreateRecipesModel
import com.paulbaker.cookpad.data.datasource.local.StepModel
import com.paulbaker.cookpad.databinding.ItemCreateStepBinding
import java.util.*

class AddStepAdapter(
    val context: Context,
    val data: MutableList<CreateRecipesModel.Step> = mutableListOf(),
    val mClickItem: AddStepAdapter.SetOnItemClick? = null
) :
    RecyclerView.Adapter<AddStepAdapter.ViewHolder>() {

    init {
        data.addAll(
            listOf(
                CreateRecipesModel.Step(
                    name = "",
                    picture = null
                ),
                CreateRecipesModel.Step(
                    name = "",
                    picture = null
                )
            )
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCreateStepBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bindData(item, position)
    }

    override fun getItemCount(): Int {
        return if (data.isNullOrEmpty()) 0 else data.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun onMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(data, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(data, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    fun swipe(position: Int, direction: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class ViewHolder(val binding: ItemCreateStepBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener,
        View.OnCreateContextMenuListener {
        init {
            itemView.setOnClickListener(this)
            binding.containerAddImageStep.setOnClickListener(this)
            binding.btnOptionsStep.setOnClickListener(this)
            binding.btnOptionsStep.setOnCreateContextMenuListener(this)
            binding.edtInputStep.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    data[adapterPosition].name = binding.edtInputStep.text.toString()
                }
            })
        }

        fun bindData(item: CreateRecipesModel.Step?, position: Int) {
            binding.tvCount.text = (position + 1).toString()
            item?.picture?.let {
                binding.btnAddImageStep.setImageBitmap(
                    com.paulbaker.library.core.extension.Utils.decodeBase64ToBitMap(data[position].picture)
                )
                binding.imgHolder.visibility = View.GONE
            }

            if (position == 0)
                if (data[position].name?.isEmpty() == true)
                    binding.edtInputStep.hint = "Trộn bột và nước đến khi đặc lại"
                else
                    binding.edtInputStep.setText(item?.name)
            else {
                if (data[position].name?.isEmpty() == true)
                    binding.edtInputStep.hint =
                        "Đậy kín hỗn hợp lại và để ở nhiệt độ phòng trong vòng 24-36 tiếng"
                else
                    binding.edtInputStep.setText(item?.name)
            }
        }

        @RequiresApi(Build.VERSION_CODES.N)
        override fun onClick(v: View?) {
            when (v?.id) {
                R.id.btnOptionsStep -> {
                    v.showContextMenu(v.x, v.y)
                }
                R.id.containerAddImageStep -> {
                    mClickItem?.onItemClick(v, data[adapterPosition], adapterPosition)
                }
                else -> {
                    mClickItem?.forceHideKeyBroad()
                }
            }
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            if (v != null) {
                menu?.add("Thêm bước")?.setOnMenuItemClickListener {
                    data.add(
                        CreateRecipesModel.Step(
                            name = "",
                            picture = null
                        )
                    )
                    notifyItemInserted(data.size - 1)
                    notifyDataSetChanged()
                    return@setOnMenuItemClickListener true
                }
                menu?.add("Xóa bước này")?.setOnMenuItemClickListener {
                    data.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                    return@setOnMenuItemClickListener true
                }
            }
        }
    }

    interface SetOnItemClick {
        fun onItemClick(view: View?, item: CreateRecipesModel.Step?, position: Int)
        fun forceHideKeyBroad()
    }
}