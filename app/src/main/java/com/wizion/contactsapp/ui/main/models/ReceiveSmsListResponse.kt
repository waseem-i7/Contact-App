package com.wizion.contactsapp.ui.main.models

data class ReceiveSmsListResponse(
    val end: Int,
    val first_page_uri: String,
    val messages: List<Message>,
    val next_page_uri: Any,
    val page: Int,
    val page_size: Int,
    val previous_page_uri: Any,
    val start: Int,
    val uri: String
)