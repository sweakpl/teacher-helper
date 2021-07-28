package com.sweak.teacherhelper.ui.activity

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.sweak.teacherhelper.R
import com.sweak.teacherhelper.databinding.ActivityAddEditScheduleBinding
import java.util.*

class AddEditScheduleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditScheduleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddEditScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareActionBar()
        prepareForEditingIfNeeded()
        prepareTimePickers()
    }

    private fun prepareActionBar() {
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        title = if (intent.hasExtra(EXTRA_ID)) {
            getString(R.string.edit_activity)
        } else {
            getString(R.string.add_activity)
        }
    }

    private fun prepareForEditingIfNeeded() {
        if (intent.hasExtra(EXTRA_ID)) {
            binding.editTextActivity.setText(intent.getStringExtra(EXTRA_ACTIVITY))
            binding.editTextTimeStart.setText(intent.getStringExtra(EXTRA_TIME_START))
            binding.editTextTimeEnd.setText(intent.getStringExtra(EXTRA_TIME_END))
        }
    }

    private fun prepareTimePickers() {
        binding.editTextTimeStart.inputType = InputType.TYPE_NULL
        binding.editTextTimeEnd.inputType = InputType.TYPE_NULL

        val calendar = Calendar.getInstance()

        val pickerTimeStart: TimePickerDialog = object : TimePickerDialog(this,
            OnTimeSetListener { _, hourOfDay, minute ->
                binding.editTextTimeStart.setText(getTimeString(hourOfDay, minute)) },
            calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true) {}

        binding.editTextTimeStart.setOnClickListener {
            pickerTimeStart.show()
        }

        val pickerTimeEnd: TimePickerDialog = object : TimePickerDialog(this,
            OnTimeSetListener { _, hourOfDay, minute ->
                binding.editTextTimeEnd.setText(getTimeString(hourOfDay, minute)) },
            calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true) {}

        binding.editTextTimeEnd.setOnClickListener {
            pickerTimeEnd.show()
        }
    }

    private fun getTimeString(hour: Int, minute: Int): String =
        if (minute < 10) "$hour:0$minute" else "$hour:$minute"

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.save -> {saveActivity(); true}
        else -> super.onOptionsItemSelected(item)
    }

    private fun saveActivity() {
        val activity: String = binding.editTextActivity.text.toString()
        val timeStart: String = binding.editTextTimeStart.text.toString()
        val timeEnd: String = binding.editTextTimeEnd.text.toString()

        if (activity.trim().isEmpty() || timeStart.isEmpty() || timeEnd.isEmpty()) {
            Toast.makeText(this, getString(R.string.insert_activity_and_time), Toast.LENGTH_LONG).show()
            return
        }

        val data = Intent()
        data.putExtra(EXTRA_ACTIVITY, activity)
        data.putExtra(EXTRA_TIME_START, timeStart)
        data.putExtra(EXTRA_TIME_END, timeEnd)

        val id: Int = intent.getIntExtra(EXTRA_ID, -1)
        if (id != -1) {
            data.putExtra(EXTRA_ID, id)
        }

        setResult(RESULT_OK, data)
        finish()
    }

    companion object {
        const val EXTRA_ID: String =
            "com.sweak.teacherhelper.ui.activity.AddEditScheduleActivity.EXTRA_ID"
        const val EXTRA_ACTIVITY: String =
            "com.sweak.teacherhelper.ui.activity.AddEditScheduleActivity.EXTRA_ACTIVITY"
        const val EXTRA_TIME_START: String =
            "com.sweak.teacherhelper.ui.activity.AddEditScheduleActivity.EXTRA_TIME_START"
        const val EXTRA_TIME_END: String =
            "com.sweak.teacherhelper.ui.activity.AddEditScheduleActivity.EXTRA_TIME_END"
    }
}