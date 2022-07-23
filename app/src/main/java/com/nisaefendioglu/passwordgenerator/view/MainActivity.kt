package com.nisaefendioglu.passwordgenerator.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nisaefendioglu.passwordgenerator.api.ApiClient
import com.nisaefendioglu.passwordgenerator.api.ApiService
import com.nisaefendioglu.passwordgenerator.databinding.ActivityMainBinding
import com.nisaefendioglu.passwordgenerator.model.PasswordResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        generatePassword()

        binding.generatePassword.apply {
            setOnClickListener {
                generatePassword()
            }
        }

        binding.copy.setOnClickListener {
            val clipboard: ClipboardManager =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Password", binding.password.text)
            clipboard.setPrimaryClip(clip)

            Toast.makeText(this, "Password Copied \uD83D\uDD10", Toast.LENGTH_SHORT).show()

        }
    }

    private fun generatePassword() {
        val clientRest: ApiService = ApiClient.create(ApiService::class.java)
        val callResponse: Call<PasswordResponse> = clientRest.getPasswordGenerator()
        callResponse.enqueue(object : Callback<PasswordResponse?> {
            override fun onResponse(
                call: Call<PasswordResponse?>,
                response: Response<PasswordResponse?>
            ) {
                val passwordResponse: PasswordResponse? = response.body()
                if (passwordResponse != null) {
                    val myPassword = passwordResponse.char.toString().replace("[", "").replace(
                        "]",
                        ""
                    )

                    binding.password.text = myPassword

                    if (binding.upperSwitch.isChecked) {
                        binding.password.text = myPassword
                            .uppercase(Locale.getDefault())
                        binding.lowerSwitch.isChecked = false
                    }

                    if (binding.lowerSwitch.isChecked) {
                        binding.password.text = myPassword
                            .lowercase(Locale.getDefault())
                        binding.upperSwitch.isChecked = false

                    }
                }
            }

            override fun onFailure(call: Call<PasswordResponse?>, t: Throwable) {
                Log.d("Password Response Fail", t.toString())
            }

        })
    }

}