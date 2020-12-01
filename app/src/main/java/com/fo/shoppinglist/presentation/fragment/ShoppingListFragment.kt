package com.fo.shoppinglist.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fo.shoppinglist.data.model.Product
import com.fo.shoppinglist.databinding.FragmentShoppingListBinding
import com.fo.shoppinglist.presentation.adapter.ShoppingListAdapter
import com.fo.shoppinglist.presentation.viewmodel.ShoppingListViewModel
import com.fo.shoppinglist.presentation.viewstate.ErrorState
import com.fo.shoppinglist.presentation.viewstate.NavigateToAddProduct
import com.fo.shoppinglist.presentation.viewstate.NavigateToProductDetails
import com.fo.shoppinglist.util.SingleEventObserver
import com.fo.shoppinglist.util.getViewModelFactory
import timber.log.Timber

class ShoppingListFragment : Fragment() {

    private val viewModel by viewModels<ShoppingListViewModel> { getViewModelFactory() }
    private lateinit var binding: FragmentShoppingListBinding
    private lateinit var adapter: ShoppingListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShoppingListBinding.inflate(inflater, container, false).apply {
            shoppingListViewModel = viewModel
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner
        setupAdapter()
        observeViewState()
        observeProducts()
        observeSingleEvents()
    }

    private fun observeProducts() {
        viewModel.products.observe(viewLifecycleOwner, {
            updateList(it)
        })
    }

    private fun observeViewState() {
        viewModel.viewState.observe(viewLifecycleOwner, {
            when (it) {
                ErrorState -> Timber.e("Unable to load data.")
            }
        })
    }

    private fun observeSingleEvents() {
        viewModel.singleEvent.observe(viewLifecycleOwner, SingleEventObserver {
            when (it) {
                is NavigateToProductDetails -> navigateToProductDetailsFragment(it.product)
                NavigateToAddProduct -> navigateToAddProductFragment()
            }
        })
    }

    private fun updateList(products: List<Product>) {
        adapter.submitList(products)
        adapter.notifyDataSetChanged()
    }

    private fun setupAdapter() {
        val viewModel = binding.shoppingListViewModel
        if (viewModel != null) {
            adapter = ShoppingListAdapter(viewModel)
            binding.shoppingListRecyclerView.adapter = adapter
        } else {
            Timber.w("ViewModel not initialized when attempting to set up adapter.")
        }
    }

    private fun navigateToAddProductFragment() {
        val action = ShoppingListFragmentDirections.actionShoppingListFragmentToAddProductFragment()
        findNavController().navigate(action)
    }

    private fun navigateToProductDetailsFragment(product: Product) {
        val action =
            ShoppingListFragmentDirections.actionShoppingListFragmentToDetailsFragment(product.id)
        findNavController().navigate(action)
    }
}