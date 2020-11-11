package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.R
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.User
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: ProfileViewModel
    private lateinit var viewModelFactory: ProfileViewModelFactory
    private val INVALID_INPUT = "Please fill all fields."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        viewModelFactory = ProfileViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ProfileViewModel::class.java)

        setupToolbar()
        getData()
        binding.btnEdit.setOnClickListener { edit() }
        binding.btnSave.setOnClickListener { save() }
        binding.btnCancel.setOnClickListener { cancel() }
        binding.btnCamera.setOnClickListener { camera() }
        binding.btnGallery.setOnClickListener { gallery() }
    }

    private fun getData() {
        viewModel.getData()
        viewModel.liveData.observe(this, Observer {
            updateView(it)
        })
    }

    private fun updateView(user: User) {
        binding.nameLabel.text = user.name
        binding.emailLabel.text = user.email
    }

    private fun edit() {
        val name = viewModel.liveData.value?.name
        val email = viewModel.liveData.value?.email
        binding.nameLabel.visibility = View.GONE
        binding.emailLabel.visibility = View.GONE
        binding.btnEdit.visibility = View.GONE
        binding.nameField.visibility = View.VISIBLE
        binding.emailField.visibility = View.VISIBLE
        binding.btnSave.visibility = View.VISIBLE
        binding.btnCancel.visibility = View.VISIBLE
        binding.btnCamera.visibility = View.VISIBLE
        binding.btnGallery.visibility = View.VISIBLE
        binding.nameEdit.setText(name)
        binding.emailEdit.setText(email)
    }

    private fun save() {
        val name = binding.nameEdit.text.toString()
        val email = binding.emailEdit.text.toString()
        val user = viewModel.liveData.value
        if(!viewModel.validateInput(name, email)) {
            val toast = Toast.makeText(this, INVALID_INPUT, Toast.LENGTH_LONG)
            toast.show()
        } else {
            if (user != null) {
                user.name = name
                user.email = email
                viewModel.updateData(user)
            }
            binding.nameLabel.text = name
            binding.emailLabel.text = email
            cancel()
        }
    }

    private fun cancel() {
        binding.nameLabel.visibility = View.VISIBLE
        binding.emailLabel.visibility = View.VISIBLE
        binding.btnEdit.visibility = View.VISIBLE
        binding.nameField.visibility = View.GONE
        binding.emailField.visibility = View.GONE
        binding.btnSave.visibility = View.GONE
        binding.btnCancel.visibility = View.GONE
        binding.btnCamera.visibility = View.GONE
        binding.btnGallery.visibility = View.GONE
    }

    private fun camera() {

    }

    private fun gallery() {

    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            android.R.id.home -> pressBack()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun pressBack(): Boolean {
        onBackPressed()
        return true
    }
}