package com.sweak.teacherhelper.features.addedit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.sweak.teacherhelper.R
import com.sweak.teacherhelper.databinding.ActivityAddEditStudentBinding

class AddEditStudentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEditStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddEditStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareActionBar()
        prepareForEditingIfNeeded()
    }

    private fun prepareActionBar() {
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        title = if (intent.hasExtra(EXTRA_ID)) {
            getString(R.string.edit_student)
        } else {
            getString(R.string.add_student)
        }
    }

    private fun prepareForEditingIfNeeded() {
        if (intent.hasExtra(EXTRA_ID)) {
            binding.textInputFirstName.editText!!.setText(intent.getStringExtra(EXTRA_FIRST_NAME))
            binding.textInputLastName.editText!!.setText(intent.getStringExtra(EXTRA_LAST_NAME))
            binding.textInputClassName.editText!!.setText(intent.getStringExtra(EXTRA_CLASS_NAME))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.save -> {saveStudent(); true}
        else -> super.onOptionsItemSelected(item)
    }

    private fun saveStudent() {
        val firstName: String = binding.textInputFirstName.editText!!.text.toString().trim()
        val lastName: String = binding.textInputLastName.editText!!.text.toString().trim()
        val className: String = binding.textInputClassName.editText!!.text.toString().trim()
        val groupId: Int = intent.getIntExtra(EXTRA_GROUP_ID, -1)

        if (firstName.isEmpty() || lastName.isEmpty() || className.isEmpty()) {
            Toast.makeText(this, getString(R.string.insert_full_name_and_class), Toast.LENGTH_LONG).show()
            return
        }

        val data = Intent()
        data.putExtra(EXTRA_FIRST_NAME, firstName)
        data.putExtra(EXTRA_LAST_NAME, lastName)
        data.putExtra(EXTRA_CLASS_NAME, className)
        data.putExtra(EXTRA_GROUP_ID, groupId)

        val id: Int = intent.getIntExtra(EXTRA_ID, -1)
        if (id != -1) {
            data.putExtra(EXTRA_ID, id)
        }

        setResult(RESULT_OK, data)
        finish()
    }

    companion object {
        const val EXTRA_ID: String =
            "com.sweak.teacherhelper.features.addedit.AddEditStudentActivity.EXTRA_ID"
        const val EXTRA_FIRST_NAME: String =
            "com.sweak.teacherhelper.features.addedit.AddEditStudentActivity.EXTRA_FIRST_NAME"
        const val EXTRA_LAST_NAME: String =
            "com.sweak.teacherhelper.features.addedit.AddEditStudentActivity.EXTRA_LAST_NAME"
        const val EXTRA_CLASS_NAME: String =
            "com.sweak.teacherhelper.features.addedit.AddEditStudentActivity.EXTRA_CLASS_NAME"
        const val EXTRA_GROUP_ID: String =
            "com.sweak.teacherhelper.features.addedit.AddEditStudentActivity.EXTRA_GROUP_ID"
    }
}