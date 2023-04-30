package com.example.bodega
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.widget.EditText
class ProductoAdapter(public var product:List<ProductosDATA>):RecyclerView.Adapter<ProductoAdapter.ViewHolder>(){
    class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val textoNombre = view.findViewById<EditText>(R.id.txtNombreProducto)
        val textoPrecio = view.findViewById<EditText>(R.id.txtPrecioProducto)
        val textoStock = view.findViewById<EditText>(R.id.txtStockProducto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_productos,parent , false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produ = product[position]
        holder.textoNombre.setText(produ.Nombre)
        holder.textoPrecio.setText(produ.Precio.toString())
        holder.textoStock.setText(produ.Stock.toString())
    }
    override fun getItemCount(): Int {
        return product.size
    }
}