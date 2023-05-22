package com.wizion.contactsapp.ui.main.contacts

import android.Manifest
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.wizion.contactsapp.databinding.FragmentContactsBinding
import com.wizion.contactsapp.ui.main.adapter.ContactAdp
import com.wizion.contactsapp.ui.main.interfaces.ItemClickListner
import com.wizion.contactsapp.ui.main.models.Contacts
import com.wizion.contactsapp.ui.main.other.Constants


/**
 * This class is used to display contacts list.
 */
class ContactsFragment : Fragment() , ItemClickListner {

    private var _binding: FragmentContactsBinding? = null
    private val REQUEST_CONTACTS_PERMISSION = 1
    private lateinit var contactsList : Array<Contacts>
    private  lateinit var contactAdp: ContactAdp



    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                fetchContacts()
            } else {
                // Handle permission denied case
            }
        }



    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        val root = binding.root
        addAdp1()
        init()
        return root
    }

    fun init(){
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fetchContacts()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
        }
    }

    /**
     * This method is used to fetchContacts from ContactList.json file(inside assets folder)
     * you can  also uncomment the code to access contacts from your device.
     */
    private fun fetchContacts() {
        val inputStream = requireActivity().assets.open("ContactList.json")
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val json = String(buffer,Charsets.UTF_8)
        val gson = Gson()
        contactsList=gson.fromJson(json,Array<Contacts>::class.java)
        contactAdp.updateArrayList(contactsList)

//        val contentResolver: ContentResolver = requireActivity().contentResolver
//        val cursor = contentResolver.query(
//            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//            null,
//            null,
//            null,
//            null
//        )
//
//        if (cursor != null) {
//            cursor.use {
//                val nameColumnIndex =
//                    it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
//                val numberColumnIndex =
//                    it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
//
//                while (it.moveToNext()) {
//                    val name = it.getString(nameColumnIndex)
//                    val number = it.getString(numberColumnIndex)
//                    contactsList.add(Contacts(number,name))
//                }
//            }
//            contactAdp.updateArrayList(contactsList)
//            cursor.close()

    }




    private fun addAdp1() {
        contactAdp = ContactAdp(requireActivity(), this)
        val layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.lv1.layoutManager = layoutManager
        binding.lv1.itemAnimator = DefaultItemAnimator()
        binding.lv1.isNestedScrollingEnabled = true
        binding.lv1.adapter = contactAdp
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(position: Int) {
       val intent = Intent(requireActivity(),ContactInfoActivity::class.java)
        intent.putExtra(Constants.contactName,contactsList[position].displayName)
        intent.putExtra(Constants.contactNumber,contactsList[position].mobileNumber)
        startActivity(intent)
    }

    override fun onItemClick(position: Int, rtype: Int) {

    }

}