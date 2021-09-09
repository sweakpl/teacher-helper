package com.sweak.teacherhelper.ui.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sweak.teacherhelper.R
import com.sweak.teacherhelper.adapter.recyclerview.StudentActivitiesAdapter
import com.sweak.teacherhelper.databinding.ActivityStudentActivitiesBinding
import com.sweak.teacherhelper.viewmodel.viewmodel.StudentActivitiesViewModel
import com.sweak.teacherhelper.viewmodel.viewmodel.StudentActivitiesViewModelFactory

class StudentActivitiesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentActivitiesBinding
    private lateinit var studentActivitiesViewModel: StudentActivitiesViewModel
    private lateinit var studentActivitiesAdapter: StudentActivitiesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStudentActivitiesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        studentActivitiesViewModel = ViewModelProvider(this,
            StudentActivitiesViewModelFactory(application,
                intent.getIntExtra(StudentActivity.EXTRA_STUDENT_ID, -1)))
            .get(StudentActivitiesViewModel::class.java)

        prepareActionBar()
        prepareStudentRecyclerView()
        setViewModelDataObserver()
    }

    private fun prepareActionBar() {
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        val firstName: String? = intent.getStringExtra(StudentActivity.EXTRA_STUDENT_FIRST_NAME)
        val lastName: String? = intent.getStringExtra(StudentActivity.EXTRA_STUDENT_LAST_NAME)
        title = "$firstName $lastName"
    }

    private fun prepareStudentRecyclerView() {
        binding.recyclerViewStudentActivities.layoutManager = LinearLayoutManager(this)

        studentActivitiesAdapter = StudentActivitiesAdapter(this,
            object : StudentActivitiesAdapter.OptionsMenuClickListener {
                override fun onOptionsMenuClicked(position: Int) {
                    showOptionsMenu(position)
                }
            })

        binding.recyclerViewStudentActivities.adapter = studentActivitiesAdapter
    }

    private fun showOptionsMenu(position: Int) {
        val menuButtonView = binding.recyclerViewStudentActivities
            .findViewHolderForLayoutPosition(position)?.itemView
            ?.findViewById<TextView>(R.id.text_view_student_activity_options)

        if (menuButtonView == null)
            return
        else {
            val popupMenu = PopupMenu(this, menuButtonView)
            popupMenu.inflate(R.menu.delete_menu)

            popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    when (item?.itemId) {
                        R.id.delete -> {
                            studentActivitiesViewModel.delete(
                                studentActivitiesAdapter.getStudentAt(
                                    position
                                )
                            )
                            return true
                        }
                    }
                    return false
                }
            })

            popupMenu.show()
        }
    }

    private fun setViewModelDataObserver() {
        studentActivitiesViewModel.allStudentActivities.observe(this, { students ->
            studentActivitiesAdapter.submitList(students)

            binding.textViewEmptyIndicator.visibility =
                if (students.isEmpty()) View.VISIBLE else View.GONE
        })
    }
}