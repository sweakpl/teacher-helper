package com.sweak.teacherhelper.features.studentcontrol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sweak.teacherhelper.R
import com.sweak.teacherhelper.databinding.ActivityStudentControlBinding
import com.sweak.teacherhelper.features.group.GroupFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StudentControlActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentControlBinding
    private val studentControlViewModel: StudentControlViewModel by viewModels()
    @Inject lateinit var studentControlActivityBuffer: StudentControlActivityBuffer
    private lateinit var studentControlAdapter: StudentControlAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStudentControlBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                        isChecked, studentControlAdapter.getStudentAt(position).id
                    )
                }
            },
            object : StudentControlAdapter.SpinnerItemSelectedListener {
                override fun onSelected(
                    parent: AdapterView<*>?,
                    adapterViewPosition: Int,
                    recyclerViewPosition: Int
                ) {
                    if (parent != null) {
                        studentControlActivityBuffer.handleSpinnerSelection(
                            parent.getItemAtPosition(adapterViewPosition).toString(),
                            studentControlAdapter.getStudentAt(recyclerViewPosition).id
                        )
                    }
                }

            })

        binding.recyclerViewStudentControl.adapter = studentControlAdapter
    }

    private fun setViewModelDataObserver() {
        studentControlViewModel.initializeAllStudentsAndMissingKits(
            intent.getIntExtra(GroupFragment.EXTRA_GROUP_ID, -1)
        )

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
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
        } else
            Toast.makeText(this, getString(R.string.nothing_to_save), Toast.LENGTH_SHORT)
                .show()
    }
}
