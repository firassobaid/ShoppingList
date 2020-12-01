package com.fo.shoppinglist.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fo.shoppinglist.databinding.FragmentDetailsBinding
import com.fo.shoppinglist.presentation.viewmodel.DetailsViewModel
import com.fo.shoppinglist.presentation.viewstate.ErrorDetailsViewState
import com.fo.shoppinglist.presentation.viewstate.InitDetailsViewState
import com.fo.shoppinglist.presentation.viewstate.NavigateToShoppingListFragment
import com.fo.shoppinglist.presentation.viewstate.ShowDialogEvent
import com.fo.shoppinglist.util.SingleEventObserver
import com.fo.shoppinglist.util.getViewModelFactory
import timber.log.Timber

class DetailsFragment : Fragment() {

    private val viewModel by viewModels<DetailsViewModel> { getViewModelFactory() }
    private lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false).apply {
            detailsViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewState()
        observeSingleEvents()
        viewModel.start(args.productId)
    }

    private fun observeViewState() {
        viewModel.viewState.observe(viewLifecycleOwner, { state ->
            when (state) {
                is InitDetailsViewState -> Timber.i("Init state")
                ErrorDetailsViewState -> Timber.e("Error getting details!")
            }
        })
    }

    private fun observeSingleEvents() {
        viewModel.singleEvent.observe(viewLifecycleOwner, SingleEventObserver {
            when (it) {
                ShowDialogEvent -> deleteDialog()
                NavigateToShoppingListFragment -> navigateToShoppingList()
            }
        })
    }

    private fun navigateToShoppingList() {
//        val action = DetailsFragmentDirections.actionDetailsFragmentToShoppingListFragment()
//        findNavController().navigate(action)
        findNavController().popBackStack()
    }

    private fun deleteDialog() {
        activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle("Are you sure you want to delete this item?")
                setPositiveButton("Ok") { _, _ ->
                    viewModel.deleteItem(args.productId)
                }
                setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
            }
            builder.create()
            builder.show()
        }
    }
}