package com.example.bodega

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class helperSQLite(context:Context):SQLiteOpenHelper(context, "Bodega", null, 1) {
    companion object {
        // Constantes para la tabla Productos
        const val TABLE_PRODUCTOS = "Productos"
        const val COL_ID = "id"
        const val COL_NOMBRE = "Nombre"
        const val COL_PRECIO = "Precio"
        const val COL_STOCK = "Stock"
        const val COL_PROID = "ProveedoresID"
    }

    //create tabla productos
    override fun onCreate(db: SQLiteDatabase?) {
        val tblProductos = "CREATE TABLE $TABLE_PRODUCTOS" +
                "($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COL_NOMBRE TEXT, $COL_PRECIO DOUBLE, $COL_STOCK INTEGER," +
                "$COL_PROID INTEGER," +
                "FOREIGN KEY($COL_PROID) REFERENCES tblProveedores(ID))"
        db!!.execSQL(tblProductos)
    }

    //update prodcutos
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val actualizartblProductos = "DROP TABLE IF EXISTS $TABLE_PRODUCTOS"
        db!!.execSQL(actualizartblProductos)
        onCreate(db)
    }

    //insert into prodcutos
    fun createProductos(nombre: String, precio: Double, stock: Int) {
        val datos = ContentValues()
       // datos.put(COL_PROID, proveedoresid)
        datos.put(COL_NOMBRE, nombre)
        datos.put(COL_PRECIO, precio)
        datos.put(COL_STOCK, stock)
        val db = this.writableDatabase
        db.insert(TABLE_PRODUCTOS, null, datos)
        db.close()
    }

    //delete
    fun deleteProducto(nombre: String): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_PRODUCTOS, "$COL_NOMBRE = ?", arrayOf(nombre))
    }



    @SuppressLint("Range")
    fun getProductos(): List<ProductosDATA> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Productos", null)
        val produ = mutableListOf<ProductosDATA>()

        while(cursor.moveToNext()) {
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow("Nombre"))
            val precio = cursor.getDouble(cursor.getColumnIndexOrThrow("Precio"))
            val stock = cursor.getInt(cursor.getColumnIndexOrThrow("Stock"))
            //val proveedorid = cursor.getInt(cursor.getColumnIndexOrThrow("ProveedoresID"))

            val producto = ProductosDATA(nombre, precio, stock)
            produ.add(producto)
        }

        cursor.close()
        db.close()

        return produ
    }


}
