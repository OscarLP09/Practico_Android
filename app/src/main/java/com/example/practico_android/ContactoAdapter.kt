package com.example.practico_android

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class ContactoAdapter(
    private val context: Context,
    private val contacts: MutableList<Contacto>
) : RecyclerView.Adapter<ContactoAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.contact_name)
        val phoneTextView: TextView = view.findViewById(R.id.contact_phone)
        val photoImageView: ImageView = view.findViewById(R.id.contact_photo)

        init {
            // Click corto: Modo de solo lectura
            view.setOnClickListener {
                val contact = contacts[adapterPosition]
                val intent = Intent(context, ContactoDetailActivity::class.java).apply {
                    putExtra("CONTACT_ID", contact.id)
                    putExtra("CONTACT_NAME", contact.name)
                    putExtra("CONTACT_PHONE", contact.phone)
                    putExtra("CONTACT_EMAIL", contact.email)
                    putExtra("EXTRA_IS_EDITABLE", false)
                }
                context.startActivity(intent)
            }


            view.setOnLongClickListener {
                showOptionsDialog(adapterPosition)
                true
            }
        }
    }

    private fun showOptionsDialog(position: Int) {
        val contact = contacts[position]


        AlertDialog.Builder(context).apply {
            setTitle("Elija una opción")
            setItems(arrayOf("Editar", "Eliminar")) { _, which ->
                when (which) {
                    0 -> { // Editar
                        val intent = Intent(context, ContactoDetailActivity::class.java).apply {
                            putExtra("CONTACT_ID", contact.id)
                            putExtra("CONTACT_NAME", contact.name)
                            putExtra("CONTACT_PHONE", contact.phone)
                            putExtra("CONTACT_EMAIL", contact.email)
                            putExtra("EXTRA_IS_EDITABLE", true)
                        }
                        context.startActivity(intent)
                    }
                    1 -> {
                        showDeleteConfirmationDialog(position)
                    }
                }
            }
        }.show()
    }

    private fun showDeleteConfirmationDialog(position: Int) {
        AlertDialog.Builder(context).apply {
            setTitle("Confirmar eliminación")
            setMessage("¿Estás seguro de que deseas eliminar este contacto?")
            setPositiveButton("Sí") { _, _ ->
                contacts.removeAt(position)
                notifyItemRemoved(position)
            }
            setNegativeButton("No", null)
        }.show()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.contacto_item, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.nameTextView.text = contact.name
        holder.phoneTextView.text = contact.phone
        holder.photoImageView.setImageResource(contact.image)
    }

    override fun getItemCount(): Int = contacts.size
}
