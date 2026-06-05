package com.example.sharedpreferenceskotlin

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var layoutMain: LinearLayout
    private lateinit var switchDarkMode: Switch
    private lateinit var txtJudulLogin: TextView
    private lateinit var txtSubJudulLogin: TextView
    private lateinit var txtInfoLogin: TextView
    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var pref: SharedPreferences

    private val masaLogin: Long = 24 * 60 * 60 * 1000 // 24 jam

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pref = getSharedPreferences("LOGIN_PREF", MODE_PRIVATE)

        val isLogin = pref.getBoolean("isLogin", false)
        val waktuLogin = pref.getLong("waktuLogin", 0)
        val waktuSekarang = System.currentTimeMillis()

        if (isLogin && waktuSekarang - waktuLogin < masaLogin) {
            startActivity(Intent(this, BiodataActivity::class.java))
            finish()
            return
        } else if (isLogin && waktuSekarang - waktuLogin >= masaLogin) {
            pref.edit().remove("isLogin").remove("username").remove("waktuLogin").apply()
            Toast.makeText(this, "Sesi login telah habis, silakan login kembali", Toast.LENGTH_SHORT).show()
        }

        setContentView(R.layout.activity_main)

        layoutMain = findViewById(R.id.layoutMain)
        switchDarkMode = findViewById(R.id.switchDarkMode)
        txtJudulLogin = findViewById(R.id.txtJudulLogin)
        txtSubJudulLogin = findViewById(R.id.txtSubJudulLogin)
        txtInfoLogin = findViewById(R.id.txtInfoLogin)
        edtUsername = findViewById(R.id.edtUsername)
        edtPassword = findViewById(R.id.edtPassword)
        btnLogin = findViewById(R.id.btnLogin)

        val darkMode = pref.getBoolean("darkMode", false)
        switchDarkMode.isChecked = darkMode
        applyDarkMode(darkMode)

        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            pref.edit().putBoolean("darkMode", isChecked).apply()
            applyDarkMode(isChecked)
        }

        btnLogin.setOnClickListener {
            val username = edtUsername.text.toString()
            val password = edtPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username dan password harus diisi", Toast.LENGTH_SHORT).show()
            } else if (username == "admin" && password == "12345") {
                pref.edit()
                    .putBoolean("isLogin", true)
                    .putString("username", username)
                    .putLong("waktuLogin", System.currentTimeMillis())
                    .apply()

                Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, BiodataActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Username atau password salah", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun applyDarkMode(isDarkMode: Boolean) {
        if (isDarkMode) {
            layoutMain.setBackgroundColor(Color.parseColor("#121212"))

            switchDarkMode.setTextColor(Color.WHITE)
            txtJudulLogin.setTextColor(Color.WHITE)
            txtSubJudulLogin.setTextColor(Color.LTGRAY)
            txtInfoLogin.setTextColor(Color.LTGRAY)

            edtUsername.setTextColor(Color.BLACK)
            edtUsername.setHintTextColor(Color.GRAY)
            edtUsername.setBackgroundColor(Color.WHITE)

            edtPassword.setTextColor(Color.BLACK)
            edtPassword.setHintTextColor(Color.GRAY)
            edtPassword.setBackgroundColor(Color.WHITE)
        } else {
            layoutMain.setBackgroundColor(Color.parseColor("#F5F5F5"))

            switchDarkMode.setTextColor(Color.parseColor("#212121"))
            txtJudulLogin.setTextColor(Color.parseColor("#212121"))
            txtSubJudulLogin.setTextColor(Color.parseColor("#666666"))
            txtInfoLogin.setTextColor(Color.parseColor("#777777"))

            edtUsername.setTextColor(Color.BLACK)
            edtUsername.setHintTextColor(Color.GRAY)
            edtUsername.setBackgroundColor(Color.WHITE)

            edtPassword.setTextColor(Color.BLACK)
            edtPassword.setHintTextColor(Color.GRAY)
            edtPassword.setBackgroundColor(Color.WHITE)
        }
    }
}