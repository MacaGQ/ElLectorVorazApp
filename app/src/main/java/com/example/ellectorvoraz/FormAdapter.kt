package com.example.ellectorvoraz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.ellectorvoraz.data.FormField

class FormAdapter (private val fields: List<FormField>) : RecyclerView.Adapter<FormAdapter.FieldViewHolder>(){

    val formData = mutableMapOf<String, String>()

    class FieldViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val editText: EditText = view.findViewById(R.id.form_edit_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_form_field, parent, false)
        return FieldViewHolder(view)
    }

    override fun onBindViewHolder(holder: FieldViewHolder, position: Int) {
        val field = fields[position]

        // Configurar el diseño de acuerdo al tipo de campo
        holder.editText.hint = field.label
        holder.editText.inputType = field.inputType
        holder.editText.setText(formData[field.key] ?: "")

        // Guardar el input del usuario mientras escribe
        holder.editText.doOnTextChanged { text, _, _, _ ->
            formData[field.key] = text.toString()
        }
    }

    override fun getItemCount() = fields.size

}