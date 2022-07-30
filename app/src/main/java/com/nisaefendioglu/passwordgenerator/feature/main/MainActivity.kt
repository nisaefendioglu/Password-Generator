package com.nisaefendioglu.passwordgenerator.feature.main

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.nisaefendioglu.passwordgenerator.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launchWhenResumed {
            launch {
                viewModel.uiState.collect {
                    binding.password.text = it.passwordGenerator
                }
            }
        }
        initViews()
    }

    private fun initViews() {
        binding.apply {
            generatePassword.apply {
                setOnClickListener {
                    when {
                        binding.upperSwitch.isChecked -> {
                            setCustomizePassword(isUpperCase = true, isLowerCase = false)
                            binding.lowerSwitch.isChecked = false
                        }
                        binding.lowerSwitch.isChecked -> {
                            setCustomizePassword(isUpperCase = false, isLowerCase = true)
                            binding.upperSwitch.isChecked = false
                        }
                        else -> {
                            setCustomizePassword()
                        }
                    }
                }
            }

            copy.setOnClickListener {
                val clipboard: ClipboardManager =
                    getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Password", binding.password.text)
                clipboard.setPrimaryClip(clip)

                Toast.makeText(
                    this@MainActivity,
                    "Password Copied \uD83D\uDD10",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setCustomizePassword(isUpperCase: Boolean = true, isLowerCase: Boolean = true) {
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