package com.example.ellectorvoraz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ellectorvoraz.data.model.CatalogItem

class CatalogAdapter (private var items: List<CatalogItem>) : RecyclerView.Adapter<CatalogAdapter.ViewHolder>() {

    // Vista de cada elemento
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.item_title)
        val descriptionTextview: TextView = view.findViewById(R.id.item_description)
       /* val imageView: ImageView = view.findViewById(R.id.item_image)*/
    }

    // Crea la vista para cada elemento
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_catalog_entry, parent, false)
        return ViewHolder(view)
    }

    // Carga los datos en la vista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.nameTextView.text = item.nombre
        holder.descriptionTextview.text = item.descripcion

    }

    override fun getItemCount() = items.size

    fun updateItems(newItems: List<CatalogItem>) {
        this.items = newItems
        notifyDataSetChanged()
    }
}