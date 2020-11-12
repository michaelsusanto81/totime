package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.covid.saved

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
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.CovidCase
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.databinding.FragmentCovidSavedBinding

class CovidSavedFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener, CovidSavedCommunicator {

    private lateinit var binding: FragmentCovidSavedBinding
    private lateinit var viewModel: CovidSavedViewModel
    private lateinit var viewModelFactory: CovidSavedViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_covid_saved, container, false)
        viewModelFactory = CovidSavedViewModelFactory(requireContext())
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(CovidSavedViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchData()
        binding.swipeRefresh.setOnRefreshListener(this)
        binding.recView.layoutManager = LinearLayoutManager(context)
        binding.recView.adapter = CovidSavedAdapter(this, ArrayList())
    }

    private fun fetchData() {
        binding.swipeRefresh.isRefreshing = true
        viewModel.fetchData()
        viewModel.liveData.observe(viewLifecycleOwner, Observer {
            val savedCovidCases = viewModel.liveData.value
            binding.recView.adapter = CovidSavedAdapter(this, savedCovidCases as ArrayList)
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

    override fun clickedItem(covidSaved: CovidCase) {
        viewModel.removeData(covidSaved)
    }
}