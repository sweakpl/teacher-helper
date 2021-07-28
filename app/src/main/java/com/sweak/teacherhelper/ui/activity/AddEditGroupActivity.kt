package com.sweak.teacherhelper.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.sweak.teacherhelper.R
import com.sweak.teacherhelper.databinding.ActivityAddEditGroupBinding

class AddEditGroupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditGroupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddEditGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareActionBar()
        prepareForEditingIfNeeded()
    }

    private fun prepareActionBar() {
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        title = if (intent.hasExtra(EXTRA_ID)) {
            getString(R.string.edit_group)
        } else {
            getString(R.string.add_group)
        }
    }

    private fun prepareForEditingIfNeeded() {
        if (intent.hasExtra(EXTRA_ID)) {
            binding.editTextName.setText(intent.getStringExtra(EXTRA_NAME))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.save -> {saveGroup(); true}
        else -> super.onOptionsItemSelected(item)
    }

    private fun saveGroup() {
        val name: String = binding.editTextName.text.toString()

        if (name.trim().isEmpty()) {
            Toast.makeText(this, getString(R.string.insert_name), Toast.LENGTH_LONG).show()
            return
        }

        val data = Intent()
        data.putExtra(EXTRA_NAME, name)

        val id: Int = intent.getIntExtra(EXTRA_ID, -1)
        if (id != -1) {
            data.putExtra(EXTRA_ID, id)
        }

        setResult(RESULT_OK, data)
        finish()
    }

    companion object {
        const val EXTRA_ID: String =
            "com.sweak.teacherhelper.ui.activity.AddEditGroupActivity.EXTRA_ID"
        const val EXTRA_NAME: String =
            "com.sweak.teacherhelper.ui.activity.AddEditGroupActivity.EXTRA_NAME"
    }
}