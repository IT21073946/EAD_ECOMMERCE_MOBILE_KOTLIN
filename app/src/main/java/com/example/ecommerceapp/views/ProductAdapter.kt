package com.example.ecommerceapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceapp.databinding.ItemProductBinding
import com.example.ecommerceapp.models.Product
import com.squareup.picasso.Picasso

class ProductAdapter(
    private val products: List<Product>,
    private val onProductClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.productName.text = product.productName
            binding.productPrice.text = "$${product.price}"
            Picasso.get().load(product.imageUrl).into(binding.productImage)
            binding.root.setOnClickListener {
                onProductClick(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }
}
