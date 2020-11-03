package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.covid.saved

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.R
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.databinding.FragmentCovidSavedBinding

class CovidSavedFragment : Fragment() {

    private lateinit var binding: FragmentCovidSavedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_covid_saved, container, false)
        return binding.root
    }
}