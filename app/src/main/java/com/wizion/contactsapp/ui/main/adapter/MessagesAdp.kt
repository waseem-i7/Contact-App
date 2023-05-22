package com.wizion.contactsapp.ui.main.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.wizion.contactsapp.R
import com.wizion.contactsapp.ui.main.interfaces.ItemClickListner
import com.wizion.contactsapp.ui.main.models.Message
import com.wizion.contactsapp.ui.main.other.Constants
import java.text.SimpleDateFormat
import java.util.Locale

class MessagesAdp : RecyclerView.Adapter<MessagesAdp.MyViewHolder> {

    private val arrayList = ArrayList<Message>()
    private var activity: Activity
    private var listener : ItemClickListner

    inner class MyViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        val tv1: TextView = view!!.findViewById(R.id.tv1)
        val tv2: TextView = view!!.findViewById(R.id.tv2)
        val tv3: TextView = view!!.findViewById(R.id.tv3)
        val tv4: TextView = view!!.findViewById(R.id.tv4)
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
            .inflate(R.layout.item_messages, parent, false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        try {
            val currentItem = arrayList[position]
            holder.tv1.text = currentItem.to
            val inputDateString= currentItem.date_sent
            val inputDateFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.getDefault())
            val inputDate = inputDateFormat.parse(inputDateString)
            val outputDateFormat = SimpleDateFormat("dd/MM/yyyy, HH:mm", Locale.getDefault())
            val outputDateString = inputDate?.let { outputDateFormat.format(it) }
            holder.tv2.text = outputDateString
            holder.tv3.text = currentItem.body.substringAfter("Hi, ")
            if (currentItem.status==Constants.failed){
                holder.tv4.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.red))
                holder.tv4.text = "Not delivered"
            }else{
                holder.tv4.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.green))
                holder.tv4.text = "Otp delivered"
            }
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
    fun updateArrayList(newList: List<Message>){
        arrayList.clear()
        arrayList.addAll(newList)
        notifyDataSetChanged()
    }
}