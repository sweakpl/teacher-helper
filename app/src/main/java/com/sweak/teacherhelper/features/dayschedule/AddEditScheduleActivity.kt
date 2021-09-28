package com.sweak.teacherhelper.features.dayschedule

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
            binding.textInputActivity.editText!!.setText(intent.getStringExtra(EXTRA_ACTIVITY))
            binding.textInputTimeStart.editText!!.setText(intent.getStringExtra(EXTRA_TIME_START))
            binding.textInputTimeEnd.editText!!.setText(intent.getStringExtra(EXTRA_TIME_END))
        }
    }

    private fun prepareTimePickers() {
        binding.textInputTimeStart.editText!!.inputType = InputType.TYPE_NULL
        binding.textInputTimeEnd.editText!!.inputType = InputType.TYPE_NULL

        val calendar = Calendar.getInstance()

        val pickerTimeStart: TimePickerDialog = object : TimePickerDialog(
            this,
            OnTimeSetListener { _, hourOfDay, minute ->
                binding.textInputTimeStart.editText!!.setText(getTimeString(hourOfDay, minute))
            },
            calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true
        ) {}

        binding.textInputTimeStart.editText!!.setOnClickListener {
            pickerTimeStart.show()
        }

        val pickerTimeEnd: TimePickerDialog = object : TimePickerDialog(
            this,
            OnTimeSetListener { _, hourOfDay, minute ->
                binding.textInputTimeEnd.editText!!.setText(getTimeString(hourOfDay, minute))
            },
            calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true
        ) {}

        binding.textInputTimeEnd.editText!!.setOnClickListener {
            pickerTimeEnd.show()
        }
    }

    private fun getTimeString(hour: Int, minute: Int): String =
        if (hour < 10)
            if (minute < 10)
                "0$hour:0$minute"
            else
                "0$hour:$minute"
        else
            if (minute < 10)
                "$hour:0$minute"
            else
                "$hour:$minute"

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.save -> {
            saveActivity(); true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun saveActivity() {
        val activity: String = binding.textInputActivity.editText!!.text.toString().trim()
        val timeStart: String = binding.textInputTimeStart.editText!!.text.toString()
        val timeEnd: String = binding.textInputTimeEnd.editText!!.text.toString()

        if (activity.isEmpty() || timeStart.isEmpty() || timeEnd.isEmpty()) {
            Toast.makeText(this, getString(R.string.insert_activity_and_time), Toast.LENGTH_LONG)
                .show()
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
            "com.sweak.teacherhelper.features.dayschedule.AddEditScheduleActivity.EXTRA_ID"
        const val EXTRA_ACTIVITY: String =
            "com.sweak.teacherhelper.features.dayschedule.AddEditScheduleActivity.EXTRA_ACTIVITY"
        const val EXTRA_TIME_START: String =
            "com.sweak.teacherhelper.features.dayschedule.AddEditScheduleActivity.EXTRA_TIME_START"
        const val EXTRA_TIME_END: String =
            "com.sweak.teacherhelper.features.dayschedule.AddEditScheduleActivity.EXTRA_TIME_END"
    }
}