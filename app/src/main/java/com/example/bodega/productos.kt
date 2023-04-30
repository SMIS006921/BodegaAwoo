package com.example.bodega
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
class productos : AppCompatActivity() {
    lateinit var helperSQLite: helperSQLite
    lateinit var adapter: ProductoAdapter // Definición de la variable adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos)
        val nombree = findViewById<EditText>(R.id.txtNombreProducto)
        val precio = findViewById<EditText>(R.id.txtPrecioProducto)
        val stock = findViewById<EditText>(R.id.txtStockProducto)
        val registrar = findViewById<Button>(R.id.btnReg)
        val mostrar = findViewById<Button>(R.id.btnMostrarr)
        val btnEliminar = findViewById<Button>(R.id.btnEliminar)
        val recyclerView = findViewById<RecyclerView>(R.id.txtDatos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        helperSQLite = helperSQLite(this)
        // actualizar la lista de productos al inicio
        adapter = ProductoAdapter(helperSQLite.getProductos())
        recyclerView.adapter = adapter
        registrar.setOnClickListener{
            if(nombree.text.isNullOrEmpty()||precio.text.isNullOrEmpty()||stock.text.isNullOrEmpty()){
                Toast.makeText(this, "Rellene los campos", Toast.LENGTH_LONG).show()
            }else{
                helperSQLite.createProductos(
                    nombree.text.toString(),
                    precio.text.toString().toDouble(),
                    stock.text.toString().toInt(),
                )
                Toast.makeText(this, "Datos Ingresados", Toast.LENGTH_LONG).show()
                nombree.text.clear()
                precio.text.clear()
                stock.text.clear()
            }
        }
        mostrar.setOnClickListener{
            val db: SQLiteDatabase = helperSQLite.readableDatabase
            val cursor = db.rawQuery("SELECT * FROM productos", null)
            val productosList = ArrayList<ProductosDATA>()

            if (cursor.moveToFirst()){
                do {
                    productosList.add(ProductosDATA(cursor.getString(1), cursor.getDouble(2), cursor.getInt(3)))
                } while (cursor.moveToNext())
            }
            cursor.close()
            // actualizar la lista de productos
            adapter.product = productosList
            adapter.notifyDataSetChanged()
        }
        btnEliminar.setOnClickListener {
            val nombre = nombree.text.toString()
            if(nombre.isNullOrEmpty()) {
                Toast.makeText(this, "Ingrese el nombre del producto a eliminar", Toast.LENGTH_LONG).show()
            } else {
                val filasEliminadas = helperSQLite.deleteProducto(nombre)
                if(filasEliminadas > 0) {
                    Toast.makeText(this, "Registro eliminado correctamente", Toast.LENGTH_LONG).show()
                    // actualizar la lista de productos
                    adapter.product = helperSQLite.getProductos()
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this, "No se pudo eliminar el registro", Toast.LENGTH_LONG).show()
                }
            }
        }


        //AAAAAAAAAAAAAAAAAAAAAA



    }
}