package com.example.ellectorvoraz.adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ellectorvoraz.R
import com.example.ellectorvoraz.data.model.TransactionItem

class TransactionAdapter(
    private val items: MutableList<TransactionItem>,
    private val onDataChanged: () -> Unit,
    private val isPedido: Boolean
) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreProducto: TextView = view.findViewById(R.id.nombre_producto)
        val subtotalItem: TextView = view.findViewById(R.id.subtotal_item)
        val btnEliminar: ImageButton = view.findViewById(R.id.btn_eliminar_item)
        val btnRestar: Button = view.findViewById(R.id.btn_restar)
        val btnSumar: Button = view.findViewById(R.id.btn_sumar)
        val valorCantidad: TextView = view.findViewById(R.id.cantidad_item)
        val precioCosto: EditText = view.findViewById(R.id.input_precio_costo)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaccion_producto, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.nombreProducto.text = item.nombreProducto
        holder.valorCantidad.text = item.cantidad.toString()
        holder.subtotalItem.text = "Subtotal: $${String.format("%.2f", item.subtotal)}"

        holder.precioCosto.removeTextChangedListener(holder.itemView.tag as? TextWatcher)

        if (isPedido) {
            holder.precioCosto.visibility = View.VISIBLE
            if (item.precioUnitario > 0) {
                holder.precioCosto.setText(item.precioUnitario.toString())
            } else {
                holder.precioCosto.setText("")
            }

            val textWatcher = object: TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                }

                override fun afterTextChanged(s: Editable?) {
                    val nuevoCosto = s.toString().toDoubleOrNull() ?: 0.0
                    if (item.precioUnitario != nuevoCosto) {
                        item.precioUnitario = nuevoCosto
                        item.subtotal = item.cantidad * item.precioUnitario
                        holder.subtotalItem.text =
                            "Subtotal: $${String.format("%.2f", item.subtotal)}"
                        onDataChanged()
                    }
                }
            }

            holder.precioCosto.addTextChangedListener(textWatcher)
            holder.itemView.tag = textWatcher


        } else {
            holder.precioCosto.visibility = View.GONE
        }

        holder.btnSumar.setOnClickListener {
            item.cantidad++
            item.subtotal = item.cantidad * item.precioUnitario
            notifyItemChanged(position)
            onDataChanged()
        }

        holder.btnRestar.setOnClickListener {
            if (item.cantidad > 1) {
                item.cantidad--
                item.subtotal = item.cantidad * item.precioUnitario
                notifyItemChanged(position)
                onDataChanged()
            }
        }

        holder.btnEliminar.setOnClickListener {
            items.removeAt(position)
            notifyItemRemoved(position)
            onDataChanged()
        }
    }

    override fun getItemCount() = items.size
}