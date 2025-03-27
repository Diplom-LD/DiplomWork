package com.example.diplomwork.di

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomwork.R

/**
 * Data class representing an order.
 *
 * @property title The title of the order.
 * @property details The details of the order.
 */
data class Order(val title: String, val details: String)

/**
 * Adapter for displaying a list of orders in a RecyclerView.
 *
 * @property orderList The list of orders to be displayed.
 */
class OrderAdapter(private val orderList: List<Order>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_item, parent, false)
        return OrderViewHolder(view)
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]
        holder.orderTitle.text = order.title
        holder.orderDetails.text = order.details
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {
        return orderList.size
    }

    /**
     * ViewHolder class for holding references to the views within each order item.
     *
     * @property orderTitle The TextView displaying the order title.
     * @property orderDetails The TextView displaying the order details.
     */
    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderTitle: TextView = itemView.findViewById(R.id.orderTitle)
        val orderDetails: TextView = itemView.findViewById(R.id.orderDetails)
    }
}
