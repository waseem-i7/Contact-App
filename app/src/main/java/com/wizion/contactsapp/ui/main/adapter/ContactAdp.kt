package com.wizion.contactsapp.ui.main.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.wizion.contactsapp.R
import com.wizion.contactsapp.ui.main.interfaces.ItemClickListner
import com.wizion.contactsapp.ui.main.models.Contacts

class ContactAdp : RecyclerView.Adapter<ContactAdp.MyViewHolder> {

    private val arrayList = ArrayList<Contacts>()
    private var activity: Activity
    private var listener : ItemClickListner

    inner class MyViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        val tv1: TextView = view!!.findViewById(R.id.tv1)
        val tv2: TextView = view!!.findViewById(R.id.tv2)
    }
    constructor(activity: Activity) {
        this.activity = activity
        this.listener = activity as ItemClickListner
    }
    constructor(activity: Activity, fragment: Fragment?) {
        this.activity = activity
        this.listener = fragment as ItemClickListner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        try {
            val currentItem = arrayList[position]
            holder.tv1.text = currentItem.displayName
            holder.tv2.text = currentItem.mobileNumber
            holder.itemView.setOnClickListener {
                listener.onItemClick(holder.adapterPosition)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateArrayList(newList: Array<Contacts>){
        arrayList.clear()
        arrayList.addAll(newList)
        notifyDataSetChanged()
    }
}