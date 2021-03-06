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
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.R
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.UserActivity
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.databinding.FragmentUserActivityBinding
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.service.TotimerService
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.util.TimerState

class UserActivityFragment : Fragment() {

    private lateinit var binding: FragmentUserActivityBinding
    private lateinit var viewModel: UserActivityViewModel
    private lateinit var viewModelFactory: UserActivityViewModelFactory
    private var timerText: String = "00:00:00"

    private val br: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateView(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("timerText", timerText)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(savedInstanceState != null) {
            timerText = savedInstanceState.getString("timerText")!!
            binding.timer.text = timerText
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user_activity, container, false)
        viewModelFactory = UserActivityViewModelFactory(requireContext())
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(UserActivityViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkTimerState()
        binding.btnStart.setOnClickListener { startTimer() }
        binding.btnStop.setOnClickListener { stopTimer() }
        binding.btnSave.setOnClickListener { saveTimer() }
    }

    private fun checkTimerState() {
        when(viewModel.getTimerState()) {
            TimerState.STARTED -> {
                binding.btnStart.visibility = View.GONE
                binding.btnStop.visibility = View.VISIBLE
                binding.btnSave.visibility = View.GONE
                binding.activityNameField.visibility = View.GONE
                binding.placeField.visibility = View.GONE
            }
            TimerState.STOPPED -> {
                binding.btnStart.visibility = View.GONE
                binding.btnStop.visibility = View.GONE
                binding.btnSave.visibility = View.VISIBLE
                binding.activityNameEdit.setText("")
                binding.activityNameField.visibility = View.VISIBLE
                binding.placeEdit.setText("")
                binding.placeField.visibility = View.VISIBLE
            }
            else -> {
                binding.btnStart.visibility = View.VISIBLE
                binding.btnStop.visibility = View.GONE
                binding.btnSave.visibility = View.GONE
                binding.activityNameField.visibility = View.GONE
                binding.placeField.visibility = View.GONE
                timerText = "00:00:00"
            }
        }
    }

    private fun startTimer() {
        activity?.startService(Intent(activity, TotimerService::class.java))
        viewModel.setTimerState("Started")
        checkTimerState()
    }

    private fun stopTimer() {
        activity?.stopService(Intent(activity, TotimerService::class.java))
        viewModel.setTimerState("Stopped")
        checkTimerState()
    }

    private fun saveTimer() {
        val activityName = binding.activityNameEdit.text.toString()
        val place = binding.placeEdit.text.toString()
        val hours = viewModel.getHours()
        val minutes = viewModel.getMinutes()
        val seconds = viewModel.getSeconds()
        val userActivity = UserActivity(
            name = activityName,
            hours = hours,
            minutes = minutes,
            seconds = seconds,
            dateStart = "testDate",
            place = place
        )

        val response = viewModel.validateInput(activityName, place)
        if(response.isError) {
            val toast = Toast.makeText(requireContext(), response.message, Toast.LENGTH_LONG)
            toast.show()
        } else {
            binding.timer.setText(R.string.start_timer)
            viewModel.saveData(userActivity)
            viewModel.setTimerState("Saved")
            checkTimerState()
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.registerReceiver(br, IntentFilter(TotimerService.TOTIMER_BR))
    }

    override fun onStop() {
        super.onStop()
        activity?.unregisterReceiver(br)
    }

    @SuppressLint("SetTextI18n")
    private fun updateView(intent: Intent?) {
        if(intent?.extras != null) {
            val hours: String = intent.getStringExtra("hours")!!
            val minutes: String = intent.getStringExtra("minutes")!!
            val seconds: String = intent.getStringExtra("seconds")!!
            timerText = "$hours:$minutes:$seconds"
            binding.timer.text = timerText
            viewModel.setTimer(hours.toInt(), minutes.toInt(), seconds.toInt())
        }
    }
}