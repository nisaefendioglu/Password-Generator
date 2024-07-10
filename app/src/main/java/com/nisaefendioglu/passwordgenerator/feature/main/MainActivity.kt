package com.nisaefendioglu.passwordgenerator.feature.main

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.nisaefendioglu.passwordgenerator.R
import com.nisaefendioglu.passwordgenerator.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var mAdView: AdView
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        lifecycleScope.launchWhenResumed {
            viewModel.uiState.collect { uiState ->
                binding.password.text = uiState.passwordGenerator
            }
        }
        setupViews()
    }

    private fun setupViews() {
        binding.apply {
            setupSwitchListeners()
            generatePassword.setOnClickListener { onGeneratePasswordClicked() }
            copy.setOnClickListener { copyPasswordToClipboard() }
        }
    }

    private fun setupSwitchListeners() {
        binding.apply {
            upperSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) lowerSwitch.isChecked = false
            }

            lowerSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) upperSwitch.isChecked = false
            }
        }
    }

    private fun onGeneratePasswordClicked() {
        val isUpperChecked = binding.upperSwitch.isChecked
        val isLowerChecked = binding.lowerSwitch.isChecked

        if (!isUpperChecked && !isLowerChecked) {
            setCustomizePassword(isUpperCase = true, isLowerCase = true)
        } else {
            setCustomizePassword(isUpperCase = isUpperChecked, isLowerCase = isLowerChecked)
        }
    }

    private fun copyPasswordToClipboard() {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Password", binding.password.text)
        clipboard.setPrimaryClip(clip)

        Toast.makeText(this, "Password Copied \uD83D\uDD10", Toast.LENGTH_SHORT).show()
    }

    private fun setCustomizePassword(isUpperCase: Boolean, isLowerCase: Boolean) {
        viewModel.onTriggerEvent(
            MainViewEvent.GeneratePassword(
                viewModel.uiState.value.copy(
                    isUpperCase = isUpperCase,
                    isLowerCase = isLowerCase
                )
            )
        )
    }
}
