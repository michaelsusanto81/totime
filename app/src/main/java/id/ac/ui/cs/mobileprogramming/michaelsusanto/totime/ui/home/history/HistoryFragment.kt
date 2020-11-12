package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.home.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.R
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.UserActivity
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener, HistoryCommunicator {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var viewModel: HistoryViewModel
    private lateinit var viewModelFactory: HistoryViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        viewModelFactory = HistoryViewModelFactory(requireContext())
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(HistoryViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchData()
        binding.swipeRefresh.setOnRefreshListener(this)
        binding.recView.layoutManager = LinearLayoutManager(context)
        binding.recView.adapter = HistoryAdapter(this, ArrayList())
    }

    private fun fetchData() {
        binding.swipeRefresh.isRefreshing = true
        viewModel.fetchData()
        viewModel.liveData.observe(viewLifecycleOwner, Observer {
            val userActivities = viewModel.liveData.value
            binding.recView.adapter = HistoryAdapter(this, userActivities as ArrayList)
        })
        binding.swipeRefresh.isRefreshing = false
    }

    override fun onRefresh() {
        fetchData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.swipeRefresh.setOnRefreshListener(null)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.swipeRefresh.setOnRefreshListener(null)
    }

    override fun clickedItem(userActivity: UserActivity) {
        viewModel.removeData(userActivity)
    }
}