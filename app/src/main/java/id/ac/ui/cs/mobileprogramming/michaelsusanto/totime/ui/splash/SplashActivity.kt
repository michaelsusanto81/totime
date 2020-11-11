package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.MainActivity
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.R
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.User
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var viewModel: SplashViewModel
    private lateinit var viewModelFactory: SplashViewModelFactory
    private val INVALID_INPUT = "Please fill all fields."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        viewModelFactory = SplashViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SplashViewModel::class.java)
        checkUser()
        binding.btnStart.setOnClickListener { start() }
    }

    private fun checkUser() {
        if(viewModel.checkUser()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun start() {
        val name = binding.nameEdit.text.toString()
        val email = binding.emailEdit.text.toString()
        if(!viewModel.validateInput(name, email)) {
            val toast = Toast.makeText(this, INVALID_INPUT, Toast.LENGTH_LONG)
            toast.show()
        } else {
            val user = User(name = name, email = email)
            viewModel.saveData(user)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}