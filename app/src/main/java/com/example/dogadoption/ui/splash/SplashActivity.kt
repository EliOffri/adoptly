package com.example.dogadoption.ui.splash

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.dogadoption.MainActivity
import com.example.dogadoption.R
import com.example.dogadoption.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        styleAppName()

        binding.btnGetStarted.setOnClickListener { launchMain() }
        binding.textSignIn.setOnClickListener { launchMain() }
    }

    private fun styleAppName() {
        val appName = getString(R.string.app_name)
        val spannable = SpannableString(appName)
        val orange = ContextCompat.getColor(this, R.color.primary)
        spannable.setSpan(ForegroundColorSpan(orange), 0, 1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.textAppName.text = spannable
    }

    private fun launchMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
