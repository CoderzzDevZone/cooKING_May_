package com.coderzz.cooking.database

import com.coderzz.cooking.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SaveUser {

    val mAuth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val collection = db.collection("users")

    fun addUser(user: User){
        collection.document(user.email).set(user)
    }
}