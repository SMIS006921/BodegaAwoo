package com.example.bodega

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
class ProveedoresAdapter(public var proveee:List<ProveedoresDATA>): RecyclerView.Adapter<ProveedoresAdapter.ViewHolder>(){
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val textoNombre = view.findViewById<EditText>(R.id.txtNombreProveedor)
        val textoTelefonoo = view.findViewById<EditText>(R.id.txtTelefonoProveedor)
        val textoDireccion = view.findViewById<EditText>(R.id.txtDirecccionProveedor)
        val textoCiudad = view.findViewById<EditText>(R.id.txtCiudadProveedor)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vieww = LayoutInflater.from(parent.context).inflate(R.layout.activity_proveedores,parent , false)
        return ViewHolder(vieww)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val prove = proveee[position]
        holder.textoNombre.setText(prove.Nombre)
        holder.textoTelefonoo.setText(prove.Telefono.toString())
        holder.textoDireccion.setText(prove.Direccion.toString())
        holder.textoCiudad.setText(prove.Direccion.toString())
    }
    override fun getItemCount(): Int {
        return proveee.size
    }
}