package com.example.practico_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {

    private lateinit var contactAdapter: ContactoAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        // Inicializar el adaptador con la lista de contactos desde el proveedor
        contactAdapter = ContactoAdapter(this, ContactoProvider.contactoList.toMutableList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = contactAdapter

        // Configuración de SwipeRefreshLayout
        swipeRefreshLayout = findViewById(R.id.swipe_refresh)
        swipeRefreshLayout.setOnRefreshListener {
            refreshContactList()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun onResume() {
        super.onResume()
        // Recargar la lista de contactos al regresar a MainActivity
        refreshContactList()
    }

    private fun refreshContactList() {
        // Actualizar la lista de contactos desde el proveedor
        contactAdapter.notifyDataSetChanged()
    }
}
