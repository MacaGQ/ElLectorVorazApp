package com.example.ellectorvoraz.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ellectorvoraz.data.model.DetalleCaracteristica
import com.example.ellectorvoraz.R

class DetalleAdapter (
    private val caracteristicas: List<DetalleCaracteristica>) : RecyclerView.Adapter<DetalleAdapter.ViewHolder> () {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val etiquetaTextView : TextView = view.findViewById(R.id.info_fila_lbl)
        val valorTextView : TextView = view.findViewById(R.id.info_fila_txt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_detalle_fila, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val caracteristica = caracteristicas[position]
        holder.etiquetaTextView.text = caracteristica.etiqueta
        holder.valorTextView.text = caracteristica.valor
    }

    override fun getItemCount(): Int {
        return caracteristicas.size
    }
}
