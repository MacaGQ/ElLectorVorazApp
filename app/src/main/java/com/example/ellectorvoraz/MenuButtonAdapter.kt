package com.example.ellectorvoraz

import android.view.LayoutInflater
import com.example.ellectorvoraz.data.MenuButton
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView


class MenuButtonAdapter(
    private val buttons: List<MenuButton>,
    private val smallLayoutThreshold: Int = 4
) : RecyclerView.Adapter<MenuButtonAdapter.ButtonViewHolder>() {

    class ButtonViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val button: AppCompatButton = view.findViewById(R.id.menu_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu_button, parent, false)
        return ButtonViewHolder(view)
    }

    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
        val buttonData = buttons[position]
        holder.button.text = buttonData.text

        if (buttons.size >= smallLayoutThreshold) {
            holder.button.layoutParams.height = 250
        } else {
            holder.button.layoutParams.height = 400
        }

        holder.button.setOnClickListener {
            val intent = buttonData.createIntentAction(holder.itemView.context)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = buttons.size

}