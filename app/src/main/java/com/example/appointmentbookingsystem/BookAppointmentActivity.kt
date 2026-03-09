package com.example.appointmentbookingsystem

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class BookAppointmentActivity : AppCompatActivity() {

    private lateinit var etName: TextInputEditText
    private lateinit var etPhone: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var spinnerType: Spinner
    private lateinit var btnDate: Button
    private lateinit var btnTime: Button
    private lateinit var tvSelectedDateTime: TextView
    private lateinit var rgGender: RadioGroup
    private lateinit var cbTerms: CheckBox
    private lateinit var btnConfirm: Button

    private var selectedDate = ""
    private var selectedTime = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_appointment)

        etName = findViewById(R.id.etName)
        etPhone = findViewById(R.id.etPhone)
        etEmail = findViewById(R.id.etEmail)
        spinnerType = findViewById(R.id.spinnerType)
        btnDate = findViewById(R.id.btnDate)
        btnTime = findViewById(R.id.btnTime)
        tvSelectedDateTime = findViewById(R.id.tvSelectedDateTime)
        rgGender = findViewById(R.id.rgGender)
        cbTerms = findViewById(R.id.cbTerms)
        btnConfirm = findViewById(R.id.btnConfirmBooking)

        btnDate.setOnClickListener {
            showDatePicker()
        }

        btnTime.setOnClickListener {
            showTimePicker()
        }

        btnConfirm.setOnClickListener {
            validateAndConfirm()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(this, R.style.DialogTheme, { _, y, m, d ->
            selectedDate = "$d/${m + 1}/$y"
            updateDateTimeDisplay()
        }, year, month, day)
        datePicker.show()
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        // Using a more user-friendly TimePickerDialog with a Spinner style if supported or standard Material
        val timePicker = TimePickerDialog(this, R.style.DialogTheme, { _, h, m ->
            val amPm = if (h < 12) "AM" else "PM"
            val hourOfDay = if (h % 12 == 0) 12 else h % 12
            selectedTime = String.format(Locale.getDefault(), "%02d:%02d %s", hourOfDay, m, amPm)
            updateDateTimeDisplay()
        }, hour, minute, false)
        timePicker.show()
    }

    private fun updateDateTimeDisplay() {
        if (selectedDate.isNotEmpty() || selectedTime.isNotEmpty()) {
            val datePart = if (selectedDate.isNotEmpty()) selectedDate else "Not set"
            val timePart = if (selectedTime.isNotEmpty()) selectedTime else "Not set"
            tvSelectedDateTime.text = getString(R.string.selected_date_time, datePart, timePart)
        }
    }

    private fun validateAndConfirm() {
        val name = etName.text.toString().trim()
        val phone = etPhone.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val type = spinnerType.selectedItem.toString()
        val genderId = rgGender.checkedRadioButtonId
        val gender = if (genderId != -1) {
            findViewById<RadioButton>(genderId).text.toString()
        } else {
            ""
        }

        if (name.isEmpty()) {
            etName.error = getString(R.string.err_empty_name)
            return
        }
        if (phone.isEmpty()) {
            etPhone.error = getString(R.string.err_empty_phone)
            return
        }
        if (email.isEmpty()) {
            etEmail.error = getString(R.string.err_empty_email)
            return
        }
        if (selectedDate.isEmpty() || selectedTime.isEmpty()) {
            Toast.makeText(this, getString(R.string.err_date_time), Toast.LENGTH_SHORT).show()
            return
        }
        if (gender.isEmpty()) {
            Toast.makeText(this, getString(R.string.err_gender), Toast.LENGTH_SHORT).show()
            return
        }
        if (!cbTerms.isChecked) {
            Toast.makeText(this, getString(R.string.err_terms), Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(this, ConfirmationActivity::class.java).apply {
            putExtra("NAME", name)
            putExtra("PHONE", phone)
            putExtra("EMAIL", email)
            putExtra("TYPE", type)
            putExtra("DATE", selectedDate)
            putExtra("TIME", selectedTime)
            putExtra("GENDER", gender)
        }
        startActivity(intent)
    }
}