package com.example.cashichallenge.factory

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore

actual fun getFirestore(): FirebaseFirestore {
    return Firebase.firestore
}
