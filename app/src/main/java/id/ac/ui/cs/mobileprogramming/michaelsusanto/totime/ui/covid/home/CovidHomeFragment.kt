package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.covid.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.R
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.CovidCase
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.databinding.FragmentCovidHomeBinding

class CovidHomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: FragmentCovidHomeBinding
    private lateinit var viewModel: CovidHomeViewModel
    private lateinit var viewModelFactory: CovidHomeViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_covid_home, container, false)
        viewModelFactory = CovidHomeViewModelFactory(requireContext())
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(CovidHomeViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeRefresh.setOnRefreshListener(this)
        binding.btnSave.setOnClickListener { saveData() }
        getData()
    }

    private fun saveData() {
        val covidCase = viewModel.covidCase.value
        if (covidCase != null) {
            viewModel.saveData(covidCase)
            val toast = Toast.makeText(requireContext(), "Data Saved", Toast.LENGTH_LONG)
            toast.show()
        }
    }

    private fun getData() {
        binding.btnSave.visibility = View.GONE
        binding.swipeRefresh.isRefreshing = true
        viewModel.updateData()
        viewModel.covidCase.observe(viewLifecycleOwner, Observer {
            updateView(it)
            binding.swipeRefresh.isRefreshing = false
            binding.btnSave.visibility = View.VISIBLE
        })
        viewModel.error.observe(viewLifecycleOwner, Observer {
            val toast = Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT)
            toast.show()
            binding.swipeRefresh.isRefreshing = false
            binding.btnSave.visibility = View.VISIBLE
        })
    }

    private fun updateView(covidCase: CovidCase) {
        binding.positiveValue.text = covidCase.positive
        binding.recoveredValue.text = covidCase.recovered
        binding.deathValue.text = covidCase.death
        binding.hospitalizedValue.text = covidCase.hospitalized
    }

    override fun onRefresh() {
        getData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.swipeRefresh.setOnRefreshListener(null)
    }
}