package com.wizion.contactsapp.ui.main.messages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.wizion.contactsapp.databinding.FragmentMessagesBinding
import com.wizion.contactsapp.ui.api.ServiceBuilder
import com.wizion.contactsapp.ui.api.interfaces.SmsService
import com.wizion.contactsapp.ui.main.adapter.ContactAdp
import com.wizion.contactsapp.ui.main.adapter.MessagesAdp
import com.wizion.contactsapp.ui.main.interfaces.ItemClickListner
import com.wizion.contactsapp.ui.main.models.ReceiveSmsListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * This class is used to display sent Messages List.
 */
class MessagesFragment : Fragment(), ItemClickListner {

    private var _binding: FragmentMessagesBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var responseData : ReceiveSmsListResponse
    private  lateinit var messagesAdp : MessagesAdp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMessagesBinding.inflate(inflater, container, false)
        val root = binding.root

        init()
        addAdp()
        return root
    }

    fun init() {
        receiveSMS()
        binding.swipeRefreshLayout.setOnRefreshListener {
            receiveSMS()
        }
    }

    /**
     * This method is used to receive SMS List using Twilio Rest API
     */
    private fun receiveSMS() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = try {
                    ServiceBuilder.buildService(SmsService::class.java).receiveSmsList()
                }catch (e:IOException){
                    Log.e("IOException", e.message.toString())
                    binding.swipeRefreshLayout.isRefreshing = false
                    return@launch
                }

                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                         responseData = response.body()!!
                        messagesAdp.updateArrayList(responseData.messages)
                        binding.swipeRefreshLayout.isRefreshing = false
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        binding.swipeRefreshLayout.isRefreshing = false
                        Toast.makeText(requireActivity(), "Message Failed.", Toast.LENGTH_LONG).show()

                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(requireActivity(), "Message Failed.", Toast.LENGTH_LONG).show()
                }
            }
        }

    }


    private fun addAdp() {
        messagesAdp = MessagesAdp(requireActivity(), this)
        val layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.lv1.layoutManager = layoutManager
        binding.lv1.itemAnimator = DefaultItemAnimator()
        binding.lv1.isNestedScrollingEnabled = true
        binding.lv1.adapter = messagesAdp
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(position: Int) {

    }

    override fun onItemClick(position: Int, rtype: Int) {

    }
}