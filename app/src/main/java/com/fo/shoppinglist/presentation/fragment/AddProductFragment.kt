package com.fo.shoppinglist.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.fo.shoppinglist.databinding.FragmentAddProductBinding
import com.fo.shoppinglist.presentation.viewmodel.AddProductViewModel
import com.fo.shoppinglist.presentation.viewstate.AddButtonPressed
import com.fo.shoppinglist.presentation.viewstate.EmptyName
import com.fo.shoppinglist.util.SingleEventObserver
import com.fo.shoppinglist.util.getViewModelFactory
import timber.log.Timber

class AddProductFragment : Fragment() {

    private val viewModel by viewModels<AddProductViewModel> { getViewModelFactory() }
    private lateinit var binding: FragmentAddProductBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddProductBinding.inflate(inflater, container, false).apply {
            addProductViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewState()
        observeSingleEventViewState()
    }

    private fun observeViewState() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            when (it) {
                EmptyName -> Timber.e("Empty name error!")
            }
        })
    }

    private fun observeSingleEventViewState() {
        viewModel.singleEvent.observe(viewLifecycleOwner, SingleEventObserver{
            when(it){
                AddButtonPressed -> navigateToProductsListFragment()
            }
        })
    }

    private fun navigateToProductsListFragment() {
        findNavController().popBackStack()
    }

}