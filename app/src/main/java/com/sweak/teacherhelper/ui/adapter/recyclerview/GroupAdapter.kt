package com.sweak.teacherhelper.ui.adapter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sweak.teacherhelper.database.entity.Group
import com.sweak.teacherhelper.databinding.GroupItemBinding

class GroupAdapter(
    private var optionsMenuClickListener: OptionsMenuClickListener,
    private var itemClickListener: ItemClickListener
) : RecyclerView.Adapter<GroupAdapter.GroupHolder>() {

    private var groups: List<Group> = ArrayList()

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
            with (groups[position]) {
                binding.textViewGroupName.text = this.name

                binding.textViewGroupOptions.setOnClickListener {
                    optionsMenuClickListener.onOptionsMenuClicked(position)
                }

                binding.root.setOnClickListener {
                    itemClickListener.onItemClickListener(position)
                }
            }
        }
    }

    override fun getItemCount(): Int = groups.size

    fun setGroups(groups: List<Group>) {
        this.groups = groups
        notifyDataSetChanged()
    }

    fun getGroupAt(position: Int): Group = groups[position]
}