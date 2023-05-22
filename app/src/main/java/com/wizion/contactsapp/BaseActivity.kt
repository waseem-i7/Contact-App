package com.wizion.contactsapp

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

/**
 *<i>Contacts App</i>
 *<p>This contact app helps you efficiently manage and organize your contacts. It allows you to send OTPs to your contacts and conveniently access their information, including names and phone numbers, all in one place. Additionally, the app provides a list of delivered and undelivered OTPs for easy tracking.</p>
 * @author Waseem Idrisi
 * @version 1.0
 * @since 2023
 * @see <a href="https://github.com/waseem-i7" target="_blank">Github</a>
 * @see <a href="https://www.linkedin.com/in/waseem-idrisi-5b2579195/" target="_blank">LinkedIn</a>
 */
open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.purple_500)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun BackNow(view: View) {
        finish()
    }

}
