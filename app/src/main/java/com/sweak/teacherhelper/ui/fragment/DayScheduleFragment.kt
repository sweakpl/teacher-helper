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
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sweak.teacherhelper.R
import com.sweak.teacherhelper.ui.activity.AddEditScheduleActivity
import com.sweak.teacherhelper.database.entity.Schedule
import com.sweak.teacherhelper.databinding.FragmentDayScheduleBinding
import com.sweak.teacherhelper.ui.adapter.recyclerview.ScheduleAdapter
import com.sweak.teacherhelper.ui.viewmodel.ScheduleViewModel
import com.sweak.teacherhelper.ui.viewmodel.ScheduleViewModelFactory

class DayScheduleFragment : Fragment() {

    private var _binding: FragmentDayScheduleBinding? = null
    private lateinit var day: String
    private lateinit var scheduleViewModel: ScheduleViewModel
    private lateinit var scheduleAdapter: ScheduleAdapter
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val getNewSchedule = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        var toastMessage: String = getString(R.string.schedule_not_saved)

        if (result.resultCode == Activity.RESULT_OK) {
            val activity: String? = result.data?.getStringExtra(AddEditScheduleActivity.EXTRA_ACTIVITY)
            val timeStart: String? = result.data?.getStringExtra(AddEditScheduleActivity.EXTRA_TIME_START)
            val timeEnd: String? = result.data?.getStringExtra(AddEditScheduleActivity.EXTRA_TIME_END)

            if (activity != null && timeStart != null && timeEnd != null) {
                scheduleViewModel.insert(Schedule(day, activity, timeStart, timeEnd))

                toastMessage = getString(R.string.schedule_saved)
            }
        }

        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
    }

    private val getEditedSchedule = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        var toastMessage: String = getString(R.string.schedule_not_edited)

        if (result.resultCode == Activity.RESULT_OK) {
            val id: Int? = result.data?.getIntExtra(AddEditScheduleActivity.EXTRA_ID, -1)
            val activity: String? = result.data?.getStringExtra(AddEditScheduleActivity.EXTRA_ACTIVITY)
            val timeStart: String? = result.data?.getStringExtra(AddEditScheduleActivity.EXTRA_TIME_START)
            val timeEnd: String? = result.data?.getStringExtra(AddEditScheduleActivity.EXTRA_TIME_END)

            if (id != null && activity != null && timeStart != null && timeEnd != null) {
                if (id != -1) {
                    val schedule = Schedule(day, activity, timeStart, timeEnd)
                    schedule.id = id
                    scheduleViewModel.update(schedule)

                    toastMessage = getString(R.string.schedule_updated)
                }
                else
                    toastMessage = getString(R.string.cant_update_schedule)
            }
        }

        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        day = requireArguments()[FRAGMENT_ARGUMENT_DAY].toString()

        scheduleViewModel = ViewModelProvider(this,
            ScheduleViewModelFactory(requireActivity().application, day))
            .get(ScheduleViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  FragmentDayScheduleBinding.inflate(inflater, container, false)
        val root = binding.root

        prepareScheduleRecyclerView()
        prepareAddEditScheduleButton()
        setViewModelDataObserver()

        return root
    }

    private fun prepareScheduleRecyclerView() {
        binding.recyclerViewSchedule.layoutManager = LinearLayoutManager(context)

        scheduleAdapter = ScheduleAdapter(requireContext(),
            object : ScheduleAdapter.OptionsMenuClickListener {
            override fun onOptionsMenuClicked(position: Int) {
                showOptionsMenu(position)
            }
        })
        binding.recyclerViewSchedule.adapter = scheduleAdapter
    }

    private fun showOptionsMenu(position: Int) {
        val popupMenu = PopupMenu(requireContext(),
            binding.recyclerViewSchedule[position].findViewById(R.id.text_view_schedule_options))
        popupMenu.inflate(R.menu.edit_delete_options_menu)

        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.delete -> {
                        scheduleViewModel.delete(scheduleAdapter.getScheduleAt(position))
                        return true
                    }
                    R.id.edit -> {
                        val schedule: Schedule = scheduleAdapter.getScheduleAt(position)
                        val intent = Intent(context, AddEditScheduleActivity::class.java)
                        intent.putExtra(AddEditScheduleActivity.EXTRA_ID, schedule.id)
                        intent.putExtra(AddEditScheduleActivity.EXTRA_ACTIVITY, schedule.activity)
                        intent.putExtra(AddEditScheduleActivity.EXTRA_TIME_START, schedule.timeStart)
                        intent.putExtra(AddEditScheduleActivity.EXTRA_TIME_END, schedule.timeEnd)
                        getEditedSchedule.launch(intent)
                        return true
                    }
                }
                return false
            }
        })

        popupMenu.show()
    }

    private fun prepareAddEditScheduleButton() {
        binding.buttonAddSchedule.setOnClickListener {
            val intent = Intent(context, AddEditScheduleActivity::class.java)
            getNewSchedule.launch(intent)
        }
    }

    private fun setViewModelDataObserver() {
        scheduleViewModel.allSchedule.observe(viewLifecycleOwner, { schedule ->
            scheduleAdapter.setSchedule(schedule)
        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val FRAGMENT_ARGUMENT_DAY: String =
            "com.sweak.teacherhelper.ui.fragment.DayScheduleFragment.FRAGMENT_ARGUMENT_DAY"

        @JvmStatic
        fun newInstance(bundle: Bundle) : DayScheduleFragment{
            val fragment = DayScheduleFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}