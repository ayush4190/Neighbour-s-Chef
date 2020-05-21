package com.example.myapplication.ui.fragment.history

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.CustomerApp
import com.example.myapplication.MobileNavigationDirections
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHistoryBinding
import com.example.myapplication.db.CustomerDatabase
import com.example.myapplication.util.android.base.BaseFragment
import com.example.myapplication.util.android.restartApp
import com.example.myapplication.util.android.showIf
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class HistoryFragment: BaseFragment<FragmentHistoryBinding>(), KodeinAware {
    override val kodein by kodein()
    val database by instance<CustomerDatabase>()
    val app by instance<CustomerApp>()

    private val historyViewModel by viewModels<HistoryViewModel> { HistoryViewModelFactory(database) }
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { HistoryAdapter(mutableListOf(), database) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerOrders.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.recyclerOrders.adapter = adapter

        binding.recyclerOrders.showIf(adapter.itemCount != 0)
        binding.textEmptyState.showIf(adapter.itemCount == 0)

        historyViewModel.orders.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.recyclerOrders.showIf(adapter.itemCount != 0)
            binding.textEmptyState.showIf(adapter.itemCount == 0)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) =
        inflater.inflate(R.menu.menu_main, menu)

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId) {
            R.id.action_settings -> {
                findNavController().navigate(MobileNavigationDirections.navigateToSettings())
                true
            }
            R.id.action_logout -> {
                app.signOut()
                restartApp(requireActivity())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}