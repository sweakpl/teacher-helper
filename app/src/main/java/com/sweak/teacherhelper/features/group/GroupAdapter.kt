package com.sweak.teacherhelper.features.group

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sweak.teacherhelper.data.database.entity.Group
import com.sweak.teacherhelper.databinding.GroupItemBinding

class GroupAdapter(
    private var optionsMenuClickListener: OptionsMenuClickListener,
    private var itemClickListener: ItemClickListener
) : ListAdapter<Group, GroupAdapter.GroupHolder>(DIFF_CALLBACK) {

    class GroupHolder(val binding: GroupItemBinding) : RecyclerView.ViewHolder(binding.root)

    interface OptionsMenuClickListener {
        fun onOptionsMenuClicked(position: Int)
    }

    interface ItemClickListener {
        fun onItemClickListener(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupHolder {
        val binding: GroupItemBinding = GroupItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return GroupHolder(binding)
    }

    override fun onBindViewHolder(holder: GroupHolder, position: Int) {
        with (holder) {
            with (getItem(holder.absoluteAdapterPosition)) {
                binding.textViewGroupName.text = this.name

                binding.textViewGroupOptions.setOnClickListener {
                    optionsMenuClickListener.onOptionsMenuClicked(holder.absoluteAdapterPosition)
                }

                binding.root.setOnClickListener {
                    itemClickListener.onItemClickListener(holder.absoluteAdapterPosition)
                }
            }
        }
    }

    fun getGroupAt(position: Int): Group = getItem(position)

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Group> =
            object : DiffUtil.ItemCallback<Group>() {
                override fun areItemsTheSame(oldItem: Group, newItem: Group): Boolean {
                    return oldItem.id == newItem.id
                }
                override fun areContentsTheSame(oldItem: Group, newItem: Group): Boolean {
                    return oldItem.name == newItem.name
                }
            }
    }
}