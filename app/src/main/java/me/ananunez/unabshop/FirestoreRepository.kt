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

    suspend fun obtenerProductos(): Result<List<Producto>> {
        return try {
            val snapshot = coleccion.get().await()
            val productos = snapshot.map { doc ->
                doc.toObject(Producto::class.java).copy(id = doc.id)
            }
            Result.success(productos)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun eliminarProducto(id: String): Result<Unit> {
        return try {
            coleccion.document(id).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}
