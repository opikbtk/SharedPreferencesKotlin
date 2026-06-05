package com.example.sharedpreferenceskotlin

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BiodataActivity : AppCompatActivity() {

    private lateinit var layoutBiodata: LinearLayout
    private lateinit var switchDarkMode: Switch
    private lateinit var txtJudulBiodata: TextView
    private lateinit var txtNama: TextView
    private lateinit var txtProdi: TextView
    private lateinit var txtEmail: TextView
    private lateinit var txtUsername: TextView
    private lateinit var btnLogout: Button
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biodata)

        layoutBiodata = findViewById(R.id.layoutBiodata)
        switchDarkMode = findViewById(R.id.switchDarkMode)
        txtJudulBiodata = findViewById(R.id.txtJudulBiodata)
        txtNama = findViewById(R.id.txtNama)
        txtProdi = findViewById(R.id.txtProdi)
        txtEmail = findViewById(R.id.txtEmail)
        txtUsername = findViewById(R.id.txtUsername)
        btnLogout = findViewById(R.id.btnLogout)

        pref = getSharedPreferences("LOGIN_PREF", MODE_PRIVATE)

        val username = pref.getString("username", "-")
        txtUsername.text = "Username: $username"

        val darkMode = pref.getBoolean("darkMode", false)
        switchDarkMode.isChecked = darkMode
        applyDarkMode(darkMode)

        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            pref.edit().putBoolean("darkMode", isChecked).apply()
            applyDarkMode(isChecked)
        }

        btnLogout.setOnClickListener {
            pref.edit()
                .remove("isLogin")
                .remove("username")
                .remove("waktuLogin")
                .apply()

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun applyDarkMode(isDarkMode: Boolean) {
        if (isDarkMode) {
            layoutBiodata.setBackgroundColor(Color.parseColor("#121212"))

            switchDarkMode.setTextColor(Color.WHITE)
            txtJudulBiodata.setTextColor(Color.WHITE)

            setCardDark(txtNama)
            setCardDark(txtProdi)
            setCardDark(txtEmail)
            setCardDark(txtUsername)
        } else {
            layoutBiodata.setBackgroundColor(Color.parseColor("#F5F5F5"))

            switchDarkMode.setTextColor(Color.parseColor("#212121"))
            txtJudulBiodata.setTextColor(Color.parseColor("#212121"))

            setCardLight(txtNama)
            setCardLight(txtProdi)
            setCardLight(txtEmail)
            setCardLight(txtUsername)
        }
    }

    private fun setCardDark(textView: TextView) {
        textView.setTextColor(Color.WHITE)
        textView.setBackgroundColor(Color.parseColor("#2A2A2A"))
    }

    private fun setCardLight(textView: TextView) {
        textView.setTextColor(Color.parseColor("#212121"))
        textView.setBackgroundColor(Color.WHITE)
    }
}