package com.wizion.contactsapp.ui.main.interfaces

/**
 * By using the ItemClickListener interface, you can easily handle button clicks on items within your list or collection and perform the desired actions based on the clicked item's position or other relevant data.
 */
interface ItemClickListner {
    fun onItemClick(position: Int)
    fun onItemClick(position: Int, rtype: Int)
}