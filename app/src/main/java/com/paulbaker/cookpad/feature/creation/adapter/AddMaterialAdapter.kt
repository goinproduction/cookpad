package com.paulbaker.cookpad.feature.creation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.paulbaker.cookpad.R
import com.paulbaker.cookpad.databinding.ItemCreateMaterialBinding
import java.util.*

class AddMaterialAdapter(
    val context: Context,
    val data: MutableList<String> = mutableListOf("", ""),
    val mClickItem: AddMaterialAdapter.SetOnItemClick? = null
) :
    RecyclerView.Adapter<AddMaterialAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCreateMaterialBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bindData(item, position)
    }

    override fun getItemCount(): Int {
        return if (data.isNullOrEmpty()) 0 else data.size
    }


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

    inner class ViewHolder(val binding: ItemCreateMaterialBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener,
        View.OnCreateContextMenuListener {
        init {
            itemView.setOnClickListener(this)
            binding.btnOptionsMaterial.setOnClickListener(this)
            binding.btnOptionsMaterial.setOnCreateContextMenuListener(this)
            binding.edtInputMaterial.addTextChangedListener(object : TextWatcher {
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
                    data[adapterPosition] = binding.edtInputMaterial.text.toString()
                }
            })
        }

        fun bindData(item: String?, position: Int) {
            if (position == 0)
                if (data[position].isEmpty())
                    binding.edtInputMaterial.hint = "250g bột"
                else
                    binding.edtInputMaterial.setText(item)
            else {
                if (data[position].isEmpty())
                    binding.edtInputMaterial.hint = "100ml nước"
                else
                    binding.edtInputMaterial.setText(item)
            }
        }

        override fun onClick(v: View?) {
            when (v?.id) {
                R.id.btnOptionsMaterial -> {
                    v.showContextMenu()
                }
                else ->
                    mClickItem?.onItemClick(v, data[adapterPosition], adapterPosition)
            }
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            if (v != null) {
                menu?.add("Thêm nguyên liệu")?.setOnMenuItemClickListener {
                    data.add("")
                    notifyItemInserted(data.size - 1)
                    notifyDataSetChanged()
                    return@setOnMenuItemClickListener true
                }
                menu?.add("Xóa nguyên liệu này")?.setOnMenuItemClickListener {
                    if (data.size > 2) {
                        data.removeAt(adapterPosition)
                        notifyItemRemoved(adapterPosition)
                    } else
                        Toast.makeText(
                            context,
                            "Không thể xóa, một món ăn phải có ít nhất 2 nguyên liệu trở lên",
                            Toast.LENGTH_SHORT
                        ).show()
                    return@setOnMenuItemClickListener true
                }
            }
        }
    }

    interface SetOnItemClick {
        fun onItemClick(view: View?, item: String?, position: Int)
    }

}