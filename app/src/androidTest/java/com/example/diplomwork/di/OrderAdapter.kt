package com.example.diplomwork.di

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.diplomwork.di.Order
import com.example.diplomwork.di.OrderAdapter
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.kotlin.mock

@RunWith(AndroidJUnit4::class)
class OrderAdapterTest {

    private lateinit var orderAdapter: OrderAdapter
    private val orderList = listOf(Order("Order 1", "Details 1"), Order("Order 2", "Details 2"))

    @Before
    fun setUp() {
        orderAdapter = OrderAdapter(orderList)
    }

    @Test
    fun testGetItemCount() {
        // Act
        val itemCount = orderAdapter.itemCount

        // Assert
        assertEquals(2, itemCount)
    }

    @Test
    fun testOnCreateViewHolder() {
        // Arrange
        val parent = mock<ViewGroup>()
        val layoutInflater = mock<LayoutInflater>()
        val view = mock<View>()

        `when`(parent.context).thenReturn(mock())
        `when`(parent.context.getSystemService(LayoutInflater::class.java)).thenReturn(layoutInflater)
        `when`(layoutInflater.inflate(R.layout.order_item, parent, false)).thenReturn(view)

        // Act
        val viewHolder = orderAdapter.onCreateViewHolder(parent, 0)

        // Assert
        assertNotNull(viewHolder)
        assertTrue(viewHolder is OrderAdapter.OrderViewHolder)
    }

    @Test
    fun testOnBindViewHolder() {
        // Arrange
        val viewHolder = mock<OrderAdapter.OrderViewHolder>()
        val orderTitle = mock<TextView>()
        val orderDetails = mock<TextView>()

        `when`(viewHolder.orderTitle).thenReturn(orderTitle)
        `when`(viewHolder.orderDetails).thenReturn(orderDetails)

        // Act
        orderAdapter.onBindViewHolder(viewHolder, 0)

        // Assert
        verify(orderTitle).text = "Order 1"
        verify(orderDetails).text = "Details 1"
    }
}
