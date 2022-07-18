package com.nisaefendioglu.passwordgenerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.nisaefendioglu.passwordgenerator.databinding.ActivityMainBinding
import com.nisaefendioglu.passwordgenerator.viewmodel.PasswordGeneratorViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel : PasswordGeneratorViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.generatePassword.apply {
            setOnClickListener {
             //   binding.password.text = viewModel.responsePassword.toString()
            }
        }

    }

}