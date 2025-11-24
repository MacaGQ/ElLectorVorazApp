package com.example.ellectorvoraz.adapters

import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.ellectorvoraz.R
import com.example.ellectorvoraz.data.repository.FormField
import com.example.ellectorvoraz.data.repository.FormFieldType

class FormAdapter (
    val fields: List<FormField>,
    private val onSelectionClicked: (fieldKey: String, entityType: String) -> Unit
) : RecyclerView.Adapter<FormAdapter.FieldViewHolder>(){

    val formData = mutableMapOf<String, String>()

    class FieldViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val editText: EditText = view.findViewById(R.id.form_edit_text)
        val selectorLayout: LinearLayout = view.findViewById(R.id.selector_layout)
        val selectorValue: TextView = view.findViewById(R.id.selector_value)
        val selectorButton: Button = view.findViewById(R.id.selector_button)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_form_field, parent, false)
        return FieldViewHolder(view)
    }

    override fun onBindViewHolder(holder: FieldViewHolder, position: Int) {
        val field = fields[position]
        holder.editText.tag = field.key

        // Configurar el diseño de acuerdo al tipo de campo
        when (field.type) {
            FormFieldType.ENTITY_SELECTOR -> {
                holder.editText.visibility = View.GONE
                holder.selectorLayout.visibility = View.VISIBLE

                val valorSeleccionado = formData[field.key]

                if (valorSeleccionado.isNullOrBlank()) {
                    holder.selectorValue.text = field.label
                } else {
                    holder.selectorValue.text = valorSeleccionado
                }

                holder.selectorButton.setOnClickListener {
                    onSelectionClicked(field.key, field.entityType ?: "")
                }
            }
            else -> {
                holder.editText.visibility = View.VISIBLE
                holder.selectorLayout.visibility = View.GONE

                holder.editText.hint = field.label
                holder.editText.setText(formData[field.key] ?: "")

                holder.editText.inputType = when(field.type) {
                    FormFieldType.NUMBER -> InputType.TYPE_CLASS_NUMBER
                    FormFieldType.NUMBER_DECIMAL -> InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
                    FormFieldType.DATETIME -> InputType.TYPE_CLASS_DATETIME
                    else -> InputType.TYPE_CLASS_TEXT
                }

                // Guardar el input del usuario mientras escribe
                holder.editText.doOnTextChanged { text, _, _, _ ->
                    formData[field.key] = text.toString()
                }
            }
        }
    }

    override fun getItemCount() = fields.size

    fun updateFieldValue(key: String, value: String) {
        formData[key] = value
        val position = fields.indexOfFirst { it.key == key }
        if (position != -1) {
            notifyItemChanged(position)
        }
    }

}

