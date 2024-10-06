package com.example.ecommerceapp.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecommerceapp.databinding.ActivityProductListBinding
import com.example.ecommerceapp.views.ProductAdapter
import com.example.ecommerceapp.views.ProductDetailActivity
import com.example.ecommerceapp.viewmodel.ProductViewModel

class ProductListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductListBinding
    private lateinit var viewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Initialize ViewBinding
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        // Set up RecyclerView with GridLayoutManager (2 columns)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2) // 2 columns

        // Observe the products LiveData and update RecyclerView
        viewModel.products.observe(this, Observer { products ->
            binding.recyclerView.adapter = ProductAdapter(products) { product ->
                // Handle product item click
                val intent = Intent(this, ProductDetailActivity::class.java)
                intent.putExtra("product", product)
                startActivity(intent)
            }
        })

        // Fetch products from API
        viewModel.getProducts()
    }
}
