package com.example.appointmentbookingsystem

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        val name = intent.getStringExtra("NAME") ?: ""
        val phone = intent.getStringExtra("PHONE") ?: ""
        val email = intent.getStringExtra("EMAIL") ?: ""
        val type = intent.getStringExtra("TYPE") ?: ""
        val date = intent.getStringExtra("DATE") ?: ""
        val time = intent.getStringExtra("TIME") ?: ""
        val gender = intent.getStringExtra("GENDER") ?: ""

        findViewById<TextView>(R.id.tvConfirmName).text = getString(R.string.label_name, name)
        findViewById<TextView>(R.id.tvConfirmPhone).text = getString(R.string.label_phone, phone)
        findViewById<TextView>(R.id.tvConfirmEmail).text = getString(R.string.label_email, email)
        findViewById<TextView>(R.id.tvConfirmType).text = getString(R.string.label_type, type)
        findViewById<TextView>(R.id.tvConfirmDate).text = getString(R.string.label_date, date)
        findViewById<TextView>(R.id.tvConfirmTime).text = getString(R.string.label_time, time)
        findViewById<TextView>(R.id.tvConfirmGender).text = getString(R.string.label_gender, gender)

        findViewById<Button>(R.id.btnBackHome).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}