package com.sweak.teacherhelper.features.note

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.sweak.teacherhelper.R
import com.sweak.teacherhelper.databinding.ActivityAddEditNoteBinding

class AddEditNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareActionBar()
        prepareForEditingIfNeeded()
    }

    private fun prepareActionBar() {
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        title = if (intent.hasExtra(EXTRA_ID)) {
            getString(R.string.edit_note)
        } else {
            getString(R.string.add_note)
        }
    }

    private fun prepareForEditingIfNeeded() {
        if (intent.hasExtra(EXTRA_ID)) {
            binding.textInputTitle.editText!!.setText(intent.getStringExtra(EXTRA_TITLE))
            binding.textInputDescription.editText!!.setText(intent.getStringExtra(EXTRA_DESCRIPTION))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.save -> {
            saveNote(); true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun saveNote() {
        val title: String = binding.textInputTitle.editText!!.text.toString().trim()
        val description: String = binding.textInputDescription.editText!!.text.toString().trim()

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(
                this,
                getString(R.string.insert_title_and_description),
                Toast.LENGTH_LONG
            ).show()
            return
        }

        val data = Intent()
        data.putExtra(EXTRA_TITLE, title)
        data.putExtra(EXTRA_DESCRIPTION, description)

        val id: Int = intent.getIntExtra(EXTRA_ID, -1)
        if (id != -1) {
            data.putExtra(EXTRA_ID, id)
        }

        setResult(RESULT_OK, data)
        finish()
    }

    companion object {
        const val EXTRA_ID: String =
            "com.sweak.teacherhelper.features.note.AddEditNoteActivity.EXTRA_ID"
        const val EXTRA_TITLE: String =
            "com.sweak.teacherhelper.features.note.AddEditNoteActivity.EXTRA_TITLE"
        const val EXTRA_DESCRIPTION: String =
            "com.sweak.teacherhelper.features.note.AddEditNoteActivity.EXTRA_DESCRIPTION"
    }
}