package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.home.useractivity

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.R
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.databinding.FragmentUserActivityBinding
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.service.TotimerService
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.util.TimerState

class UserActivityFragment : Fragment() {

    private lateinit var binding: FragmentUserActivityBinding
    private lateinit var viewModel: UserActivityViewModel

    private val br: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateView(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user_activity, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(UserActivityViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkTimerState()
        binding.btnStart.setOnClickListener { startTimer() }
        binding.btnStop.setOnClickListener { stopTimer() }
    }

    private fun checkTimerState() {
        when(viewModel.getTimerState(requireContext())) {
            TimerState.STARTED -> {
                binding.btnStart.visibility = View.GONE
                binding.btnStop.visibility = View.VISIBLE
            }
            else -> {
                binding.btnStart.visibility = View.VISIBLE
                binding.btnStop.visibility = View.GONE
            }
        }
    }

    private fun startTimer() {
        activity?.startService(Intent(activity, TotimerService::class.java))
        viewModel.setTimerState(requireContext(), "Started")
        checkTimerState()
    }

    private fun stopTimer() {
        activity?.stopService(Intent(activity, TotimerService::class.java))
        viewModel.setTimerState(requireContext(), "Stopped")
        binding.timer.setText(R.string.start_timer)
        checkTimerState()
    }

    override fun onResume() {
        super.onResume()
        activity?.registerReceiver(br, IntentFilter(TotimerService.TOTIMER_BR))
    }

    override fun onStop() {
        super.onStop()
        activity?.unregisterReceiver(br)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    @SuppressLint("SetTextI18n")
    private fun updateView(intent: Intent?) {
        if(intent?.extras != null) {
            val hours: String = intent.getStringExtra("hours")!!
            val minutes: String = intent.getStringExtra("minutes")!!
            val seconds: String = intent.getStringExtra("seconds")!!
            binding.timer.text = "$hours:$minutes:$seconds"
        }
    }
}