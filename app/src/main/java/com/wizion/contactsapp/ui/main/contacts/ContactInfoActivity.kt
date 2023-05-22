package com.wizion.contactsapp.ui.main.contacts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import com.wizion.contactsapp.BaseActivity
import com.wizion.contactsapp.databinding.ActivityContactInfoBinding
import com.wizion.contactsapp.ui.main.other.Constants

/**
 * This class is used to display contact details, such as the name and mobile number.
 */
class ContactInfoActivity : BaseActivity() {
    private lateinit var binding: ActivityContactInfoBinding
    private lateinit var contactName: String
    private lateinit var contactNumber: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        contactName = intent.getStringExtra(Constants.contactName).toString()
        contactNumber = intent.getStringExtra(Constants.contactNumber).toString()

        init()

    }

    fun init() {
        binding.header1.tvHeader.text = "Contact Info"
        binding.tv1.text = contactName
        binding.tv2.text = contactNumber
        binding.btnSendMessage.setOnClickListener {
            val intent = Intent(this, MessageComposeActivity::class.java)
            intent.putExtra(Constants.contactName, contactName)
            intent.putExtra(Constants.contactNumber, contactNumber)
            startActivity(intent)
        }
    }
}