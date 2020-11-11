package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.covid.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.R
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.CovidCase
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.databinding.FragmentCovidHomeBinding

class CovidHomeFragment : Fragment() {

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
        getData()
    }

    private fun getData() {
        viewModel.updateData()
        viewModel.covidCase.observe(viewLifecycleOwner, Observer {
            updateView(it)
        })
    }

    private fun updateView(covidCase: CovidCase) {
        binding.positiveValue.text = covidCase.positive
        binding.recoveredValue.text = covidCase.recovered
        binding.deathValue.text = covidCase.death
        binding.hospitalizedValue.text = covidCase.hospitalized
    }
}