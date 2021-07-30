package com.sweak.teacherhelper.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sweak.teacherhelper.R
import com.sweak.teacherhelper.databinding.ActivityStudentControlBinding
import com.sweak.teacherhelper.ui.adapter.recyclerview.StudentControlAdapter
import com.sweak.teacherhelper.ui.fragment.GroupFragment
import com.sweak.teacherhelper.ui.util.StudentControlActivityBuffer
import com.sweak.teacherhelper.ui.viewmodel.StudentControlViewModel
import com.sweak.teacherhelper.ui.viewmodel.StudentControlViewModelFactory

class StudentControlActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentControlBinding
    private lateinit var studentControlViewModel: StudentControlViewModel
    private lateinit var studentControlAdapter: StudentControlAdapter
    private lateinit var studentControlActivityBuffer: StudentControlActivityBuffer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStudentControlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        studentControlViewModel = ViewModelProvider(this,
            StudentControlViewModelFactory(application,
                intent.getIntExtra(GroupFragment.EXTRA_GROUP_ID, -1)))
            .get(StudentControlViewModel::class.java)

        studentControlActivityBuffer = StudentControlActivityBuffer(this)

        prepareActionBar()
        prepareStudentControlRecyclerView()
        setViewModelDataObserver()
    }

    private fun prepareActionBar() {
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        val groupName: String? = intent.getStringExtra(GroupFragment.EXTRA_GROUP_NAME)
        title = groupName
    }

    private fun prepareStudentControlRecyclerView() {
        binding.recyclerViewStudentControl.layoutManager = LinearLayoutManager(this)

        studentControlAdapter = StudentControlAdapter(this,
            object : StudentControlAdapter.CheckboxCheckedListener {
                override fun onChecked(isChecked: Boolean, position: Int) {
                    studentControlActivityBuffer.handleCheckboxSelection(
                        isChecked, studentControlAdapter.getStudentAt(position).id)
                }
            },
            object : StudentControlAdapter.SpinnerItemSelectedListener {
                override fun onSelected
                            (parent: AdapterView<*>?, adapterViewPosition: Int, recyclerViewPosition: Int) {
                    if (parent != null) {
                        studentControlActivityBuffer.handleSpinnerSelection(
                            parent.getItemAtPosition(adapterViewPosition).toString(),
                            studentControlAdapter.getStudentAt(recyclerViewPosition).id)
                    }
                }

            })

        binding.recyclerViewStudentControl.adapter = studentControlAdapter
    }

    private fun setViewModelDataObserver() {
        studentControlViewModel.allStudents.observe(this, { students ->
            studentControlAdapter.setStudents(students)
        })
        studentControlViewModel.missingKits.observe(this, { missingKits ->
            studentControlAdapter.setMissingKits(missingKits)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.save -> {
            saveActivitiesIfPossibleAndFinish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun saveActivitiesIfPossibleAndFinish() {
        if (studentControlActivityBuffer.flush()) {
            Toast.makeText(this, getString(R.string.activities_saved), Toast.LENGTH_SHORT)
                .show()
            finish()
        }
        else
            Toast.makeText(this, getString(R.string.nothing_to_save), Toast.LENGTH_SHORT)
                .show()
    }
}
