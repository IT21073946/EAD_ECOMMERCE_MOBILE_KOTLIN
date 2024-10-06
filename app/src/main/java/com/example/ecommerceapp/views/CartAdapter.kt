package com.example.ecommerceapp.views

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceapp.databinding.ItemCartProductBinding
import com.example.ecommerceapp.models.Cart
import com.example.ecommerceapp.models.Product

class CartAdapter(private val products: MutableList<Product>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(private val binding: ItemCartProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product, position: Int) {
            binding.productName.text = product.productName
            binding.productPrice.text = "Price: $${product.price}"

            // Handle delete product from cart
            binding.deleteIcon.setOnClickListener {
                Cart.getInstance().removeProduct(product)  // Remove product from cart
                products.removeAt(position)  // Remove product from the list for the adapter
                notifyItemRemoved(position)  // Notify adapter of item removal
                Toast.makeText(binding.root.context, "${product.productName} removed from cart", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(products[position], position)
    }

    override fun getItemCount(): Int {
        return products.size
    }
}
