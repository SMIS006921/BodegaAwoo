package com.example.bodega

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class helperSQLiteProv(context:Context):SQLiteOpenHelper(context, "Bodega", null, 1) {
    companion object {
        // Constantes para la tabla Proveedores
        const val TABLE_PROVEEDORES = "Proveedores"
        const val COL_ID = "ID"
        const val COL_NOMBRE = "Nombre"
        const val COL_TELEFONO = "Telefono"
        const val COL_DIRECCION = "Direccion"
        const val COL_CIUDAD = "Ciudad"
    }

    //create tabla Proveedores
    override fun onCreate(db: SQLiteDatabase?) {
        val tblProveedores = "CREATE TABLE $TABLE_PROVEEDORES" +
                "($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COL_NOMBRE TEXT, $COL_TELEFONO DOUBLE, $COL_DIRECCION TEXT, $COL_CIUDAD TEXT)"
        db!!.execSQL(tblProveedores)
    }

    //update Proveedores
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val actualizarTblProveedores = "DROP TABLE IF EXISTS $TABLE_PROVEEDORES"
        db!!.execSQL(actualizarTblProveedores)
        onCreate(db)
    }

    //insert into Proveedores
    fun createProveedores(nombre: String, telefono: Double, direccion: String, ciudad: String) {
        val datos = ContentValues()
        datos.put(COL_NOMBRE, nombre)
        datos.put(COL_TELEFONO, telefono)
        datos.put(COL_DIRECCION, direccion)
        datos.put(COL_CIUDAD, ciudad)
        val db = this.writableDatabase
        db.insert(TABLE_PROVEEDORES, null, datos)
        db.close()
    }

    //delete Proveedores
    fun deleteProveedores(nombre: String): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_PROVEEDORES, "$COL_NOMBRE = ?", arrayOf(nombre))
    }

    @SuppressLint("Range")
    fun getProveedores(): List<ProveedoresDATA> {
        // Obtiene una referencia a la base de datos en modo lectura.
        val db = this.readableDatabase
        // Ejecuta una consulta SELECT en la tabla "Proveedores".
        val cursor = db.rawQuery("SELECT * FROM $TABLE_PROVEEDORES", null)
        // Crea una lista mutable de proveedores vacía.
        val proved = mutableListOf<ProveedoresDATA>()
        // repite el cursor para obtener los datos de cada fila.
        while (cursor.moveToNext()) {
            // Obtiene el nombre, telefono, direccion y ciudad del proveedor actual.
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow("Nombre"))
            val telefono = cursor.getDouble(cursor.getColumnIndexOrThrow("Telefono"))
            val direccion = cursor.getString(cursor.getColumnIndexOrThrow("Direccion"))
            val ciudad = cursor.getString(cursor.getColumnIndexOrThrow("Ciudad"))

            // Crea un objeto ProveedoresDATA con los datos del proveedor actual y lo agrega a la lista.
            val pro = ProveedoresDATA(nombre, telefono.toString().toDouble(), direccion, ciudad)
            proved.add(pro)
        }
        // Cierra el cursor y la base de datos.
        cursor.close()
        db.close()
        return proved
    }
}
// Devuelve
