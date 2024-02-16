package com.example.jetchatappcompose.data.model

data class AppUser(
    val id: String? = null,
    val firstName: String? = null,
    val email: String? = null
) {
    companion object {
        const val COLLECTION_NAME = "Users"
    }
}
