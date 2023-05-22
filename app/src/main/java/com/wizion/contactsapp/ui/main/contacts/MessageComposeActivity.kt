package com.wizion.contactsapp.ui.main.contacts


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.wizion.contactsapp.BaseActivity
import com.wizion.contactsapp.databinding.ActivityMessageComposeBinding
import com.wizion.contactsapp.ui.api.ServiceBuilder
import com.wizion.contactsapp.ui.api.interfaces.SmsService
import com.wizion.contactsapp.ui.main.other.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import kotlin.random.Random


class MessageComposeActivity : BaseActivity() {
    lateinit var binding: ActivityMessageComposeBinding
    private lateinit var contactName: String
    private lateinit var contactNumber: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageComposeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        contactName = intent.getStringExtra(Constants.contactName).toString()
        contactNumber = intent.getStringExtra(Constants.contactNumber).toString()

        init()
    }

    fun init() {
        binding.header1.tvHeader.text = ""
        binding.editTextTo.setText("To : $contactNumber")
        binding.editTextSubject.setText("Subject : Sending OTP")
        binding.editTextMessage.setText("Hi, Your OTP is : ${generateRandomSixDigitNumber()}")
        binding.btnSendMessage.setOnClickListener {
            if (contactNumber.startsWith("+91")) {
                sendSMS(contactNumber, binding.editTextMessage.text.toString())
            } else if (contactNumber.startsWith("91")) {
                sendSMS("+$contactNumber", binding.editTextMessage.text.toString())
            } else {
                sendSMS("+91$contactNumber", binding.editTextMessage.text.toString())
            }
        }
    }

    /**
     * This method generate Random Six Digit OTP
     * @return OTP
     */
    private fun generateRandomSixDigitNumber(): Int {
        val random = Random(System.currentTimeMillis())
        return random.nextInt(100000, 999999)
    }


    /**
     * This method is used to send otp using Twilio Rest API
     * @param to This is the mobile number on which you want to send OTP
     * @param sms This is the sms Body
     */
    private fun sendSMS(to: String, sms: String) {
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = try {
                    ServiceBuilder.buildService(SmsService::class.java)
                        .sendSms(Constants.SMS_SERVICE_ID, to, sms)
                } catch (e: IOException) {
                    Log.e("IOException", e.message.toString())
                    return@launch
                }

                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        binding.progressBar.visibility = View.GONE
                        val responseData = response.body()!!

                        Toast.makeText(
                            this@MessageComposeActivity,
                            "Sms Sent Successfully.",
                            Toast.LENGTH_LONG
                        ).show()

                        finish()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            this@MessageComposeActivity,
                            response.message(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this@MessageComposeActivity, e.message, Toast.LENGTH_LONG).show()

                }
            }
        }


    }
}

