package com.sweak.teacherhelper.ui.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sweak.teacherhelper.R
import com.sweak.teacherhelper.database.entity.Student
import com.sweak.teacherhelper.databinding.ActivityStudentBinding
import com.sweak.teacherhelper.ui.adapter.recyclerview.StudentAdapter
import com.sweak.teacherhelper.ui.fragment.GroupFragment
import com.sweak.teacherhelper.ui.viewmodel.StudentViewModel
import com.sweak.teacherhelper.ui.viewmodel.StudentViewModelFactory

class StudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentBinding
    private lateinit var studentViewModel: StudentViewModel
    private lateinit var studentAdapter: StudentAdapter

    private val getNewStudent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        var toastMessage: String = getString(R.string.student_not_saved)

        if (result.resultCode == Activity.RESULT_OK) {
            val firstName: String? = result.data?.getStringExtra(AddEditStudentActivity.EXTRA_FIRST_NAME)
            val lastName: String? = result.data?.getStringExtra(AddEditStudentActivity.EXTRA_LAST_NAME)
            val className: String? = result.data?.getStringExtra(AddEditStudentActivity.EXTRA_CLASS_NAME)
            val groupId: Int? = result.data?.getIntExtra(AddEditStudentActivity.EXTRA_GROUP_ID, -1)

            if (firstName != null && lastName != null && className != null && groupId != null) {
                studentViewModel.insert(Student(firstName, lastName, className, groupId))

                toastMessage = getString(R.string.student_saved)
            }
        }

        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
    }

    private val getEditedStudent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        var toastMessage: String = getString(R.string.student_not_edited)

        if (result.resultCode == Activity.RESULT_OK) {
            val id: Int? = result.data?.getIntExtra(AddEditStudentActivity.EXTRA_ID, -1)
            val firstName: String? = result.data?.getStringExtra(AddEditStudentActivity.EXTRA_FIRST_NAME)
            val lastName: String? = result.data?.getStringExtra(AddEditStudentActivity.EXTRA_LAST_NAME)
            val className: String? = result.data?.getStringExtra(AddEditStudentActivity.EXTRA_CLASS_NAME)
            val groupId: Int? = result.data?.getIntExtra(AddEditStudentActivity.EXTRA_GROUP_ID, -1)

            if (id != null && firstName != null && lastName != null && className != null && groupId != null) {
                if (id != -1) {
                    val student = Student(firstName, lastName, className, groupId)
                    student.id = id
                    studentViewModel.update(student)

                    toastMessage = getString(R.string.student_updated)
                }
                else
                    toastMessage = getString(R.string.cant_update_student)
            }
        }

        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        studentViewModel = ViewModelProvider(this,
            StudentViewModelFactory(application,
                intent.getIntExtra(GroupFragment.EXTRA_GROUP_ID, -1)))
            .get(StudentViewModel::class.java)

        prepareActionBar()
        prepareStudentRecyclerView()
        prepareAddEditStudentButton()
        setViewModelDataObserver()
    }

    private fun prepareActionBar() {
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        val groupName: String? = intent.getStringExtra(GroupFragment.EXTRA_GROUP_NAME)
        title = groupName
    }

    private fun prepareStudentRecyclerView() {
        binding.recyclerViewStudents.layoutManager = LinearLayoutManager(this)

        studentAdapter = StudentAdapter(this,
            object : StudentAdapter.ItemClickListener {
            override fun onItemClickListener(position: Int) {
                showStudentActivities(position)
            }
        },
            object : StudentAdapter.OptionsMenuClickListener {
            override fun onOptionsMenuClicked(position: Int) {
                showOptionsMenu(position)
            }
        })

        binding.recyclerViewStudents.adapter = studentAdapter
    }

    private fun showStudentActivities(position: Int) {
        val intent = Intent(this, StudentActivitiesActivity::class.java)
        intent.putExtra(EXTRA_STUDENT_ID, studentAdapter.getStudentAt(position).id)
        intent.putExtra(EXTRA_STUDENT_FIRST_NAME, studentAdapter.getStudentAt(position).firstName)
        intent.putExtra(EXTRA_STUDENT_LAST_NAME, studentAdapter.getStudentAt(position).lastName)
        startActivity(intent)
    }

    private fun showOptionsMenu(position: Int) {
        val popupMenu = PopupMenu(this,
            binding.recyclerViewStudents[position].findViewById(R.id.text_view_student_options))
        popupMenu.inflate(R.menu.edit_delete_menu)

        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.delete -> {
                        AlertDialog.Builder(this@StudentActivity)
                            .setTitle(getString(R.string.delete))
                            .setMessage(getString(R.string.all_data_will_be_lost))
                            .setPositiveButton(android.R.string.ok) { _, _ ->
                                studentViewModel.delete(studentAdapter.getStudentAt(position))
                            }
                            .show()
                        return true
                    }
                    R.id.edit -> {
                        val student: Student = studentAdapter.getStudentAt(position)
                        val intent = Intent(this@StudentActivity, AddEditStudentActivity::class.java)
                        intent.putExtra(AddEditStudentActivity.EXTRA_ID, student.id)
                        intent.putExtra(AddEditStudentActivity.EXTRA_FIRST_NAME, student.firstName)
                        intent.putExtra(AddEditStudentActivity.EXTRA_LAST_NAME, student.lastName)
                        intent.putExtra(AddEditStudentActivity.EXTRA_CLASS_NAME, student.className)
                        intent.putExtra(AddEditStudentActivity.EXTRA_GROUP_ID, student.groupId)
                        getEditedStudent.launch(intent)
                        return true
                    }
                }
                return false
            }
        })

        popupMenu.show()
    }

    private fun prepareAddEditStudentButton() {
        binding.buttonAddStudent.setOnClickListener {
            val intent = Intent(this, AddEditStudentActivity::class.java)
            intent.putExtra(
                AddEditStudentActivity.EXTRA_GROUP_ID,
                this.intent.getIntExtra(GroupFragment.EXTRA_GROUP_ID, -1))
            getNewStudent.launch(intent)
        }
    }

    private fun setViewModelDataObserver() {
        studentViewModel.allStudents.observe(this, { students ->
            studentAdapter.submitList(students)

            binding.textViewEmptyIndicator.visibility =
                if (students.isEmpty()) View.VISIBLE else View.GONE
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.control_session_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.start_control -> {
            if (studentViewModel.allStudents.value!!.isNotEmpty())
                startControlSession()
            else
                Toast.makeText(this,
                    getString(R.string.cant_start_control), Toast.LENGTH_SHORT)
                    .show()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun startControlSession() {
        val intent = Intent(this, StudentControlActivity::class.java)
        intent.putExtra(GroupFragment.EXTRA_GROUP_ID,
            this.intent.getIntExtra(GroupFragment.EXTRA_GROUP_ID, -1))
        intent.putExtra(GroupFragment.EXTRA_GROUP_NAME,
            this.intent.getStringExtra(GroupFragment.EXTRA_GROUP_NAME))

        startActivity(intent)
    }

    companion object {
        const val EXTRA_STUDENT_ID =
            "com.sweak.teacherhelper.ui.activity.StudentActivity.EXTRA_STUDENT_ID"
        const val EXTRA_STUDENT_FIRST_NAME =
            "com.sweak.teacherhelper.ui.activity.StudentActivity.EXTRA_STUDENT_FIRST_NAME"
        const val EXTRA_STUDENT_LAST_NAME =
            "com.sweak.teacherhelper.ui.activity.StudentActivity.EXTRA_STUDENT_LAST_NAME"
    }
}