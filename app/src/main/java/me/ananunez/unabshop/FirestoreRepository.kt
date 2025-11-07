package me.ananunez.unabshop

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await


class FirestoreRepository {
    private val db = Firebase.firestore
    private val coleccion = db.collection("productos")

    suspend fun agregarProducto(producto: Producto): Result<String> {
        return try {
            val ref = coleccion.add(producto).await()
            Result.success(ref.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}
