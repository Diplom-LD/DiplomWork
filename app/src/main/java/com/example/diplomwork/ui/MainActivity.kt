package com.example.diplomwork.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomwork.R
import com.example.diplomwork.di.Order
import com.example.diplomwork.di.OrderAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fabAddOrder: FloatingActionButton
    private lateinit var orderAdapter: OrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Инициализация RecyclerView
        recyclerView = findViewById(R.id.recyclerViewOrders)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Пример данных
        val ordersList = listOf(
            Order("Order 1", "Details for Order 1"),
            Order("Order 2", "Details for Order 2"),
            Order("Order 3", "Details for Order 3")
        )

        // Установка адаптера
        orderAdapter = OrderAdapter(ordersList)
        recyclerView.adapter = orderAdapter

        // Инициализация FloatingActionButton
        fabAddOrder = findViewById(R.id.fabAddOrder)
        fabAddOrder.setOnClickListener {
            // Добавьте действие, которое нужно выполнить при нажатии на кнопку
            // Например, открыть новую активность для добавления заказа
        }
    }
}
