package com.wizion.contactsapp.ui.main.models

data class Message(
    val account_sid: String,
    val api_version: String,
    val body: String,
    val date_created: String,
    val date_sent: String,
    val date_updated: String,
    val direction: String,
    val error_code: Int,
    val error_message: Any,
    val from: String,
    val messaging_service_sid: String,
    val num_media: String,
    val num_segments: String,
    val price: String,
    val price_unit: String,
    val sid: String,
    val status: String,
    val subresource_uris: SubresourceUris,
    val to: String,
    val uri: String
)