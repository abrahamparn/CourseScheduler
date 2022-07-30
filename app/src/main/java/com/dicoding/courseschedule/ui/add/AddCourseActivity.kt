package com.dicoding.courseschedule.ui.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.databinding.ActivityAddCourseBinding
import com.dicoding.courseschedule.ui.list.ListViewModelFactory
import com.dicoding.courseschedule.util.TimePickerFragment
import java.text.SimpleDateFormat
import java.util.*

class AddCourseActivity : AppCompatActivity(),  TimePickerFragment.DialogTimeListener {
    private lateinit var viewModel: AddCourseViewModel
    private lateinit var binding: ActivityAddCourseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.add_course)

        setupModel()
    }

    private fun setupModel() {
        viewModel = ViewModelProvider(
            this,
            ListViewModelFactory.createFactory(this)
        )[AddCourseViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_insert -> {

                viewModel.insertCourse(
                    binding.addNameCourse.text.toString().trim(),
                    binding.addCourseDay.selectedItemPosition,
                    binding.addCourseStart.text.toString().trim(),
                    binding.addCourseEnd.text.toString().trim(),
                    binding.addCourseLecturer.text.toString().trim(),
                    binding.addCourseNote.text.toString().trim()
                )
                onBackPressed()
                true
            }else -> super.onOptionsItemSelected(item)
        }
    }

    fun showTimePickerStartTime(view: View) {
        TimePickerFragment().show(supportFragmentManager, "startPicker")
    }

    fun showTimePickerEndTime(view: View) {
        TimePickerFragment().show(supportFragmentManager, "endPicker")
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }


        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        if (tag == "startPicker") {
            binding.addCourseStart.text = dateFormat.format(calendar.time)
        } else {
            binding.addCourseEnd.text = dateFormat.format(calendar.time)
        }
    }


}