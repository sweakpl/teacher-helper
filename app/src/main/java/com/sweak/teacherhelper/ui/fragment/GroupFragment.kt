package com.sweak.teacherhelper.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sweak.teacherhelper.R
import com.sweak.teacherhelper.ui.activity.StudentActivity
import com.sweak.teacherhelper.database.entity.Group
import com.sweak.teacherhelper.databinding.FragmentGroupBinding
import com.sweak.teacherhelper.ui.activity.AddEditGroupActivity
import com.sweak.teacherhelper.ui.adapter.recyclerview.GroupAdapter
import com.sweak.teacherhelper.ui.viewmodel.GroupViewModel
import com.sweak.teacherhelper.ui.viewmodel.GroupViewModelFactory

class GroupFragment : Fragment() {

    private var _binding: FragmentGroupBinding? = null
    private lateinit var groupViewModel: GroupViewModel
    private lateinit var groupAdapter: GroupAdapter
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val getNewGroup = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        var toastMessage: String = getString(R.string.group_not_saved)

        if (result.resultCode == Activity.RESULT_OK) {
            val name: String? = result.data?.getStringExtra(AddEditGroupActivity.EXTRA_NAME)

            if (name != null) {
                groupViewModel.insert(Group(name))
                toastMessage = getString(R.string.group_saved)
            }
        }

        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
    }

    private val getEditedGroup = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        var toastMessage: String = getString(R.string.group_not_edited)

        if (result.resultCode == Activity.RESULT_OK) {
            val id: Int? = result.data?.getIntExtra(AddEditGroupActivity.EXTRA_ID, -1)
            val name: String? = result.data?.getStringExtra(AddEditGroupActivity.EXTRA_NAME)

            if (id != null && name != null) {
                if (id != -1) {
                    val group = Group(name)
                    group.id = id
                    groupViewModel.update(group)

                    toastMessage = getString(R.string.group_updated)
                }
                else
                    toastMessage = getString(R.string.cant_update_group)
            }
        }

        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        groupViewModel = ViewModelProvider(this,
            GroupViewModelFactory(requireActivity().application))
            .get(GroupViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentGroupBinding.inflate(inflater, container, false)
        val root = binding.root

        prepareGroupRecyclerView()
        prepareAddEditGroupButton()
        setViewModelDataObserver()

        return root
    }

    private fun prepareGroupRecyclerView() {
        binding.recyclerViewGroups.layoutManager = LinearLayoutManager(context)

        groupAdapter = GroupAdapter(object : GroupAdapter.OptionsMenuClickListener {
            override fun onOptionsMenuClicked(position: Int) {
                showOptionsMenu(position)
            }
        },
        object : GroupAdapter.ItemClickListener {
            override fun onItemClickListener(position: Int) {
                showGroupStudents(groupAdapter.getGroupAt(position).id,
                    groupAdapter.getGroupAt(position).name)
            }
        })

        binding.recyclerViewGroups.adapter = groupAdapter
    }

    private fun showGroupStudents(groupId: Int, groupName: String) {
        val intent = Intent(context, StudentActivity::class.java)
        intent.putExtra(EXTRA_GROUP_ID, groupId)
        intent.putExtra(EXTRA_GROUP_NAME, groupName)
        startActivity(intent)
    }

    private fun showOptionsMenu(position: Int) {
        val popupMenu = PopupMenu(requireContext(),
            binding.recyclerViewGroups[position].findViewById(R.id.text_view_group_options))
        popupMenu.inflate(R.menu.edit_delete_menu)

        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.delete -> {
                        AlertDialog.Builder(this@GroupFragment.requireContext())
                            .setTitle(getString(R.string.delete))
                            .setMessage(getString(R.string.all_data_will_be_lost))
                            .setPositiveButton(android.R.string.ok) { _, _ ->
                                groupViewModel.delete(groupAdapter.getGroupAt(position))
                            }
                            .show()
                        return true
                    }
                    R.id.edit -> {
                        val group: Group = groupAdapter.getGroupAt(position)
                        val intent = Intent(context, AddEditGroupActivity::class.java)
                        intent.putExtra(AddEditGroupActivity.EXTRA_ID, group.id)
                        intent.putExtra(AddEditGroupActivity.EXTRA_NAME, group.name)
                        getEditedGroup.launch(intent)
                        return true
                    }
                }
                return false
            }
        })

        popupMenu.show()
    }

    private fun prepareAddEditGroupButton() {
        binding.buttonAddGroup.setOnClickListener {
            val intent = Intent(context, AddEditGroupActivity::class.java)
            getNewGroup.launch(intent)
        }
    }

    private fun setViewModelDataObserver() {
        groupViewModel.allGroups.observe(viewLifecycleOwner, { groups ->
            groupAdapter.submitList(groups)

            binding.textViewEmptyIndicator.visibility =
                if (groups.isEmpty()) View.VISIBLE else View.GONE
        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val EXTRA_GROUP_ID: String =
            "com.sweak.teacherhelper.ui.fragment.GroupFragment.EXTRA_GROUP_ID"
        const val EXTRA_GROUP_NAME: String =
            "com.sweak.teacherhelper.ui.fragment.GroupFragment.EXTRA_GROUP_NAME"

        @JvmStatic
        fun newInstance() = GroupFragment()
    }
}