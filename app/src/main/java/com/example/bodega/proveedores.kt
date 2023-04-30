package com.example.bodega
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class proveedores : AppCompatActivity() {
    lateinit var helperSQLiteProv: helperSQLiteProv
    lateinit var adapter: ProveedoresAdapter // Definición de la variable adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proveedores)
        val nombree = findViewById<EditText>(R.id.txtNombreProveedor)
        val telefono = findViewById<EditText>(R.id.txtTelefonoProveedor)
        val dirección = findViewById<EditText>(R.id.txtDirecccionProveedor)
        val ciudad = findViewById<EditText>(R.id.txtCiudadProveedor)
        val registrar = findViewById<Button>(R.id.btnReg)
        val mostrar = findViewById<Button>(R.id.btnMostrarr)
        val txtLista = findViewById<RecyclerView>(R.id.txtDatos)
        val btnEliminar = findViewById<Button>(R.id.btnEliminar)
        txtLista.layoutManager = LinearLayoutManager(this)
        helperSQLiteProv = helperSQLiteProv(this)
        // actualizar la lista de prov al inicio
        adapter = ProveedoresAdapter(helperSQLiteProv.getProveedores())
        txtLista.adapter = adapter
        registrar.setOnClickListener{
            if(nombree.text.isNullOrEmpty()||telefono.text.isNullOrEmpty()||dirección.text.isNullOrEmpty()||ciudad.text.isNullOrEmpty()){
                Toast.makeText(this, "Rellene los campos", Toast.LENGTH_LONG).show()
            }else{
                helperSQLiteProv.createProveedores(
                    nombree.text.toString(),
                    telefono.toString().toDouble(),
                    dirección.text.toString(),
                    ciudad.text.toString()
                )
                Toast.makeText(this, "Datos Ingresados", Toast.LENGTH_LONG).show()
                nombree.text.clear()
                telefono.text.clear()
                dirección.text.clear()
                ciudad.text.clear()
            }
        }
        mostrar.setOnClickListener{
            val db: SQLiteDatabase = helperSQLiteProv.readableDatabase
            val cursor = db.rawQuery("SELECT * FROM proveedores", null)
            val proveedoresList = ArrayList<ProveedoresDATA>()

            if (cursor.moveToFirst()){
                do { proveedoresList.add(ProveedoresDATA(cursor.getString(1), cursor.getDouble(2), cursor.getString(3), cursor.getString(4)))
                } while (cursor.moveToNext())
            }
            cursor.close()
            // actualizar la lista de prov
            adapter.proveee = proveedoresList
            adapter.notifyDataSetChanged()
        }
        btnEliminar.setOnClickListener {
            val nombre = nombree.text.toString()
            if(nombre.isNullOrEmpty()) {
                Toast.makeText(this, "Ingrese el nombre del proveedor a eliminar", Toast.LENGTH_LONG).show()
            } else {
                val filasEliminadas = helperSQLiteProv.deleteProveedores(nombre)
                if(filasEliminadas > 0) {
                    Toast.makeText(this, "Registro eliminado correctamente", Toast.LENGTH_LONG).show()
                    // actualizar la lista de prov
                    adapter.proveee = helperSQLiteProv.getProveedores()
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this, "No se pudo eliminar el registro", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}